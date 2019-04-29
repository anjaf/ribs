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

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.util.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.StudyUtils;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.file.thumbnails.IThumbnail;
import uk.ac.ebi.biostudies.file.thumbnails.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class Thumbnails {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String thumbnailsFolder;
    private Map<String, IThumbnail> thumbnailGenerators = new HashMap<>();

    @Autowired
    IndexConfig indexConfig;


    @PostConstruct
    public void init() {
        //register thumbnail generators
        //TODO: use ServiceLoader or annotations instead
        registerThumbnailHandler(new ImageThumbnail());
        registerThumbnailHandler(new PDFThumbnail());
        registerThumbnailHandler(new DOCXThumbnail());
        registerThumbnailHandler(new TXTThumbnail());
        registerThumbnailHandler(new HTMLThumbnail());
    }

    private void registerThumbnailHandler(IThumbnail thumbnailHandler) {
        for (String mimeType: thumbnailHandler.getSupportedTypes()) {
            thumbnailGenerators.put(mimeType, thumbnailHandler);
        }
    }


    public synchronized String getThumbnailsFolder() {
        if (null == this.thumbnailsFolder) {
            this.thumbnailsFolder = indexConfig.getThumbnailDir();
        }
        return this.thumbnailsFolder;
    }

    public synchronized void clearThumbnails() throws IOException {
        FileDeleteStrategy.FORCE.delete(new File(getThumbnailsFolder()));
    }

    public void sendThumbnail(HttpServletResponse response, String relativePath, String name) throws IOException {
        String fileType = FilenameUtils.getExtension(name).toLowerCase();
        File preExistThumbnail=null;
        if(fileType.equalsIgnoreCase("zip") || fileType.equalsIgnoreCase("tif")) {
            preExistThumbnail = new File(indexConfig.getFileRootDir() + "/" + relativePath + "/Thumbnails/" + name + ".thumbnail.png");
        }
        if (preExistThumbnail==null || !preExistThumbnail.exists()){
            preExistThumbnail = new File(getThumbnailsFolder() + "/" + relativePath + "/" + name + ".thumbnail.png");
        }

        if (!preExistThumbnail.exists() && !fileType.equalsIgnoreCase("zip")) {
            createThumbnail(indexConfig.getFileRootDir() + "/"+ relativePath+"/Files/"+name, preExistThumbnail);
        }
        if(preExistThumbnail.exists()) {
            FileInputStream in = new FileInputStream(preExistThumbnail);
            try {
                IOUtils.copy(in, response.getOutputStream());
            } finally {
                in.close();
            }
            response.getOutputStream().flush();
        }
    }

    private void createPlaceholderThumbnail(String sourceFilePath, File thumbnailFile) throws IOException {
        synchronized (sourceFilePath) {
            thumbnailFile.getParentFile().mkdirs();
            //Using extension to decide on the class as mime-types are different across *nix/Windows
            String fileType = FilenameUtils.getExtension(sourceFilePath).toLowerCase();
            logger.debug("Creating placeholder thumbnail [{}] for file type {}", thumbnailFile.getAbsolutePath(), fileType);
            int imageWidth=50, imageHeight = 65;
            BufferedImage image = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, imageWidth, imageHeight);
            g.setColor(Color.BLACK);
            g.setFont(new Font("sans-serif", Font.PLAIN, 12));
            int stringLen = (int) g.getFontMetrics().getStringBounds(fileType, g).getWidth();
            int start = imageWidth/2 - stringLen/2;
            g.drawString(fileType, start, imageHeight/2);
            ImageIOUtil.writeImage(image, thumbnailFile.getAbsolutePath(), 96);
        }
    }
    private void createThumbnail(String sourceFilePath, File thumbnailFile) throws IOException {
        synchronized (sourceFilePath) {
            thumbnailFile.getParentFile().mkdirs();
            //Using extension to decide on the class as mime-types are different across *nix/Windows
            String fileType = FilenameUtils.getExtension(sourceFilePath).toLowerCase();
            logger.debug("Creating thumbnail [{}] for file type {}", thumbnailFile.getAbsolutePath(), fileType);
            if (thumbnailGenerators.containsKey(fileType)) {
                try {
                    thumbnailGenerators.get(fileType).generateThumbnail(sourceFilePath, thumbnailFile);
                } catch (Throwable err) {
                    logger.debug("Error creating thumbnail: ", err.getMessage());
                    logger.debug("Will try to create placeholder now");
                    createPlaceholderThumbnail(sourceFilePath, thumbnailFile);
                }
            } else {
                logger.debug("Invalid file type for creating thumbnail: {}", fileType);
                throw new IOException("Invalid file type for creating thumbnail");
            }
        }
    }
    public boolean hasThumbnails(String accession, String relativePath){
        File file = new File(indexConfig.getFileRootDir() + "/" + relativePath + "/Thumbnails/");
        return file.exists();
    }

}
