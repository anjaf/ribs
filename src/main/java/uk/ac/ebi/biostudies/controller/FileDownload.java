package uk.ac.ebi.biostudies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.biostudies.file.FileDownloadService;
import uk.ac.ebi.biostudies.file.ZipDownloadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ehsan on 22/03/2017.
 */
@RestController
public class FileDownload {

    @Autowired
    ZipDownloadService zipDownloadService;

    @Autowired
    FileDownloadService fileDownloadService;

    @RequestMapping(value = "/files/**", method = RequestMethod.POST)
    public void getFilesInZippedFormat(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value="key", required=false, defaultValue = "") String seckey) throws Exception{
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        zipDownloadService.doRequest(request, response, seckey);
    }

    @RequestMapping(value = "/{datacenter}/files/**", method = RequestMethod.GET)
    public void downloadZippedFile(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value="key", required=false, defaultValue = "") String seckey) throws Exception{
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        zipDownloadService.doRequest(request, response, seckey);
    }

    @RequestMapping(value = "/files/**", method = RequestMethod.GET)
    public void getSingleFile(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value="key", required=false, defaultValue = "") String seckey) throws Exception{
        if ("null".equalsIgnoreCase(seckey)) {
            seckey = null;
        }
        fileDownloadService.doRequest(request, response, seckey);
    }
}
