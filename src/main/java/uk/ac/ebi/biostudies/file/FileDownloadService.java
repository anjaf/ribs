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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.file.download.BaseDownloadServlet;
import uk.ac.ebi.biostudies.file.download.IDownloadFile;
import uk.ac.ebi.biostudies.file.download.RegularDownloadFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FileDownloadService extends BaseDownloadServlet{

    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IndexConfig indexConfig;


    protected IDownloadFile getDownloadFileFromRequest(
            HttpServletRequest request
            , HttpServletResponse response
            , String relativePath
    ) throws DownloadServletException {
        String accession = "";
        String name = "";
        IDownloadFile file = null;

        try {
            List<String> requestArgs = new ArrayList<>(Arrays.asList(
                    request.getRequestURI().replaceAll(request.getContextPath()+"/files/"       ,"").split("/")));
            if (requestArgs.size() == 1) { // name only passed
                name = requestArgs.get(0);
            } else if (requestArgs.size() >1 ) { // accession/name passed
                accession = requestArgs.remove (0);
                name = URLDecoder.decode(
                        StringUtils.replace( StringUtils.join(requestArgs,'/') , "..", "")
                            , StandardCharsets.UTF_8.toString());
            }

            logger.info("Requested download of [" + name + "], path [" + relativePath + "]");

            if (name.equalsIgnoreCase(accession+".json") || name.equalsIgnoreCase(accession+".xml") || name.equalsIgnoreCase(accession+".pagetab.tsv") ) {
                file = new RegularDownloadFile(new File(indexConfig.getFileRootDir(), relativePath + "/" + name));
            } else {
                File downloadFile = new File(indexConfig.getFileRootDir(), relativePath + "/Files/" + name);
                if (!downloadFile.exists()) { //TODO: Remove this bad^∞ hack
                    logger.info( "{0} not found ", downloadFile.getAbsolutePath());
                    downloadFile = new File(indexConfig.getFileRootDir(), relativePath + "/Files/u/" + name);
                    logger.info( "Trying ", downloadFile.getAbsolutePath());
                }
                if (downloadFile.exists()) {
                    if (downloadFile.isDirectory()) {
                        String forwardedParams = String.format("?files=%s", URLEncoder.encode(name, "UTF-8"));
                        //TODO update the forward url
                        request.getRequestDispatcher("/servlets/download/zip/" + accession + forwardedParams).forward(request, response);
                        return null;
                    }
                    file = new RegularDownloadFile(downloadFile);

                } else {
                    throw new DownloadServletException("Could not open " + downloadFile.getAbsolutePath());
                }
            }


        } catch (Exception x) {
            throw new DownloadServletException(x);
        }
        return file;
    }

}
