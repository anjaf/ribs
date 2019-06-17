package uk.ac.ebi.biostudies.service.impl;

import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.file.download.BaseDownloadServlet;
import uk.ac.ebi.biostudies.file.download.IDownloadFile;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.service.ZipDownloadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class ZipDownloadServiceImpl implements ZipDownloadService {

    private Logger logger = LogManager.getLogger(ZipDownloadServiceImpl.class.getName());

    @Autowired
    SearchService searchService;

    @Autowired
    IndexConfig indexConfig;


    @Override
    public void sendZip(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String[] args = request.getRequestURI().replaceAll(request.getContextPath()+"(/[a-zA-Z])?/files/"       ,"").split("/");
        String key = request.getParameter("key");
        if ("null".equalsIgnoreCase(key)) {
            key=null;
        }

        Document doc = searchService.getDocumentByAccession(args[0], key);
        if(doc==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String accession = doc.get(Constants.Fields.ACCESSION);
        String relativePath = doc.get(Constants.Fields.RELATIVE_PATH);

        if (relativePath==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new Exception("File does not exist or user does not have the rights to download it.");
        }

        String[] files = request.getParameterMap().get("files");
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename="+ accession+".zip");
        String rootFolder = indexConfig.getFileRootDir();

        Stack<String> fileStack = new Stack<>();
        fileStack.addAll(Arrays.asList(files));
        byte[] buffer = new byte[4 * IDownloadFile.KB];
        try {
            try (ZipOutputStream zos = new ZipOutputStream( new BufferedOutputStream(response.getOutputStream()))) {
                String canonicalPath = rootFolder + "/" + relativePath + "/Files/";
                String envIndependentCanonicalPath = new File(canonicalPath).getCanonicalPath();
                while (!fileStack.empty()) {
                    final String filename = StringUtils.replace(fileStack.pop(),"..",".");
                    File file = new File(canonicalPath,filename);
                    if (!file.getCanonicalPath().startsWith(envIndependentCanonicalPath)) break;
                    if (!file.exists()) {
                        logger.debug( "{0} not found ", file.getAbsolutePath());
                        file = new File(canonicalPath+"u/", filename );
                        logger.debug( "Trying ", file.getAbsolutePath());
                    }
                    if (file.isDirectory()) {
                        fileStack.addAll(Collections2.transform(Arrays.asList(file.list()),
                                f -> filename+"/"+f));
                    } else if(file.exists()) {
                        ZipEntry entry = new ZipEntry(filename);
                        zos.putNextEntry(entry);
                        FileInputStream fin = new FileInputStream(file);
                        int length;
                        while ((length = fin.read(buffer)) > 0) {
                            zos.write(buffer, 0, length);
                        }
                        fin.close();
                        zos.closeEntry();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
