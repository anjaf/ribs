package uk.ac.ebi.biostudies.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.SearchService;
import java.net.URLDecoder;


import static java.nio.charset.StandardCharsets.UTF_8;
import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;

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

    @RequestMapping(value = "/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String search(@RequestParam(value="query", required=false, defaultValue = "*:*") String queryString,
                                        @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                        @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                                        @RequestParam(value="sortBy", required=false, defaultValue = "relevance") String sortBy,
                                        @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder
    ) throws Exception {
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), null, null, page, pageSize, sortBy, sortOrder);
//        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{project}/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getHecatosSelectedFacets(@RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                           @RequestParam(value="facets", required=false) String facets,
                                           @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                           @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                                           @RequestParam(value="sortBy", required=false, defaultValue = "relevance") String sortBy,
                                           @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder,
                                           @PathVariable String project) throws Exception {
//        ObjectMapper objectContainer = new ObjectMapper();
//        ArrayNode organNa =mapper.createArrayNode();
//        organNa.add("heart");
//        organNa.add("n/a");
//        objectContainer.set("organ", organNa);
//        ArrayNode raw =mapper.createArrayNode();
//        raw.add("raw");
//        raw.add("processed");
//        objectContainer.set("rawprocessed",raw);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode selectedFacets = null;
        selectedFacets = mapper.readTree(facets);
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, project, page, pageSize, sortBy, sortOrder);
    }

    @RequestMapping(value = "/{prjname}/default", produces = JSON_UNICODE_MEDIA_TYPE , method = RequestMethod.GET)
    public String getHecatosDefaultFacets(@PathVariable String prjname) throws Exception{
        return facetService.getDefaultFacetTemplate(prjname).toString();
    }
}
