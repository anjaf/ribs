package uk.ac.ebi.biostudies.controller;

/**
 * Created by ehsan on 25/05/2017.
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.service.SearchService;

import static uk.ac.ebi.biostudies.api.util.Constants.STRING_UNICODE_MEDIA_TYPE;


@RestController
@RequestMapping(value="/api/v1/autocomplete")
public class AutoCompleteController {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/keywords", produces = STRING_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getKeywords(@RequestParam(value="query", defaultValue = "") String query,
                              @RequestParam(value="field", required=false, defaultValue = "") String field,
                              @RequestParam(value="limit", required=false, defaultValue = "10") Integer limit){
        if(query==null || query.isEmpty())
            return "";

        return searchService.getKeywords(query, field, limit);
    }

    @RequestMapping(value = "/efotree", produces = STRING_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getEfoTree(@RequestParam(value="efoid", defaultValue = "") String efoid){
        if(efoid==null || efoid.isEmpty())
            return "";

        return searchService.getEfoTree(efoid);
    }



}
