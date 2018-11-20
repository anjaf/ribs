/*
 * Copyright 2009-2016 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package uk.ac.ebi.biostudies.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.IndexConfig;

import uk.ac.ebi.biostudies.file.download.BaseDownloadServlet;
import uk.ac.ebi.biostudies.file.download.IDownloadFile;
import uk.ac.ebi.biostudies.file.download.RegularDownloadFile;
import uk.ac.ebi.biostudies.file.download.ZipperThread;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ZipDownloadService extends BaseDownloadServlet{

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(ZipDownloadService.class.getName());


    @Autowired
    IndexConfig indexConfig;
    @Autowired
    ZipStatusService zipStatusService;

    @Override
    protected void doBeforeDownloadFileFromRequest(HttpServletRequest request, HttpServletResponse response, String relativePath) throws DownloadServletException {

        // set filename and accession
        String[] requestArgs = request.getRequestURI().replaceFirst("/files/", "").split("/");
        String accession = relativePath.substring(relativePath.lastIndexOf('/')+1);
        String[] filenames = request.getParameterMap().get("files");

        if (filenames != null) { // first hit: We have to zip the files
            long fileSizeSum = 0;
            boolean isDirectory =false ;
            for (String filename : filenames) {
                String fqName = indexConfig.getFileRootDir() + "/" + relativePath + "/Files/" + StringUtils.replace(filename,"..","");
                File thisFile = new File(fqName);
                fileSizeSum += thisFile.length();
                if (thisFile.isDirectory()) {
                    isDirectory = true;
                    break;
                }
            }
            // Threshold for large files which will be available for 24 hours.
            // Since we don't know the size of a directory, just treat is as a large file.
            boolean isLargeFile = isDirectory || fileSizeSum > 200* IDownloadFile.MB;
            String uuid = UUID.randomUUID().toString();
            try {
                if (isLargeFile) {
                    Thread thread = new ZipperThread(filenames, relativePath, uuid, null, indexConfig.getZipTempDir(), indexConfig.getFileRootDir(), zipStatusService);
                    thread.start();
                    String datacentre = System.getProperty("datacentre") == null ? "lc" : System.getProperty("datacentre");
                    String forwardedParams = String.format("?uuid=%s&accession=%s&dc=%s",
                            URLEncoder.encode(uuid, "UTF-8"),
                            URLEncoder.encode(accession, "UTF-8"),
                            URLEncoder.encode(datacentre.substring(0, 1), "UTF-8"));
                    zipStatusService.addFile(uuid);
                    response.sendRedirect( request.getContextPath()+ "/zip" + forwardedParams);
                    return;
                } else {
                    // File is not large. Send over the zipped stream
                    response.setContentType("application/zip");
                    response.addHeader("Content-Disposition", "attachment; filename="+ accession+".zip");
                    Thread thread = new ZipperThread(filenames, relativePath, uuid,response.getOutputStream(), indexConfig.getZipTempDir(), indexConfig.getFileRootDir(), zipStatusService);
                    thread.start();
                    thread.join();
                }
            } catch (Exception e) {
                throw new DownloadServletException(e);
            }

        }

    }

    @Override
    protected IDownloadFile getDownloadFileFromRequest(
            HttpServletRequest request
            , HttpServletResponse response
            , String relativePath
    ) throws DownloadServletException {

        String path=null;
        String[] requestArgs = request.getRequestURI().replaceAll(request.getContextPath()+"(/[a-zA-Z])?/files/"       ,"").split("/");
        String accession = requestArgs[0];


        if (request.getParameter("file")!=null) { // async request
            String uuid = UUID.fromString(request.getParameter("file")).toString();
            path = indexConfig.getZipTempDir()+"/"+uuid + ".zip";
        } else if (request.getAttribute("zipFile")!=null) {
            path = (String) request.getAttribute("zipFile");
        }
        if (path==null) return null;
        return (path!=null) ? new RegularDownloadFile(Paths.get(path), accession+".zip") : null;
    }

    @Override
    protected void doAfterDownloadFileFromRequest(HttpServletRequest request, HttpServletResponse response) throws DownloadServletException {
        if (request.getAttribute("zipFile") != null) {
            File zipFile = new File( (String)request.getAttribute("zipFile"));
            zipFile.delete();
            logger.info("Zip file {} deleted", zipFile.getName());
        }
    }

}
