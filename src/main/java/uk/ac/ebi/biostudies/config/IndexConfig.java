package uk.ac.ebi.biostudies.config;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.ac.ebi.biostudies.api.util.Constants;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by ehsan on 27/02/2017.
 */

@Configuration
@PropertySource("classpath:index.properties")
public class IndexConfig {

    @Value("${index.directory}")
    private String indexDirectory;

    @ Value("${index.facetDirectory}")
    private String facetDirectory;

    @ Value("${studiesFileDirectory}")
    private String studiesFileDirectory;

    @ Value("${indexer.threadCount}")
    private int threadCount;

    @Value("${indexer.queueSize}")
    private int queueSize;

    @ Value("${index.fields}")
    private String indexFields;

    @ Value("${repository.path}")
    private String repositoryPath;

    @ Value("${defaultField}")
    private String defaultField;

    @ Value("${searchSnippetFragmentSize}")
    private int searchSnippetFragmentSize;

    @Value("${bs.studies.thumbnails-location}")
    private String thumbnailDir;

    @Value("${bs.files.temp-zip.location}")
    private String zipTempDir;

    @Value("${bs.studies.files-root-location}")
    private String fileRootDir;

    @Value("${bs.files.ftp.url}")
    private String ftpDir;

    @Value("${indexer.stopwords}")
    private String stopwords;

    @Value("${indexer.spellchecker-location}")
    private String spellcheckerLocation;

    public static CharArraySet STOP_WORDS;


    @PostConstruct
    private void init(){
        STOP_WORDS =  new CharArraySet(Arrays.asList(stopwords.split(",")), false);
    }


    public int getThreadCount() {
        return threadCount;
    }

    public String getIndexDirectory() {
        return indexDirectory;
    }

    public String getFacetDirectory() {
        return facetDirectory;
    }

    public String getStudiesInputFile() {
        return studiesFileDirectory + Constants.STUDIES_JSON_FILE;
    }

    public String getStudiesFileDirectory() {
        return studiesFileDirectory;
    }

    public String getRepositoryPath() {
        return repositoryPath;
    }

    public String getDefaultField() {
        return defaultField;
    }

    public int getSearchSnippetFragmentSize() {
        return searchSnippetFragmentSize;
    }



    public String[] getIndexFields(){
        String[] fields = indexFields.split(",");
        return fields;
    }

    public String getThumbnailDir() {
        return thumbnailDir;
    }

    public String getZipTempDir() {
        return zipTempDir;
    }

    public String getFileRootDir() {
        return fileRootDir;
    }

    public String getFtpDir() {
        return ftpDir;
    }

    public int getQueueSize(){
        return queueSize;
    }

    public String getSpellcheckerLocation() {
        return spellcheckerLocation;
    }
}
