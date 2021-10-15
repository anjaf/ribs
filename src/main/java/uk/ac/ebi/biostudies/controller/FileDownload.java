package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.service.FileDownloadService;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.SubmissionNotAccessibleException;
import uk.ac.ebi.biostudies.service.ZipDownloadService;
import uk.ac.ebi.biostudies.service.impl.BatchDownloadScriptBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by ehsan on 22/03/2017.
 */
@RestController
public class FileDownload {

    private static final Logger LOGGER = LogManager.getLogger(FileDownload.class.getName());

    @Autowired
    ZipDownloadService zipDownloadService;

    @Autowired
    FileDownloadService fileDownloadService;

    @Autowired
    SearchService searchService;

    @Autowired
    IndexConfig indexConfig;

    @Autowired
    BatchDownloadScriptBuilder batchDownloadScriptBuilder;

    @RequestMapping(value = "/files/**", method = RequestMethod.POST)
    public void getFilesInZippedFormat(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String dlType = request.getParameter("type");
        String os = request.getParameter("os");
        if(os==null || os.isEmpty())
            os="unix";
        String fileExtension = "sh";
        fileExtension = getFileExtension(os);
        List<String> fileNames = new ArrayList<>();
        Document luceneDoc = getFilePaths(request,response, fileNames);
        String relativeBaseDir = luceneDoc.get(Constants.Fields.RELATIVE_PATH);
        String accession = luceneDoc.get(Constants.Fields.ACCESSION);
        dlType = dlType.replaceFirst("/", "");
        String[] files = request.getParameterMap().get("files");
        if(dlType.equalsIgnoreCase("zip"))
            zipDownloadService.sendZip(request, response, files);
        else if(dlType.equalsIgnoreCase("ftp") || dlType.equalsIgnoreCase("aspera")){
            response.setContentType("application/txt");
            response.addHeader("Content-Disposition", "attachment; filename="+accession+"-" + os+"-"+dlType+"."+fileExtension);
            response.addHeader("Cache-Control", "no-cache");
            response.getOutputStream().print(batchDownloadScriptBuilder.fillTemplate(dlType, fileNames, relativeBaseDir, os));
            response.getOutputStream().close();
        }

    }

    @RequestMapping(value = "/files/**", method = RequestMethod.GET)
    public void getSingleFile(HttpServletRequest request, HttpServletResponse response) throws Exception, SubmissionNotAccessibleException {
        fileDownloadService.sendFile(request, response);
    }


    private  Document getFilePaths(HttpServletRequest request, HttpServletResponse response, List<String> fileNames) throws Exception {
        String[] args = request.getRequestURI().replaceAll(request.getContextPath()+"(/[a-zA-Z])?/files/"       ,"").split("/");
        String key = request.getParameter("key");
        if ("null".equalsIgnoreCase(key)) {
            key=null;
        }

        Document doc = null;
        try {
            doc = searchService.getSubmissionDocumentByAccession(args[0], key);
        } catch (SubmissionNotAccessibleException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        if(doc==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        String relativePath = doc.get(Constants.Fields.RELATIVE_PATH);

        if (relativePath==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        fileNames.addAll(Arrays.asList(request.getParameterMap().get("files")));
        return doc;

    }

    private String getFileExtension(String os){
        if(os.equalsIgnoreCase("windows"))
            return "bat";
        else return "sh";
    }
}
