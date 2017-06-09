package uk.ac.ebi.biostudies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.file.ZipStatusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ehsan on 23/03/2017.
 */

@RestController
@RequestMapping(value="/{datacenter}/zipstatus")
public class ZipStatus {

    @Autowired
    ZipStatusService zipStatusService;

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public void getFilesInZipedFormat(HttpServletRequest request, HttpServletResponse response) throws Exception{
        zipStatusService.doRequest(request, response);
    }
}
