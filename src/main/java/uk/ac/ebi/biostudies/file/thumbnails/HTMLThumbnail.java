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
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HTMLThumbnail implements IThumbnail{

    private Color background = Color.WHITE;
    private Font font = new Font("sans-serif", Font.PLAIN, 4);
    private static String [] supportedTypes= {"htm","html"};

    @Override
    public String[] getSupportedTypes() {
        return supportedTypes;
    }
    @Override
    public void generateThumbnail(String sourceFilePath, File thumbnailFile) throws IOException{
        String html = FileUtils.readFileToString(new File(sourceFilePath), StandardCharsets.UTF_8);
        JEditorPane jep = new JEditorPane("text/html", html) {
            @Override
            public boolean getScrollableTracksViewportWidth()
            {
                return true;
            }
        };
        jep.setSize(THUMBNAIL_WIDTH*4,THUMBNAIL_HEIGHT*4);
        BufferedImage image = new BufferedImage(THUMBNAIL_WIDTH*4,THUMBNAIL_HEIGHT*4, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        jep.print(g);
        BufferedImageOp resampler = new ResampleOp(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, ResampleOp.FILTER_LANCZOS);
        BufferedImage output = resampler.filter(image, null);
        ImageIOUtil.writeImage(output, thumbnailFile.getAbsolutePath(), 96);
    }
}
