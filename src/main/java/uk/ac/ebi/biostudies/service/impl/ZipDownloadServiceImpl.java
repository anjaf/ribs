package uk.ac.ebi.biostudies.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.file.download.FilteredMageTabDownloadStream;
import uk.ac.ebi.biostudies.file.download.IDownloadFile;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.SubmissionNotAccessibleException;
import uk.ac.ebi.biostudies.service.ZipDownloadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class ZipDownloadServiceImpl implements ZipDownloadService {

    @Autowired
    SearchService searchService;
    @Autowired
    IndexConfig indexConfig;
    @Autowired
    FireService fireService;
    private final Logger logger = LogManager.getLogger(ZipDownloadServiceImpl.class.getName());

    @Override
    public void sendZip(HttpServletRequest request, HttpServletResponse response, String[] files) throws Exception {

        String[] args = request.getRequestURI().replaceAll(request.getContextPath() + "(/[a-zA-Z])?/files/", "").split("/");
        String key = request.getParameter("key");
        if ("null".equalsIgnoreCase(key)) {
            key = null;
        }

        Document doc = null;
        try {
            doc = searchService.getDocumentByAccession(args[0], key);
        } catch (SubmissionNotAccessibleException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        if (doc == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String accession = doc.get(Constants.Fields.ACCESSION);
        String relativePath = doc.get(Constants.Fields.RELATIVE_PATH);

        if (relativePath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new Exception("File does not exist or user does not have the rights to download it.");
        }

        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=" + accession + ".zip");
        String rootFolder = indexConfig.getFileRootDir();

        Stack<String> fileStack = new Stack<>();
//        fileStack.addAll(Arrays.asList(files));
        byte[] buffer = new byte[4 * IDownloadFile.KB];
        try {
            try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()))) {
                String canonicalPath = relativePath + "/Files/";
                InputStream zipInputStream = null;
                fileStack = fireService.getAllDirectoryContent(Arrays.asList(files).stream().map(path -> canonicalPath + path).collect(Collectors.toList()));
                while (!fileStack.empty()) {
                    final String fileName = StringUtils.replace(fileStack.pop(), "..", ".");
                    try {
                        zipInputStream = fireService.getFireObjectInputStreamByPath(null, fileName);
                    } catch (FileNotFoundException notFoundException) {
//                        try {
//                            zipInputStream = fireService.getFireObjectInputStreamByPath(null, fileName.replace("/Files/","/Files/u/"));
//                        } catch (Exception exception){
                        logger.debug("corrupted zip file: {} not found ", canonicalPath + fileName);
//                        }
                    } catch (Throwable throwable) {
                        logger.debug("corrupted zip file: {} not found ", canonicalPath + fileName);
                    }
                    try {
                        String curFileName = fileName.replaceAll(canonicalPath, "");
                        ZipEntry entry = new ZipEntry(curFileName);
                        zos.putNextEntry(entry);
                        if (key != null) {
                            FilteredMageTabDownloadStream filteredMageTabDownloadStream =
                                    new FilteredMageTabDownloadStream(curFileName, zipInputStream);
                            if (filteredMageTabDownloadStream.isSupported()) {
                                zipInputStream = filteredMageTabDownloadStream.getInputStream();
                            }
                        }
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                    } finally {
//                        zipInputStream.close();
                        zos.closeEntry();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
