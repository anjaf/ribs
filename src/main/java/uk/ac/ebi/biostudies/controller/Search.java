package uk.ac.ebi.biostudies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.PublicRESTMethod;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.SearchService;
import java.net.URLDecoder;


import static java.nio.charset.StandardCharsets.UTF_8;
import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;

/**
 * Created by awais on 14/02/2017.
 *
 * Rest endpoint for searching Biostudies
 */

@Api(value="api", description="Rest endpoint for searching and retrieving Biostudies")
@RestController
@RequestMapping(value="/api/v1")
public class Search {

    private Logger logger = LogManager.getLogger(Search.class.getName());

    @Autowired
    SearchService searchService;
    @Autowired
    FacetService facetService;

    @ApiOperation(value = "Returns search result", notes = "Search your query in Biostudies and return the results", response = ObjectNode.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "JsonObject contains search results", response = ObjectNode.class)
    })
    @PublicRESTMethod
    @RequestMapping(value = "/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String search(@RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                        @RequestParam(value="facets", required=false) String facets,
                                        @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                        @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                                        @RequestParam(value="sortBy", required=false, defaultValue = "") String sortBy,
                                        @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder
    ) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacets(facets);
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, null, page, pageSize, sortBy, sortOrder);
    }


    @ApiOperation(value = "Returns latest studies", notes = "", response = ObjectNode.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "JsonObject contains latest study results", response = ObjectNode.class)
    })
    @PublicRESTMethod
    @RequestMapping(value = "/latest", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getLatestStudies() throws Exception
    {
        return searchService.search(URLDecoder.decode("type:Study -release_date:20500101", String.valueOf(UTF_8)), null, null, 1, 5, "rdatelong", "descending");
    }

    @ApiOperation(value = "Returns results for selected facets", notes = "Returns results for selected facets by user interface", response = ObjectNode.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search results for selected facets", response = ObjectNode.class)
    })
    @PublicRESTMethod
    @RequestMapping(value = "/{project}/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getSelectedFacets(@RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                           @RequestParam(value="facets", required=false) String facets,
                                           @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                           @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                                           @RequestParam(value="sortBy", required=false, defaultValue = "") String sortBy,
                                           @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder,
                                           @PathVariable String project) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacets(facets);
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, project, page, pageSize, sortBy, sortOrder);
    }

    @RequestMapping(value = "/{project}/facets", produces = JSON_UNICODE_MEDIA_TYPE , method = RequestMethod.GET)
    public String getDefaultFacets(@PathVariable String project, @RequestParam(value="query", required=false, defaultValue = "") String queryString) throws Exception{
        return facetService.getDefaultFacetTemplate(project, queryString).toString();
    }

    @ApiOperation(value = "Returns index stats", notes = "Returns stats for indexed fields", response = ObjectNode.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stats for indexed fields", response = ObjectNode.class)
    })
    @PublicRESTMethod
    @RequestMapping(value = "/stats", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getStats() throws Exception {
        return searchService.getFieldStats();
    }

    private ObjectNode checkSelectedFacets(String facets){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode selectedFacets = mapper.createObjectNode();
        if (facets!=null) {
            for (String facet : StringUtils.split(facets, ",")) {
                String[] parts = facet.split(":");
                if (parts.length != 2) continue;
                if (!selectedFacets.has(parts[0])) {
                    selectedFacets.set(parts[0], mapper.createArrayNode());
                }
                ((ArrayNode) selectedFacets.get(parts[0])).add(parts[1]);
            }
        }
        return selectedFacets;
    }
}
