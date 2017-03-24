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

package uk.ac.ebi.biostudies.file.download;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.biostudies.file.ZipStatusService;


import java.io.*;
import java.util.Arrays;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipperThread extends Thread {
    private String[] files;
    private String relativePath;
    private String uuid;
    private OutputStream out;
    private String tempZipFolder;
    private String rootFolder;
    ZipStatusService zipStatusService;

    public ZipperThread(String[] files, String relativePath, String uuid, OutputStream out, String tempZipFolder, String rootFolder, ZipStatusService zipStatusService){
        this.files = files;
        this.relativePath = relativePath;
        this.uuid = uuid;
        this.out = out;
        this.tempZipFolder = tempZipFolder;
        this.rootFolder = rootFolder;
        this.zipStatusService = zipStatusService;
    }


    public void run() {
        Stack<String> fileStack = new Stack<>();
        fileStack.addAll(Arrays.asList(files));
        String zipFileName = tempZipFolder + "/" + uuid + ".zip";
        byte[] buffer = new byte[4 * IDownloadFile.KB];
        OutputStream outputStream = null;
        try {
            outputStream = out != null ? out : new FileOutputStream(zipFileName);
            try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
                String canonicalPath = rootFolder + "/" + relativePath + "/Files/";
                String envIndependentCanonicalPath = new File(canonicalPath).getCanonicalPath();
                while (!fileStack.empty()) {
                    final String filename = StringUtils.replace(fileStack.pop(),"..",".");
                    File file = new File(canonicalPath,filename);
                    if (!file.getCanonicalPath().startsWith(envIndependentCanonicalPath)) break;
                    if (file.isDirectory()) {
                        fileStack.addAll(Collections2.transform(Arrays.asList(file.list()),
                                new Function<String, String>() {
                                    @Override
                                    public String apply(final String f) {
                                        return filename+"/"+f;
                                    }
                                }));
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
            new File(zipFileName).delete();
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        zipStatusService.removeFile(uuid);
    }
}
