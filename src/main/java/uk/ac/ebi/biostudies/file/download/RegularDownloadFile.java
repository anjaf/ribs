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

import java.io.*;
import java.nio.file.Path;

public final class RegularDownloadFile implements IDownloadFile {
    private final Path path;
    private String downloadName;

    public RegularDownloadFile(Path path) {
        if (null == path) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.path = path;
    }

    public RegularDownloadFile(Path path, String downloadName) {
        if (null == path) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.path = path;
        this.downloadName = downloadName;
    }
    private File getFile() {
        return this.path.toFile();
    }

    public String getName() {
        return downloadName!=null ? downloadName : getFile().getName();
    }

    public String getPath() {
        return path.toString();
    }

    public long getLength() {
        return getFile().length();
    }

    public long getLastModified() {
        return getFile().lastModified();
    }

    public boolean canDownload() {
        return getFile().exists() && getFile().isFile() && getFile().canRead();
    }

    public boolean isDirectory() {
        return getFile().exists() && getFile().isDirectory() && getFile().canRead();
    }


    public InputStream getInputStream() throws IOException {
        return new FileInputStream(getFile());
    }

    public void close() throws IOException {
    }
}
