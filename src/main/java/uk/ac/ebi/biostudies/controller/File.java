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
public class File {
    private Logger logger = LogManager.getLogger(File.class.getName());

    @Autowired
    FilePaginationService paginationService;

    @RequestMapping(value = "/files/{accession:.+}", produces = JSON_UNICODE_MEDIA_TYPE, method = RequestMethod.POST)
    public String search(@PathVariable(value="accession") String accession,
                         @RequestParam(value="start", required=false, defaultValue = "0") Integer start,
                         @RequestParam(value="length", required=false, defaultValue = "5") Integer pageSize,
                         @RequestParam(value="search[value]", required=false, defaultValue = "") String search,
                         @RequestParam(value="draw", required=false, defaultValue = "1") Integer draw,
                         @RequestParam(value="metadata", required=false, defaultValue = "true") boolean metadata,
                         @RequestParam MultiValueMap<String,String> order,
                         @RequestParam(value="key", required=false) String seckey
    ) throws Exception
    {
        Map parseResult = DataTableColumnInfo.ParseDataTableRequest(order);
        return paginationService.getFileList(accession, start, pageSize, search, draw, metadata, parseResult, seckey);
    }

}
