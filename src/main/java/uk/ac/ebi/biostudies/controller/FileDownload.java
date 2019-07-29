package uk.ac.ebi.biostudies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.file.FileDownloadService;
import uk.ac.ebi.biostudies.service.SubmissionNotAccessibleException;
import uk.ac.ebi.biostudies.service.ZipDownloadService;

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
    public void getFilesInZippedFormat(HttpServletRequest request, HttpServletResponse response) throws Exception{
        zipDownloadService.sendZip(request, response);
    }

    @RequestMapping(value = "/files/**", method = RequestMethod.GET)
    public void getSingleFile(HttpServletRequest request, HttpServletResponse response) throws Exception, SubmissionNotAccessibleException {
        fileDownloadService.doRequest(request, response);
    }
}
