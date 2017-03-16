package uk.ac.ebi.biostudies.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.SearchService;

import java.io.IOException;

/**
 * Created by awais on 14/02/2017.
 */
@RestController
@RequestMapping(value="/api")
public class Search {

    private Logger logger = LogManager.getLogger(Search.class.getName());


    @Autowired
    SearchService searchService;
    @Autowired
    FacetService facetService;

    @RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String search(@RequestParam(value="query", required=false, defaultValue = "*:*") String queryString,
                                         @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                        @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize
    ){
        return searchService.search(queryString, page, pageSize);
//        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{project}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getHecatosSelectedFacets(@RequestParam(value="query", required=false, defaultValue = "content:hecatos") String queryString,
                                           @RequestParam(value="facets", required=false) String facets,
                         @RequestParam(value="page", required=false, defaultValue = "1") Integer page, @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode objectContainer = null;
        try {
            objectContainer = mapper.readTree(facets);
        } catch (IOException e) {
           logger.debug(e);
        }
//        ArrayNode organNa =mapper.createArrayNode();
//        organNa.add("heart");
//        organNa.add("n/a");
//        objectContainer.set("organ", organNa);
//        ArrayNode raw =mapper.createArrayNode();
//        raw.add("raw");
//        raw.add("processed");
//        objectContainer.set("rawprocessed",raw);
        QueryParser qp = new QueryParser(BioStudiesField.PROJECT.toString(), new SimpleAnalyzer());
        Query fq = null;
        try {
            fq = qp.parse(queryString);
        } catch (ParseException e) {
            logger.debug(e);
        }

        fq = searchService.applyFacets(fq, objectContainer);
        String result = searchService.applySearchOnQuery(fq, page, pageSize);
        return result;
    }

    @RequestMapping(value = "/heca/default", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getHecatosDefaultFacets() {
        return facetService.getHecatosFacets().toString();
    }

}
