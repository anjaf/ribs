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
import uk.ac.ebi.biostudies.service.SearchService;

import java.io.*;

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

    @RequestMapping(value = "/studies/{accession:.+}", produces = {JSON_UNICODE_MEDIA_TYPE}, method = RequestMethod.GET)
    public ResponseEntity<String> getStudy(@PathVariable("accession") String accession, @RequestParam(value="key", required=false) String seckey)  {
        accession = searchService.getAccessionIfAccessible(accession, seckey);
        if(accession==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found!\"}");
        }
        InputStreamResource result;
        try {
            result = searchService.getStudyAsStream(accession.replace("..",""));
        } catch (IOException e) {
            logger.error(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found!\"}");
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/studies/{accession:.+}/similar", produces = {JSON_UNICODE_MEDIA_TYPE}, method = RequestMethod.GET)
    public ResponseEntity<String> getSimilarStudies(@PathVariable("accession") String accession)  {
        try {
            accession = searchService.getAccessionIfAccessible(accession);
            if(accession!=null) {
                ResponseEntity result =  new ResponseEntity(searchService.getSimilarStudies(accession.replace("..","")), HttpStatus.OK);
                return result;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body("{\"similarStudies\":[]}");

    }

    @RequestMapping(value = "/studies/{accession:.+}/info", produces = {MediaType.TEXT_PLAIN_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<String> getSecretKey(@PathVariable("accession") String accession, @RequestParam(value="key", required=false) String seckey)  {
        String securityKey="{}";
        try {
            accession = searchService.getAccessionIfAccessible(accession, seckey);
            if(accession!=null) {
                Document document =searchService.getStudyAsLuceneDocument(accession);
                if (document!=null && !document.get(Constants.Fields.ACCESS).toLowerCase().contains("public")) {
                    securityKey = "{\"key\":\""+document.get(Constants.Fields.SECRET_KEY)+"\"}";
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(securityKey);

    }
}
