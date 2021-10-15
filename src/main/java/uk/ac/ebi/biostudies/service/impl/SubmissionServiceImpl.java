package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.auth.Session;
import uk.ac.ebi.biostudies.auth.User;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.SecurityConfig;
import uk.ac.ebi.biostudies.file.Thumbnails;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.SubmissionNotAccessibleException;
import uk.ac.ebi.biostudies.service.SubmissionService;

import java.io.IOException;
import java.util.*;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    SubmissionReaderFactory submissionReaderFactory;
    @Autowired
    IndexManager indexManager;
    @Autowired
    SearchService searchService;
    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    IndexConfig indexConfig;
    @Autowired
    Thumbnails thumbnails;


    private Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public InputStreamResource getStudyAsStream(Document document, String secretKey) throws IOException {
        SubmissionFile submissionFile = submissionReaderFactory.createSubmissionFileReader(document, secretKey);
        return submissionFile.getInputStreamResource();
    }

    @Override
    public ObjectNode getStudyInfo(String accession, String secretKey) throws SubmissionNotAccessibleException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode studyInfo = mapper.createObjectNode();

        String orderedArray[] = {"Name", "Size"};
        ArrayNode fileColumnAttributes = mapper.createArrayNode();
        Document doc = searchService.getSubmissionDocumentByAccession(accession, secretKey);
        if (doc==null) return studyInfo;
        accession = doc.get(Constants.Fields.ACCESSION);
        String relativePath = doc.get(Constants.Fields.RELATIVE_PATH);
        String attFiles = doc.get(Constants.File.FILE_ATTS);
        if (attFiles==null) return studyInfo;
        String allAtts[] = attFiles.split("\\|");
        Set<String> headerSet = new HashSet<>(Arrays.asList(orderedArray));
        List<String> orderedList = new ArrayList<>(Arrays.asList(orderedArray));
        for(String att:allAtts) {
            if (att.isEmpty() || headerSet.contains(att))
                continue;
            headerSet.add(att);
            orderedList.add(att);
        }
        int counter =0;
        for(String att : orderedList){
            ObjectNode node = mapper.createObjectNode();
            node.put("name", att);
            node.put("title", att);
            node.put("visible", true);
            node.put("searchable", !att.equalsIgnoreCase("size"));
            node.put("data", att.replaceAll("[\\[\\]\\(\\)\\s]", "_"));
            node.put("defaultContent", "");
            fileColumnAttributes.add(node);
            if(counter++==1 && thumbnails.hasThumbnails(accession, relativePath)){
                fileColumnAttributes.add(getThumbnailHeader(mapper));
            }
        }

        String sectionsWithFiles = doc.get(Constants.Fields.SECTIONS_WITH_FILES);
        studyInfo.set("columns", fileColumnAttributes);
        studyInfo.put(Constants.Fields.FILES, doc.get(Constants.Fields.FILES));
        studyInfo.put("ftpLink", indexConfig.getFtpDir() +  doc.get(Constants.Fields.RELATIVE_PATH));
        studyInfo.put("isPublic", (" " + doc.get(Constants.Fields.ACCESS) + " ").toLowerCase().contains(" public ")  );
        studyInfo.put(Constants.Fields.RELATIVE_PATH, relativePath);
        setPrivateData(studyInfo, doc);
        try {
            if (sectionsWithFiles!=null) {
                studyInfo.set("sections", mapper.readTree("[\"" +
                        sectionsWithFiles.replaceAll(" ", "\",\"")
                        + "\"]"));
            }
        } catch (Exception e) {
            logger.error("Error retrieving sections with files");
            studyInfo.put("sections","[]");
        }
        return studyInfo;
    }

    private ObjectNode getThumbnailHeader(ObjectMapper mapper){
        String thumbStr = "Thumbnail";
        ObjectNode node = mapper.createObjectNode();
        node.put("name", thumbStr);
        node.put("title", thumbStr);
        node.put("visible", true);
        node.put("searchable", false);
        node.put("sortable", false);
        node.put("defaultContent", "");
        return node;
    }


    private void setPrivateData(ObjectNode studyInfo, Document doc){
        User currentUser = Session.getCurrentUser();
        if( !(doc.get(Constants.Fields.ACCESS) + " ").toLowerCase().contains(" public ")
                || (currentUser!=null && currentUser.isSuperUser())) {
            IndexableField key = doc.getField(Constants.Fields.SECRET_KEY);
            if (key!=null) {
                studyInfo.put(Constants.Fields.SECRET_KEY, key.stringValue());
            }
            try {
                studyInfo.put("modified", Long.parseLong(doc.get(Constants.Fields.MODIFICATION_TIME)));
            }catch(Throwable error){
                //do nothing
            }
        }

    }


}
