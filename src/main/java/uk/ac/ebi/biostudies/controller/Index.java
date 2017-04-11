package uk.ac.ebi.biostudies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.service.IndexService;

import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;


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
    @RequestMapping(value = "/index/reload/{filename}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> indexAll(String filename) throws Exception {
        if(filename == null || filename.isEmpty() || filename.equalsIgnoreCase(Constants.STUDIES_JSON_FILE) || filename.equalsIgnoreCase("default")) {
            indexService.clearIndex(false);
            filename = "";
        }
        indexService.updateFromJSONFile(filename);
        indexService.indexAll(filename);
        return new ResponseEntity<String>("{\"message\":\"Indexing started\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/index/clear", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> clearIndex() throws Exception {
        indexService.clearIndex(true);
        return new ResponseEntity<String>("{\"message\":\"Index empty\"}", HttpStatus.OK);
    }
    @RequestMapping(value = "/index/delete/{accession}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> deleteDoc(@PathVariable(required=false) String accession) throws Exception {
        indexService.deleteDoc(accession);
        return new ResponseEntity<String>("{\"message\":\"Index empty\"}", HttpStatus.OK);
    }
}
