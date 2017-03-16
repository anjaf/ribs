package uk.ac.ebi.biostudies.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.service.SearchService;

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
                                         @RequestParam(value="page", required=false, defaultValue = "1") Integer page, @RequestParam(value="pagesize", required=false, defaultValue = "20") Integer pagesize){
        return searchService.search(queryString, page, pagesize);
//        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }
}
