package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.impl.IndexServiceImpl;

import java.io.*;


/**
 * Created by awais on 14/02/2017.
 */
@RestController
@RequestMapping(value="/api")
public class Study {

    private Logger logger = LogManager.getLogger(IndexServiceImpl.class.getName());

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/studies/{accession:.+}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<String> search(@PathVariable("accession") String accession)  {
        //TODO: check access
        String result = null;
        try {
            result = searchService.getDetailFile(accession);
        } catch (IOException e) {
            logger.error(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body("{\"errorMessage\":\"Study not found!\"}");
        }
        return ResponseEntity.ok(result);
    }
}
