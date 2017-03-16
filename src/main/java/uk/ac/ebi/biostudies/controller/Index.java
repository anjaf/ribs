package uk.ac.ebi.biostudies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.service.IndexService;


/**
 * Root resource (exposed at "myresource" path)
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping(value="/api")
public class Index {

    @Autowired
    IndexService indexService;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @RequestMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<String> indexAll() throws Exception {
        indexService.indexAll();
        return new ResponseEntity<String>("{\"message\":\"Indexing started\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/index/clear", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<String> clearIndex() throws Exception {
        indexService.clearIndex();
        return new ResponseEntity<String>("{\"message\":\"Index empty\"}", HttpStatus.OK);
    }
}
