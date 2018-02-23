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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.PublicRESTMethod;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.SearchService;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.stream.Collectors;


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
                         @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                         @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                         @RequestParam(value="sortBy", required=false, defaultValue = "") String sortBy,
                         @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder,
                         @RequestParam MultiValueMap<String,String> params
    ) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacets(params);
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
        return searchService.search(URLDecoder.decode("type:Study -release_date:20500101", String.valueOf(UTF_8)),
                null, null, 1, 5, Constants.Fields.RELEASE_TIME, "descending");
    }

    @ApiOperation(value = "Returns results for selected facets", notes = "Returns results for selected facets by user interface", response = ObjectNode.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search results for selected facets", response = ObjectNode.class)
    })
    @PublicRESTMethod
    @RequestMapping(value = "/{project}/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String searchProject(@RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                @RequestParam(value="facets", required=false) String facets,
                                @RequestParam(value="page", required=false, defaultValue = "1") Integer page,
                                @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize,
                                @RequestParam(value="sortBy", required=false, defaultValue = "") String sortBy,
                                @RequestParam(value="sortOrder", required=false, defaultValue = "descending") String sortOrder,
                                @RequestParam MultiValueMap<String,String> params,
                                @PathVariable String project) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacets(params);
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, project, page, pageSize, sortBy, sortOrder);
    }

    @RequestMapping(value = "/{project}/facets", produces = JSON_UNICODE_MEDIA_TYPE , method = RequestMethod.GET)
    public String getDefaultFacets(@PathVariable String project,
                                   @RequestParam(value="query", required=false, defaultValue = "") String queryString,
                                   @RequestParam(value="limit", required=false, defaultValue = ""+Constants.TOP_FACET_COUNT) Integer limit,
                                   @RequestParam MultiValueMap<String,String> params
    ) throws Exception{
        ObjectNode selectedFacets = checkSelectedFacets(params);
        return facetService.getDefaultFacetTemplate(project, queryString, limit, selectedFacets).toString();
    }

    @RequestMapping(value = "/{project}/facets/{dimension}/", produces = JSON_UNICODE_MEDIA_TYPE , method = RequestMethod.GET)
    public String getDefaultFacets(@PathVariable String project, @PathVariable String dimension) throws Exception{
        return facetService.getDimension(project, dimension).toString();
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

    private ObjectNode checkSelectedFacets(MultiValueMap<String, String> params){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode selectedFacets = mapper.createObjectNode();
        if (params!=null) {
            for (String facet: params.keySet()) {
                if (!facet.toLowerCase().startsWith("facet.")) continue;
                String facetKey = StringUtils.remove(facet, "[]");
                if (!selectedFacets.has( facetKey )) {
                    selectedFacets.set(facetKey, mapper.createArrayNode());
                }
                for (String value: params.get(facet).stream().flatMap( v-> Arrays.stream(v.split(",")) ).collect(Collectors.toList()) ) {
                    ((ArrayNode) selectedFacets.get(facetKey)).add ( value );
                }
            }
        }
        return selectedFacets;
    }
}
