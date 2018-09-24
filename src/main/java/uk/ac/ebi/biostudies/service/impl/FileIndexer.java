package uk.ac.ebi.biostudies.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.controller.Index;

import java.io.IOException;
import java.util.*;


public class FileIndexer {
    private static Logger LOGGER = LogManager.getLogger(FileIndexer.class.getName());

    public static String indexSubmissionFiles(String accession,JsonNode json, IndexWriter writer, Set<String> attributeColumns) throws IOException {
        int counter = 0;
        List<String> columns = new ArrayList<>();
        Set<String> sectionsWithFiles = new HashSet<>();
        List<JsonNode> filesParents = json.findParents("files");
        if(!Index.getIsFullIndex()) {
            deleteOldFiles(writer, accession);
        }
        if(filesParents==null) return null;
        for(JsonNode parent:filesParents) {
            if(parent==null) continue;
            for(JsonNode fNode : parent.findValue("files")){
                Document doc = getFileDocument(accession, columns, fNode, parent);
                writer.updateDocument(new Term(Constants.Fields.ID, accession + counter++), doc);
                if (doc.get(Constants.File.SECTION)!=null) {
                    sectionsWithFiles.add(doc.get(Constants.File.SECTION));
                }
            }
        }

        //put Section as the first column. Name and size would be prepended later
        if (columns.contains("Section")) {
            columns.remove("Section");
            columns.add(0,"Section");
        }
        attributeColumns.addAll(columns);

        return sectionsWithFiles.size()==0 ? null : String.join(" ", sectionsWithFiles);
    }

    private static Document getFileDocument(String accession, List<String> attributeColumns, JsonNode fNode, JsonNode parent) {
        Long size;
        String path;
        String name;
        List<JsonNode> attributes;
        String value;
        Document doc = new Document();
        if(fNode.get(Constants.File.SIZE.toLowerCase())!=null) {
            size = Long.valueOf(fNode.get(Constants.File.SIZE.toLowerCase()).asText());
            doc.add(new SortedNumericDocValuesField(Constants.File.SIZE, size));
            doc.add(new StoredField(Constants.File.SIZE, size));
        }
        JsonNode pathNode = fNode.get(Constants.File.PATH);
        path = pathNode==null || pathNode.asText().equalsIgnoreCase("null")? null : pathNode.asText();
        pathNode = fNode.get(Constants.IndexEntryAttributes.NAME);
        name = pathNode==null || pathNode.asText().equalsIgnoreCase("null")? null:pathNode.asText();
        if(path==null && name!=null)
            path = name;
        if(path!=null && name == null)
            name = path.contains("/") ? StringUtils.substringAfterLast(path, "/") : path;
        doc.add(new StringField(Constants.File.PATH, path.toLowerCase(), Field.Store.NO));
        doc.add(new StoredField(Constants.File.PATH, path));
        doc.add(new SortedDocValuesField(Constants.File.PATH, new BytesRef(path)));
        if(name!=null) {
            doc.add(new StringField(Constants.File.NAME, name.toLowerCase(), Field.Store.NO));
            doc.add(new StoredField(Constants.File.NAME, name));
            doc.add(new SortedDocValuesField(Constants.File.NAME, new BytesRef(name)));
        }
        attributes = fNode.findValues(Constants.File.ATTRIBUTES);

        doc.add(new StringField(Constants.File.TYPE, Constants.File.FILE, Field.Store.YES));
        doc.add(new StringField(Constants.File.OWNER, accession, Field.Store.YES));
        // add section field if file is not global
        if (parent.has("accno") && (!parent.has("type") || !parent.get("type").textValue().toLowerCase().equalsIgnoreCase("study") )  ) {
            String section = parent.get("accno").textValue().replaceAll ("/","").replaceAll(" ", "");
            doc.add(new StringField(Constants.File.SECTION,  section, Field.Store.NO));
            doc.add(new StoredField(Constants.File.SECTION, section));
            doc.add(new SortedDocValuesField(Constants.File.SECTION, new BytesRef(section)));
            attributeColumns.add(Constants.File.SECTION);
        }

        if (attributes != null && attributes.size()>0 && attributes.get(0)!=null) {
            for (JsonNode attrib : attributes.get(0)) {
                JsonNode tempAttName = attrib.findValue(Constants.IndexEntryAttributes.NAME);
                JsonNode tempAttValue = attrib.findValue(Constants.File.VALUE);
                if(tempAttName==null || tempAttValue==null)
                    continue;
                name = tempAttName.asText();
                value = tempAttValue.asText();
                if(name!=null && value!=null && !name.isEmpty() && ! value.isEmpty()) {
                    if(doc.getField(name)!=null)
                    {
//                                        LOGGER.debug("this value is repeated accno: {} firstAppearance value: {}, secondAppearance value: {}", accession, doc.getField(Constants.File.FILE_ATTS + name).stringValue(), name);
                        continue;
                    }
                    if(name.equalsIgnoreCase("type") && accession.toLowerCase().contains("epmc"))
                        continue;
                    doc.add(new StringField(name, value.toLowerCase(), Field.Store.NO));
                    doc.add(new StoredField(name, value));
                    doc.add(new SortedDocValuesField(name, new BytesRef(value) ));
                    attributeColumns.add(name);
                }
            }
        }

        return doc;
    }

    private static void deleteOldFiles(IndexWriter writer, String deleteAccession){
        QueryParser parser = new QueryParser(Constants.File.OWNER, new KeywordAnalyzer());
        try {
            Query query = parser.parse(Constants.File.OWNER+":"+deleteAccession);
            writer.deleteDocuments(query);
        } catch (Exception e) {
            LOGGER.error("Problem in deleting old files", e);
        }
    }

}
