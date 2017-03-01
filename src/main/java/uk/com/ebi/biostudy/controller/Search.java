package uk.com.ebi.biostudy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.com.ebi.biostudy.service.SearchService;

/**
 * Created by awais on 14/02/2017.
 */
@RestController
@RequestMapping(value="/api")
public class Search {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String search(@RequestParam(value="query", required=false) String queryString,
                                         @RequestParam(value="page", required=false, defaultValue = "1") Integer page, @RequestParam(value="pageSize", required=false, defaultValue = "20") Integer pageSize){
        return searchService.search(queryString, page, pageSize);
//        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }
}
