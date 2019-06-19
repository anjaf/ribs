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

package uk.ac.ebi.biostudies.schedule.jobs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.IndexConfig;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

@Service
public class DeleteTempZipFilesJob  {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    IndexConfig indexConfig;

    @Scheduled(cron = "${bs.files.temp-zip.delete}")
    public void doExecute( ) throws Exception {
        try {
            logger.debug("Looking for expired temp zip files");
            Date oldestFileDate = DateUtils.addDays(new Date(), -1);
            File tempZipDirectory = new File(indexConfig.getZipTempDir());
            if (!tempZipDirectory.exists())
                tempZipDirectory.mkdir();
            Iterator<File> filesToDelete = FileUtils.iterateFiles(tempZipDirectory, new AgeFileFilter(oldestFileDate), null);
            while (filesToDelete.hasNext()) {
                File file = filesToDelete.next();
                file.delete();
                logger.debug("Deleted "+ file.getAbsolutePath());
            }
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }


}