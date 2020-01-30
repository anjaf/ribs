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
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.EFOConfig;
import uk.ac.ebi.biostudies.service.impl.efo.Ontology;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class ReloadOntologyJob{
    // logging machinery
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EFOConfig efoConfig;
    @Autowired
    Ontology ontology;

    @Scheduled(cron = "${bs.efo.reload}")
    @Async
    public void doExecute(){
        // check if efo.owl is in temp folder; if it's not there, copy from source
        try {
            String efoLocation = efoConfig.getOwlFilename();
            File efoFile = new File(efoLocation);
            if (!efoFile.exists()) {
                String efoBuiltinSource = efoConfig.getLocalOwlFilename();
                try (InputStream is = new ClassPathResource(efoBuiltinSource).getInputStream()) {
                    Files.copy( is, efoFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            logger.info("Loading EFO ontology from [{}]", efoFile.getPath());

            try (InputStream is = new FileInputStream(efoFile)) {
                ontology.update(is);
                logger.info("EFO loading completed");
            }
        }catch (Exception ex){
            logger.error("Problem in loading EFO! ", ex);
        }
    }
}