package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.BytesRef;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.parser.AbstractParser;
import uk.ac.ebi.biostudies.api.util.parser.ParserManager;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.FileIndexService;

import java.io.IOException;
import java.util.*;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;
import static uk.ac.ebi.biostudies.api.util.Constants.PUBLIC;

public class JsonDocumentIndexer implements Runnable {
    private Logger logger = LogManager.getLogger(JsonDocumentIndexer.class.getName());

    private IndexWriter writer;
    private JsonNode json;
    private TaxonomyManager taxonomyManager;
    private IndexManager indexManager;
    private FileIndexService fileIndexService;
    private boolean removeFileDocuments;
    private ParserManager parserManager;

    public JsonDocumentIndexer(JsonNode json, TaxonomyManager taxonomyManager, IndexManager indexManager, FileIndexService fileIndexService, boolean removeFileDocuments, ParserManager parserManager) {
        this.writer = indexManager.getIndexWriter();
        this.json = json;
        this.taxonomyManager = taxonomyManager;
        this.indexManager = indexManager;
        this.removeFileDocuments = removeFileDocuments;
        this.parserManager = parserManager;
        this.fileIndexService = fileIndexService;
    }

    @Override
    public void run() {
        Map<String, Object> valueMap = new HashMap<>();
        String accession = "";
        try {
            ReadContext jsonPathContext = JsonPath.parse(json.toString());
            accession = parserManager.getParser(Constants.Fields.ACCESSION).parse(valueMap, json, jsonPathContext);
            parserManager.getParser(Constants.Fields.SECRET_KEY).parse(valueMap, json, jsonPathContext);

            for (JsonNode fieldMetadataNode : indexManager.getIndexDetails().findValue(PUBLIC)) {//parsing common "public" facet and fields
                AbstractParser abstractParser = parserManager.getParser(fieldMetadataNode.get("name").asText());
                abstractParser.parse(valueMap, json, jsonPathContext);
            }
            //collections do not need more parsing
            if (valueMap.getOrDefault(Constants.Fields.TYPE, "").toString().equalsIgnoreCase("collection")) {
                addCollectionToHierarchy(valueMap, accession);
                updateDocument(valueMap);
                return;
            }

            // remove repeating collections
            Set<String> collectionFacets = new HashSet<>();
            if (valueMap.containsKey(Constants.Facets.COLLECTION)) {
                collectionFacets.addAll(Arrays.asList(valueMap.get(Constants.Facets.COLLECTION).toString().toLowerCase().split("\\" + Constants.Facets.DELIMITER)));
                collectionFacets.remove("");
                collectionFacets.remove(PUBLIC);
                valueMap.put(Constants.Facets.COLLECTION, String.join(Constants.Facets.DELIMITER, collectionFacets));
            }

            for (String collectionName : collectionFacets) {
                JsonNode collectionSpecificFields = indexManager.getIndexDetails().findValue(collectionName);
                if (collectionSpecificFields != null) {
                    for (JsonNode fieldMetadataNode : collectionSpecificFields) {//parsing collection's facet and fields
                        AbstractParser abstractParser = parserManager.getParser(fieldMetadataNode.get("name").asText());
                        abstractParser.parse(valueMap, json, jsonPathContext);
                    }
                }
            }
            Set<String> columnSet = new LinkedHashSet<>();

            Map<String, Object> fileValueMap = fileIndexService.indexSubmissionFiles((String) valueMap.get(Constants.Fields.ACCESSION), (String) valueMap.get(Constants.Fields.RELATIVE_PATH), json, writer, columnSet, removeFileDocuments);
            if (fileValueMap != null) {
                valueMap.putAll(fileValueMap);
            }

            valueMap.put(Constants.File.FILE_ATTS, columnSet);
            updateDocument(valueMap);

        } catch (Exception ex) {
            logger.debug("problem in parser for parsing accession: {}!", accession, ex);
        }
    }

    private void addCollectionToHierarchy(Map<String, Object> valueMap, String accession) {
        Object parent = valueMap.getOrDefault(Constants.Facets.COLLECTION, null);
        //TODO: Start - Remove this when backend supports subcollections
        if (accession.equalsIgnoreCase("JCB") || accession.equalsIgnoreCase("BioImages-EMPIAR")) {
            parent = "BioImages";
        }
        //TODO: End - Remove this when backend supports subcollections
        if (parent == null || StringUtils.isEmpty(parent.toString())) {
            indexManager.unsetCollectionParent(accession);
        } else {
            indexManager.setSubCollection(parent.toString(), accession);
        }
    }

    private void updateDocument(Map<String, Object> valueMap) throws IOException {
        Document doc = new Document();

        //TODO: replace by classes if possible
        String value;
        String prjName = (String) valueMap.get(Constants.Facets.COLLECTION);
        //updateCollectionParents(valueMap);
        addFileAttributes(doc, (Set<String>) valueMap.get(Constants.File.FILE_ATTS));
        for (String field : indexManager.getCollectionRelatedFields(prjName.toLowerCase())) {
            JsonNode curNode = indexManager.getIndexEntryMap().get(field);
            String fieldType = curNode.get(Constants.IndexEntryAttributes.FIELD_TYPE).asText();
            try {
                switch (fieldType) {
                    case Constants.IndexEntryAttributes.FieldTypeValues.TOKENIZED_STRING:
                        value = String.valueOf(valueMap.get(field));
                        doc.add(new TextField(String.valueOf(field), value, Field.Store.YES));
                        break;
                    case Constants.IndexEntryAttributes.FieldTypeValues.UNTOKENIZED_STRING:
                        if (!valueMap.containsKey(field)) break;
                        value = String.valueOf(valueMap.get(field));
                        Field unTokenizeField = new Field(String.valueOf(field), value, IndexServiceImpl.TYPE_NOT_ANALYZED);
                        doc.add(unTokenizeField);
                        if (curNode.has(Constants.IndexEntryAttributes.SORTABLE) && curNode.get(Constants.IndexEntryAttributes.SORTABLE).asBoolean(false))
                            doc.add(new SortedDocValuesField(String.valueOf(field), new BytesRef(valueMap.get(field).toString())));
                        break;
                    case Constants.IndexEntryAttributes.FieldTypeValues.LONG:
                        if (!valueMap.containsKey(field) || StringUtils.isEmpty(valueMap.get(field).toString())) break;
                        doc.add(new SortedNumericDocValuesField(String.valueOf(field), (Long) valueMap.get(field)));
                        doc.add(new StoredField(String.valueOf(field), valueMap.get(field).toString()));
                        doc.add(new LongPoint(String.valueOf(field), (Long) valueMap.get(field)));
                        break;
                    case Constants.IndexEntryAttributes.FieldTypeValues.FACET:
                        addFacet(valueMap.containsKey(field) && valueMap.get(field) != null ?
                                String.valueOf(valueMap.get(field)) : null, field, doc, curNode);
                }
            } catch (Exception ex) {
                logger.error("field name: {} doc accession: {}", field.toString(), String.valueOf(valueMap.get(Constants.Fields.ACCESSION)), ex);
            }


        }

        Document facetedDocument = taxonomyManager.getFacetsConfig().build(indexManager.getFacetWriter(), doc);
        writer.updateDocument(new Term(Constants.Fields.ID, valueMap.get(Constants.Fields.ACCESSION).toString()), facetedDocument);

    }

    /*private void updateCollectionParents(Map<String, Object> valueMap) {
        String collection = valueMap.getOrDefault (Facets.PROJECT, "").toString();
        if (StringUtils.isEmpty(collection)) return;
        Map<String, String> collectionParentMap = indexManager.getCollectionParentMap();
        List<String> parents = new ArrayList<>();
        parents.add(collection);
        while (collectionParentMap.containsKey(collection)) {
            String parent = collectionParentMap.get(collection);
            parents.add(parent);
            collection = parent;
        }
        valueMap.put(Facets.PROJECT, StringUtils.join(parents,Facets.DELIMITER));
    }*/

    private void addFileAttributes(Document doc, Set<String> columnAtts) {
        StringBuilder allAtts = new StringBuilder("Name|Size|");
        if (columnAtts == null)
            columnAtts = new HashSet<>();
        for (String att : columnAtts)
            allAtts.append(att).append("|");
        doc.add(new StringField(Constants.File.FILE_ATTS, allAtts.toString(), Field.Store.YES));
    }

    private void addFacet(String value, String fieldName, Document doc, JsonNode facetConfig) {
        if (value == null || value.isEmpty()) {
            if (fieldName.equalsIgnoreCase(Constants.Facets.FILE_TYPE) || fieldName.equalsIgnoreCase(Constants.Facets.LINK_TYPE)
                    || (facetConfig.has(Constants.IndexEntryAttributes.FACET_TYPE) && facetConfig.get(Constants.IndexEntryAttributes.FACET_TYPE).asText().equalsIgnoreCase("boolean"))
            )
                return;
            else
                value = NA;
        }
        for (String subVal : StringUtils.split(value, Constants.Facets.DELIMITER)) {
            if (subVal.equalsIgnoreCase(NA) && facetConfig.has(Constants.IndexEntryAttributes.DEFAULT_VALUE)) {
                subVal = facetConfig.get(Constants.IndexEntryAttributes.DEFAULT_VALUE).textValue();
            }
            doc.add(new FacetField(fieldName, subVal.trim().toLowerCase()));
        }
    }
}
