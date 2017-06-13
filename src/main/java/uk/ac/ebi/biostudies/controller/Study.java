package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    //TODO: stream file directly
    public ResponseEntity<String> search(@PathVariable("accession") String accession)  {
        if(!searchService.isAccessible(accession)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found!\"}");
        }
        String result = null;
        try {
            result = searchService.getDetailFile(accession.replace("..",""));
        } catch (IOException e) {
            logger.error(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found!\"}");
        }
        return ResponseEntity.ok(result);
    }
}
