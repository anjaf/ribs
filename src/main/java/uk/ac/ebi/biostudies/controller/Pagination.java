package uk.ac.ebi.biostudies.controller;


import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.api.util.DataTableColumnInfo;
import uk.ac.ebi.biostudies.api.util.PublicRESTMethod;
import uk.ac.ebi.biostudies.service.FilePaginationService;

import java.util.Map;
import java.util.Set;

import static uk.ac.ebi.biostudies.api.util.Constants.JSON_UNICODE_MEDIA_TYPE;

@Api(value="api", description="Rest endpoint for searching and retrieving Biostudies")
@RestController
@RequestMapping(value="/api/v1")
public class Pagination {
    private Logger logger = LogManager.getLogger(Pagination.class.getName());

    @Autowired
    FilePaginationService paginationService;

    @PublicRESTMethod
    @RequestMapping(value = "/filelist", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.POST)
    public String search(@RequestParam(value="acc", required=false, defaultValue = "") String accession,
                         @RequestParam(value="start", required=false, defaultValue = "0") Integer start,
                         @RequestParam(value="length", required=false, defaultValue = "5") Integer pageSize,
                         @RequestParam(value="search[value]", required=false, defaultValue = "") String search,
                         @RequestParam(value="draw", required=false, defaultValue = "1") Integer draw,
                         @RequestParam MultiValueMap<String,String> order
                        ) throws Exception
    {
        Set<String> keySet = order.keySet();
        Map parseResult = DataTableColumnInfo.ParseDataTableRequest(order);
        return paginationService.getFileList(accession, start, pageSize, search, draw, parseResult);
    }

    @PublicRESTMethod
    @RequestMapping(value = "/headers/{accession}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.GET)
    public String getColumns(@PathVariable String accession){
        return paginationService.getColumns(accession);

    }

}
