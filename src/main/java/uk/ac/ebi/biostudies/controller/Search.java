package uk.ac.ebi.biostudies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.*;
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


import static java.nio.charset.StandardCharsets.UTF_8;
import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;

/**
 * Created by awais on 14/02/2017.
 * <p>
 * Rest endpoint for searching Biostudies
 */

@Api(value = "api", description = "Rest endpoint for searching and retrieving Biostudies")
@RestController
@RequestMapping(value = "/api/v1")
public class Search {

    private Logger logger = LogManager.getLogger(Search.class.getName());

    @Autowired
    SearchService searchService;
    @Autowired
    FacetService facetService;

    @ApiOperation(value = "Returns search result", notes = "Search your query in Biostudies and return the results")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns a JSON object with search results")})
    @PublicRESTMethod
    @ApiModelProperty(hidden = true)
    @RequestMapping(value = "/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String search(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                         @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
                         @RequestParam(value = "sortOrder", required = false, defaultValue = "descending") String sortOrder,
                         @RequestParam MultiValueMap<String, String> params
    ) throws Exception {
        String queryString = params.getFirst("query");
        params.remove("query");
        queryString = queryString == null ? "" : queryString;
        ObjectNode selectedFacets = checkSelectedFacetsAndFields(params);
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, null, page, pageSize, sortBy, sortOrder);
    }


    @ApiOperation(value = "Returns search results for {project}", notes = "Returns search results for {project}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Returns a JSON object with search results.")})
    @PublicRESTMethod
    @RequestMapping(value = "/{project}/search", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String searchProject(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
                                @RequestParam(value = "sortOrder", required = false, defaultValue = "descending") String sortOrder,
                                @RequestParam MultiValueMap<String, String> params,
                                @PathVariable String project) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacetsAndFields(params);
        String queryString = params.getFirst("query");
        params.remove("query");
        queryString = queryString == null ? "" : queryString;
        return searchService.search(URLDecoder.decode(queryString, String.valueOf(UTF_8)), selectedFacets, project, page, pageSize, sortBy, sortOrder);
    }


    @RequestMapping(value = "/latest", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getLatestStudies() throws Exception {
        return searchService.search(URLDecoder.decode(Constants.Fields.TYPE + ":Study -" + Constants.RELEASE_DATE + ":20500101", String.valueOf(UTF_8)), null, null, 1, 5, Constants.Fields.RELEASE_TIME, Constants.SortOrder.DESCENDING);
    }


    @RequestMapping(value = "/{project}/facets", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getDefaultFacets(@PathVariable String project,
                                   @RequestParam(value = "query", required = false, defaultValue = "") String queryString,
                                   @RequestParam(value = "limit", required = false, defaultValue = "" + Constants.TOP_FACET_COUNT) Integer limit,
                                   @RequestParam MultiValueMap<String, String> params
    ) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacetsAndFields(params);
        return facetService.getDefaultFacetTemplate(project, queryString, limit, selectedFacets).toString();
    }


    @RequestMapping(value = "/{project}/facets/{dimension}/", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getDefaultFacets(@PathVariable String project,
                                   @PathVariable String dimension,
                                   @RequestParam(value = "query", required = false, defaultValue = "") String queryString,
                                   @RequestParam MultiValueMap<String, String> params) throws Exception {
        ObjectNode selectedFacets = checkSelectedFacetsAndFields(params);
        return facetService.getDimension(project, dimension, queryString, selectedFacets).toString();
    }


    @RequestMapping(value = "/stats", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getStats() throws Exception {
        return searchService.getFieldStats();
    }


    private ObjectNode checkSelectedFacetsAndFields(MultiValueMap<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode allSelected = mapper.createObjectNode();
        ObjectNode selectedFacets = mapper.createObjectNode();
        ObjectNode selectedFields = mapper.createObjectNode();
        if (params != null) {
            for (String key : params.keySet()) {
                if (key.equalsIgnoreCase("pageSize") || key.equalsIgnoreCase("page") || key.equalsIgnoreCase("sortBy") || key.equalsIgnoreCase("sortOrder") || key.equalsIgnoreCase("query") || key.equalsIgnoreCase("limit"))
                    continue;
                if (!key.toLowerCase().contains("facet.")) {
                    selectedFields.put(key, params.getFirst(key));
                } else {
                    String facetKey = StringUtils.remove(key, "[]");
                    if (!selectedFacets.has(facetKey)) {
                        selectedFacets.set(facetKey, mapper.createArrayNode());
                    }
                    for (String value : params.get(key)) {
                        ((ArrayNode) selectedFacets.get(facetKey)).add(value);
                    }
                }
            }
        }
        allSelected.set("facets", selectedFacets);
        allSelected.set("fields", selectedFields);
        return allSelected;
    }
}
