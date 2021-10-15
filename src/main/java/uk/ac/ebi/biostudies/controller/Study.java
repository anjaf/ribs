package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.PublicRESTMethod;
import uk.ac.ebi.biostudies.service.FilePaginationService;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.SubmissionNotAccessibleException;
import uk.ac.ebi.biostudies.service.SubmissionService;
import uk.ac.ebi.biostudies.service.impl.SubmissionReaderFactory;

import java.io.IOException;

import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;


/**
 * Created by awais on 14/02/2017.
 */
@RestController
@RequestMapping(value="/api/v1")
public class Study {

    private Logger logger = LogManager.getLogger(Study.class.getName());

    @Autowired
    SearchService searchService;
    @Autowired
    FilePaginationService paginationService;
    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionReaderFactory submissionReaderFactory;

    @RequestMapping(value = "/studies/{accession:.+}", produces = {JSON_UNICODE_MEDIA_TYPE}, method = RequestMethod.GET)
    public ResponseEntity<String> getStudy(@PathVariable("accession") String accession, @RequestParam(value="key", required=false) String seckey)  {
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        Document document = null;
        try {
            document = searchService.getSubmissionDocumentByAccession(accession, seckey);
        } catch (SubmissionNotAccessibleException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"errorMessage\":\"Study not accessible\"}");
        }
        if(document==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found\"}");
        }
        InputStreamResource result;
        try {
            result = submissionService.getStudyAsStream(document, seckey);
        } catch (IOException e) {
            logger.error(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found\"}");
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/studies/{accession:.+}/similar", produces = {JSON_UNICODE_MEDIA_TYPE}, method = RequestMethod.GET)
    public ResponseEntity<String> getSimilarStudies(@PathVariable("accession") String accession,
                                                    @RequestParam(value="key", required=false) String seckey)
            throws  Exception {
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        try {
            Document document = searchService.getSubmissionDocumentByAccession(accession, seckey);
            if(document!=null) {
                accession = document.get(Constants.Fields.ACCESSION);
                ResponseEntity result =  new ResponseEntity(searchService.getSimilarStudies(accession.replace("..",""), seckey), HttpStatus.OK);
                return result;
            }
        } catch (SubmissionNotAccessibleException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"errorMessage\":\"Study not accessible\"}");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body("{\"similarStudies\":[]}");

    }

    @PublicRESTMethod
    @RequestMapping(value = "/studies/{accession:.+}/info", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> getStudyInfo(@PathVariable String accession, @RequestParam(value="key", required=false) String seckey){
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(submissionService.getStudyInfo(accession, seckey).toString());
        } catch (SubmissionNotAccessibleException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("{\"errorMessage\":\"Study not accessible\"}");
        }

    }



}
