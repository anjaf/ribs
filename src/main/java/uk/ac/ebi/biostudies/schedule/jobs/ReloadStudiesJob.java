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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.service.IndexService;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
public class ReloadStudiesJob {
    // logging machinery
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IndexConfig indexConfig;

    @Autowired
    IndexService indexService;

    @Scheduled(cron = "${bs.studies.reload}")
    public void doExecute() throws Exception {
        try {
            // check preferences and if source location is defined, use that
            String sourceLocation = indexConfig.getStudiesInputFile();
            if (isNotBlank(sourceLocation)) {
                logger.info("Reload of study data from [{}] requested", sourceLocation);
                indexService.clearIndex(false);
                indexService.indexAll("", true);
                logger.info("Reload of study data from [{}] completed", sourceLocation);
            }
        } catch (Exception x) {
            logger.error("Error reloading index",x);
            throw new RuntimeException(x);
        }
    }



}