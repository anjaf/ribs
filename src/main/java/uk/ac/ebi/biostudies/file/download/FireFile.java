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

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.biostudies.service.impl.FireService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public final class FireFile implements IDownloadFile {
    private final String path;
    private final FireService fireService;

    public FireFile(String path, FireService fireService) {
        this.fireService = fireService;
        if (null == path) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.path = path;
    }

    public String getName() {
        return path;
    }

    public String getPath() {
        return StringUtils.replace(path.toString(),"\\","/");
    }

    public long getLength() {
        return 0;//TODO: fix length
    }

    public long getLastModified() {
        return new Date().getTime();
    }

    public boolean canDownload() {
        return true;
    }

    public boolean isDirectory() {
        return false; //TODO fix isDirectory
    }


    public InputStream getInputStream() throws IOException {
        return fireService.getFireObjectInputStream(path).getInputStream();
    }

    public void close() throws IOException {
        fireService.getFireObjectInputStream(path).getInputStream().close();
    }
}
