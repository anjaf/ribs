package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    private String studiesInputFile;

    @ Value("${indexer.threadCount}")
    private int threadCount;

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


    public int getThreadCount() {
        return threadCount;
    }

    public String getIndexDirectory() {
        return indexDirectory;
    }

    public void setIndexDirectory(String indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

    public String getFacetDirectory() {
        return facetDirectory;
    }

    public void setFacetDirectory(String facetDirectory) {
        this.facetDirectory = facetDirectory;
    }

    public String getStudiesInputFile() {
        return studiesInputFile;
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



    public void setStudiesInputFile(String studiesInputFile) {
        this.studiesInputFile = studiesInputFile;
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
}
