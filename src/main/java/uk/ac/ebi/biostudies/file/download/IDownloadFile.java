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

import java.io.IOException;
import java.io.InputStream;

public interface IDownloadFile {
    int KB = 1024;

    String getName();

    default String getPath() {
        return null;
    }

    long getLength();

    long getLastModified();

    boolean canDownload();

    boolean isDirectory();

    InputStream getInputStream() throws IOException;

    void close() throws IOException;
}
