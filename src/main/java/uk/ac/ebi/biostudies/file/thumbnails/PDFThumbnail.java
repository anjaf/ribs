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

package uk.ac.ebi.biostudies.file.thumbnails;

import com.twelvemonkeys.image.ResampleOp;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

public class PDFThumbnail implements IThumbnail {

    private static String[] supportedTypes = {"pdf"};

    @Override
    public String[] getSupportedTypes() {
        return supportedTypes;
    }

    @Override
    public void generateThumbnail(String sourceFilePath, File thumbnailFile) throws IOException {
        PDDocument pdf = null;
        try {
            pdf = PDDocument.load(sourceFilePath);
            PDPage page = (PDPage) pdf.getDocumentCatalog().getAllPages().get(0);
            BufferedImage image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 96);
            float inverseAspectRatio = ((float) image.getHeight()) / image.getWidth();
            BufferedImageOp resampler = new ResampleOp(THUMBNAIL_WIDTH, Math.round(inverseAspectRatio * THUMBNAIL_WIDTH), ResampleOp.FILTER_LANCZOS);
            BufferedImage output = resampler.filter(image, null);
            ImageIO.write(output, "png", thumbnailFile);
        } finally {
            if(pdf!=null) {
                pdf.close();
            }
        }
    }
}
