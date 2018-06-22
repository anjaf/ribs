package uk.ac.ebi.biostudies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.auth.UserSecurityService;
import uk.ac.ebi.biostudies.service.IndexService;

import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;


/**
 * Root resource (exposed at "myresource" path)
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping(value="/api/v1")
public class Index {

    private Logger logger = LogManager.getLogger(Index.class.getName());

    @Autowired
    IndexService indexService;

    @Autowired
    UserSecurityService userSecurity;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @RequestMapping(value = "/index/reload/{filename:.*}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> indexAll(@PathVariable("filename") String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        try {
            if (filename == null || filename.isEmpty() || filename.equalsIgnoreCase(Constants.STUDIES_JSON_FILE) || filename.equalsIgnoreCase("default"))
            {
                indexService.clearIndex(false);
                filename = Constants.STUDIES_JSON_FILE;
            }
            indexService.copySourceFile(filename);
            indexService.indexAll(filename);
            message.put ("message", "Indexing started for "+filename);
            return ResponseEntity.ok( mapper.writeValueAsString(message) );
        } catch (Exception e) {
            logger.error(e);
            message.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapper.writeValueAsString(message));
        }
    }

    @RequestMapping(value = "/index/clear", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> clearIndex() throws Exception {
           indexService.clearIndex(true);
        return new ResponseEntity<String>("{\"message\":\"Index empty\"}", HttpStatus.OK);
    }
    @RequestMapping(value = "/index/delete/{accession}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public ResponseEntity<String> deleteDoc(@PathVariable(required=false) String accession) throws Exception {
           indexService.deleteDoc(accession);
        return new ResponseEntity<String>("{\"message\":\""+accession+" deleted\"}", HttpStatus.OK);
    }
}
