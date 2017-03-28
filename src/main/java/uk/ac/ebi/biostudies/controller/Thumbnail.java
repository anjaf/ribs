package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.api.util.StudyUtils;
import uk.ac.ebi.biostudies.file.Thumbnails;
import uk.ac.ebi.biostudies.service.SearchService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ehsan on 20/03/2017.
 */

@RestController
@RequestMapping(value="/thumbnail")
public class Thumbnail {

    private Logger logger = LogManager.getLogger(Thumbnail.class.getName());


    @Autowired
    Thumbnails thumbnails;
    @Autowired
    SearchService searchService;


    /**
     * TODO UI should pass correct related path to the server, in the previous version It calculated from xml sax transformations but in current version ui has this data in json
     * @param response
     * @param accession
     * @param name
     */
    @RequestMapping(value = "/{accession}/{name:.+}", method = RequestMethod.GET)
    public void getThumbnail(HttpServletResponse response, @PathVariable String accession, @PathVariable String name) {
        if(accession==null || accession.isEmpty() || name==null || name.isEmpty())
            return;

        try {//Maybe I need to apply some modification to change accession to relative path
            if(!searchService.isAccessible(accession)){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String relativePath = StudyUtils.getPartitionedPath(accession);
            thumbnails.sendThumbnail(response, relativePath, name);
        } catch (IOException e) {
            logger.error("problem in creating thumbnail ", e);
        }
    }
}
