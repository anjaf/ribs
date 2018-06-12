package uk.ac.ebi.biostudies.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.BytesRef;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.List;
import java.util.Set;


public class FileIndexer {
    private static Logger LOGGER = LogManager.getLogger(FileIndexer.class.getName());

    public static void indexSubmissionFiles(String accession,JsonNode json, IndexWriter writer, Set<String> attributeColumns){
        List<JsonNode> allFileNodes = json.findValues("files");
        Long size=0L;
        String name;
        String path;
        List<JsonNode> attributes;
        String value;
        int counter = 0;
        try{
            if(allFileNodes!=null)
            for(JsonNode fileNodes:allFileNodes) {
                if(fileNodes!=null)
                    for(JsonNode fNode : fileNodes){
                        Document doc = new Document();
                        if(fNode.get(Constants.File.JSONSIZE)!=null) {
                            size = Long.valueOf(fNode.get(Constants.File.JSONSIZE).asText());
                            doc.add(new SortedNumericDocValuesField(Constants.File.SIZE, size));
                            doc.add(new StoredField(Constants.File.SIZE, size));
                        }
                        path = fNode.get(Constants.File.JSONPATH).asText();
                        name = path;
                        if(path!=null) {
                            name = path.contains("/") ? StringUtils.substringAfterLast(path, "/") : path;
                            doc.add(new StringField(Constants.File.PATH, path, Field.Store.YES));
                            doc.add(new SortedDocValuesField(Constants.File.PATH, new BytesRef(path)));
                        }
                        attributes = fNode.findValues(Constants.File.ATTRIBUTES);
                        if(name!=null) {
                            doc.add(new StringField(Constants.File.NAME, name, Field.Store.YES));
                            doc.add(new SortedDocValuesField(Constants.File.NAME, new BytesRef(name)));
                        }
                        doc.add(new StringField(Constants.File.TYPE, Constants.File.FILE, Field.Store.YES));
                        doc.add(new StringField(Constants.File.OWNER, accession, Field.Store.YES));
                        if (attributes != null && attributes.get(0)!=null) {
                            for (JsonNode attrib : attributes.get(0)) {
                                JsonNode tempAttName = attrib.findValue(Constants.File.JSONNAME);
                                JsonNode tempAttValue = attrib.findValue(Constants.File.VALUE);
                                if(tempAttName==null || tempAttValue==null)
                                    continue;
                                name = tempAttName.asText();
                                value = tempAttValue.asText();
                                if(name!=null && value!=null && !name.isEmpty() && ! value.isEmpty()) {
                                    if(doc.getField(Constants.File.FILE_ATTS + name)!=null)
                                    {
//                                        LOGGER.debug("this value is repeated accno: {} firstAppearance value: {}, secondAppearance value: {}", accession, doc.getField(Constants.File.FILE_ATTS + name).stringValue(), name);
                                        continue;
                                    }
                                    doc.add(new StringField(Constants.File.FILE_ATTS + name, value, Field.Store.YES));
                                    doc.add(new SortedDocValuesField(Constants.File.FILE_ATTS + name, new BytesRef(value) ));
                                    attributeColumns.add(name);
                                }
                            }
                        }
                        writer.updateDocument(new Term(Constants.Fields.ID, accession + counter++), doc);
                }
            }
        }catch(Throwable th){
            LOGGER.debug("problem in file parsing", th);
        }

    }

}
