package uk.ac.ebi.biostudies.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.Charsets;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

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
                                        @RequestParam(value="pagesize", required=false, defaultValue = "20") Integer pagesize,
                                        @RequestParam(value="sortby", required=false, defaultValue = "") String sortBy,
                                        @RequestParam(value="sortorder", required=false, defaultValue = "") String sortOrder
    ) throws UnsupportedEncodingException {
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), page, pagesize, sortBy, sortOrder);
//        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{project}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getHecatosSelectedFacets(@RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                           @RequestParam(value="facets", required=false) String facets,
                                           @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                           @RequestParam(value="pagesize", required=false, defaultValue = "20") Integer pageSize,
                                           @RequestParam(value="sortby", required=false, defaultValue = "") String sortBy,
                                           @RequestParam(value="sortorder", required=false, defaultValue = "") String sortOrder,
                                           @PathVariable String project) throws ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode selectedFacets = null;
        String prjSearch = "(content:+"+project+")";
        try {
            selectedFacets = mapper.readTree(facets);
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
        QueryParser qp = new QueryParser(BioStudiesField.PROJECT.toString(), BioStudiesField.PROJECT.getAnalyzer());
        Query fq = null;
        try {
            fq = qp.parse(queryString);
        } catch (ParseException e) {
            logger.debug(e);
        }

        fq = searchService.applyFacets(fq, selectedFacets);
        JsonNode result = searchService.applySearchOnQuery(fq, page, pageSize, sortBy, sortOrder);
        return result.toString();
    }

    @RequestMapping(value = "/{prjname}/default", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getHecatosDefaultFacets(@PathVariable String prjname) {
        return facetService.getDefaultFacetTemplate(prjname).toString();
    }
}
