package uk.ac.ebi.biostudies.service.impl;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.service.FileIndexService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class FileIndexServiceImpl implements FileIndexService {
    private static Logger logger = LogManager.getLogger(FileIndexServiceImpl.class.getName());

    @Autowired
    IndexConfig indexConfig;



    public Map<String, Object> indexSubmissionFiles(String accession, String relativePath, JsonNode json, IndexWriter writer, Set<String> attributeColumns, boolean removeFileDocuments) throws IOException {
        Map<String, Object> valueMap = new HashMap<>();
        AtomicLong counter = new AtomicLong();
        List<String> columns = new ArrayList<>();
        Set<String> sectionsWithFiles = new HashSet<>();
        if(removeFileDocuments) {
            removeFileDocuments(writer, accession);
        }

        // find files
        List<JsonNode> filesParents = json.findParents("files").stream().filter( p-> p.get("files").size()>0).collect(Collectors.toList());
        if(filesParents!=null) {
            for (JsonNode parent : filesParents) {
                if (parent == null) continue;
                counter.set(indexFileList(accession, writer, counter.get(), columns, sectionsWithFiles, parent, parent));
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        //find file lists
        List<JsonNode> subSections = json.findParents("attributes") ;
        Map<String, JsonNode> parents = new HashMap<>();
        for (JsonNode subSection: subSections) {
            ArrayNode attributes = (ArrayNode) subSection.get("attributes");
            for (JsonNode attribute : attributes) {
                if (attribute.get("name").textValue().equalsIgnoreCase("file list")) {
                    parents.put(attribute.get("value").textValue(),subSection);
                }
            }

        }

        parents.forEach((filename, jsonNode) -> {
            if(jsonNode==null) return;
            String libraryFilePath = indexConfig.getFileRootDir() + "/"+ relativePath + "/"+  filename + (filename.toLowerCase().endsWith(".json")? "" : ".json");
            try {
                counter.set(indexLibraryFile(accession, writer, counter.get(), columns, sectionsWithFiles, jsonNode, libraryFilePath));
            } catch (IOException e) {
                logger.error(e);
            }
        });


        //put Section as the first column. Name and size would be prepended later
        if (columns.contains("Section")) {
            columns.remove("Section");
            columns.add(0,"Section");
        }
        attributeColumns.addAll(columns);
        if (sectionsWithFiles.size()!=0) {
            valueMap.put(Constants.Fields.SECTIONS_WITH_FILES, String.join(" ", sectionsWithFiles));
        }
        valueMap.put(Constants.Fields.FILES, counter.longValue());

        return valueMap;
    }

    private long indexFileList(String accession, IndexWriter writer, long counter, List<String> columns, Set<String> sectionsWithFiles, JsonNode parent, JsonNode nodeWithFiles) throws IOException {
        for(JsonNode fNode : nodeWithFiles.get("files")) {
            if (fNode.isArray()) {
                for (JsonNode singleFile : fNode) {
                    counter = indexSingleFile(accession, writer, counter, columns, sectionsWithFiles, parent, singleFile);
                }
            } else if (fNode.has("extType") && fNode.get("extType").textValue().equalsIgnoreCase("filesTable") ) {
                for (JsonNode singleFile : fNode.get("files")) {
                    counter = indexSingleFile(accession, writer, counter, columns, sectionsWithFiles, parent, singleFile);
                }
            } else {
                counter = indexSingleFile(accession, writer, counter, columns, sectionsWithFiles, parent, fNode);
            }
        }
        return counter;
    }

    private long indexLibraryFile(String accession, IndexWriter writer, long counter, List<String> columns, Set<String> sectionsWithFiles, JsonNode parent, String libraryFilePath) throws IOException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(libraryFilePath), "UTF-8")) {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(inputStreamReader);
            JsonToken token = parser.nextToken();
            while (!JsonToken.START_ARRAY.equals(token)) {
                token = parser.nextToken();
            }

            ObjectMapper mapper = new ObjectMapper();
            while (true) {
                token = parser.nextToken();
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }
                if (token == null) {
                    break;
                }

                JsonNode singleFile = mapper.readTree(parser);
                counter = indexSingleFile(accession, writer, counter, columns, sectionsWithFiles, parent, singleFile);
            }
        }
        return counter;
    }


    private long indexSingleFile(String accession, IndexWriter writer, long counter, List<String> columns, Set<String> sectionsWithFiles, JsonNode parent, JsonNode fNode) throws IOException {
        Document doc = getFileDocument(accession, columns, fNode, parent);
        writer.updateDocument(new Term(Constants.Fields.ID, accession + "-" + counter++), doc);
        if (doc.get(Constants.File.SECTION) != null) {
            IndexableField []sectionFields = doc.getFields(Constants.File.SECTION);
            //To take stored section field from lucene doc instead of indexedField for case sensivity difference in search and UI presentation
            if(sectionFields.length>0) {
                for (IndexableField secField : sectionFields) {
                    if (secField.fieldType().stored() && secField.stringValue() != null) {
                        sectionsWithFiles.add(secField.stringValue());
                        break;
                    }
                }
            }
        }
        return counter;
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
        if (name==null && fNode.has(Constants.File.FILENAME)) name = fNode.get(Constants.File.FILENAME).asText();
        if(path==null && name!=null)
            path = name;
        if(path!=null && name == null)
            name = path.contains("/") ? StringUtils.substringAfterLast(path, "/") : path;
        if (path!=null) {
            doc.add(new StringField(Constants.File.PATH, path.toLowerCase(), Field.Store.NO));
        }
        doc.add(new StoredField(Constants.File.PATH, path));
        doc.add(new SortedDocValuesField(Constants.File.PATH, new BytesRef(path)));
        if(name!=null) {
            doc.add(new StringField(Constants.File.NAME, name.toLowerCase(), Field.Store.NO));
            doc.add(new StringField(Constants.File.NAME, name, Field.Store.NO));
            doc.add(new StoredField(Constants.File.NAME, name));
            doc.add(new SortedDocValuesField(Constants.File.NAME, new BytesRef(name)));
        }
        attributes = fNode.findValues(Constants.File.ATTRIBUTES);

        doc.add(new StringField(Constants.File.TYPE, Constants.File.FILE , Field.Store.YES));
        doc.add(new StringField(Constants.File.IS_DIRECTORY, String.valueOf(fNode.get(Constants.File.TYPE).textValue().equalsIgnoreCase("directory")), Field.Store.YES));
        doc.add(new StringField(Constants.File.OWNER, accession, Field.Store.YES));

        // add section field if file is not global
        if ( (parent.has("accno") || parent.has("accNo")) && (!parent.has("type") || !parent.get("type").textValue().toLowerCase().equalsIgnoreCase("study") )  ) {
            String section =  parent.get( parent.has("accno") ? "accno" : "accNo").textValue().replaceAll("/","").replaceAll(" ", "");
            //to lower case for search should be case insensitive
            doc.add(new StringField(Constants.File.SECTION,  section.toLowerCase(), Field.Store.NO));
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
    //                                        logger.debug("this value is repeated accno: {} firstAppearance value: {}, secondAppearance value: {}", accession, doc.getField(Constants.File.FILE_ATTS + name).stringValue(), name);
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

    private static void removeFileDocuments(IndexWriter writer, String deleteAccession){
        QueryParser parser = new QueryParser(Constants.File.OWNER, new KeywordAnalyzer());
        try {
            Query query = parser.parse(Constants.File.OWNER+":"+deleteAccession);
            writer.deleteDocuments(query);
        } catch (Exception e) {
            logger.error("Problem in deleting old files", e);
        }
    }

}
