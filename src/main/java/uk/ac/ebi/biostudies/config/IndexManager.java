package uk.ac.ebi.biostudies.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.util.LowercaseAnalyzer;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.service.impl.efo.Ontology;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Created by ehsan on 27/02/2017.
 */
@Component
@Scope("singleton")
public class IndexManager {

    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private IndexWriter indexWriter;
    private Directory indexDirectory;
    private IndexWriterConfig indexWriterConfig;
    private Directory efoIndexDirectory;
    private IndexReader efoIndexReader;
    private IndexSearcher efoIndexSearcher;
    private IndexWriter efoIndexWriter;

    private Logger logger = LogManager.getLogger(IndexManager.class.getName());

    @Autowired
    IndexConfig indexConfig;
    @Autowired
    EFOConfig eFOConfig;
    @Autowired
    Ontology ontology;
    @Autowired
    TaxonomyManager taxonomyManager;
    @Autowired
    AnalyzerManager analyzerManager;


    @PostConstruct
    public void init(){
        logger.debug("begin initing bioindexmanager");
        logger.info("begin initing bioindexmanager");
        String indexDir = indexConfig.getIndexDirectory();
        String facet = indexConfig.getFacetDirectory();
        try {
            indexDirectory = FSDirectory.open(Paths.get(indexDir));
            indexWriterConfig = new IndexWriterConfig(analyzerManager.getPerFieldAnalyzerWrapper());
            getIndexWriterConfig().setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(getIndexDirectory(), getIndexWriterConfig());
            indexReader = DirectoryReader.open(indexWriter);
            indexSearcher = new IndexSearcher(getIndexReader());
            IndexWriterConfig efoIndexWriterConfig = new IndexWriterConfig(new LowercaseAnalyzer());
            efoIndexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            efoIndexDirectory = FSDirectory.open(Paths.get(eFOConfig.getEfoIndexLocation()));
            loadEFO(efoIndexWriterConfig);
            if(efoIndexWriter==null)
                efoIndexWriter = new IndexWriter(getEfoIndexDirectory(), efoIndexWriterConfig);
            efoIndexReader = DirectoryReader.open(efoIndexWriter);
            efoIndexSearcher = new IndexSearcher(getEfoIndexReader());
            taxonomyManager.init();

        }catch (Throwable error){
            logger.error("Problem in reading lucene indices",error);
        }
    }

    private void loadEFO(IndexWriterConfig efoIndexWriterConfig) throws Exception{
        if(!DirectoryReader.indexExists(efoIndexDirectory)){
            try (InputStream resourceInputStream = (new ClassPathResource(eFOConfig.getEfoSource())).getInputStream()){
                efoIndexWriter = new IndexWriter(getEfoIndexDirectory(), efoIndexWriterConfig);
                ontology.update(resourceInputStream);
                logger.info("EFO loading completed");
            }catch (Exception ex){
                logger.error("EFO file not found", ex);
            }
        }
    }

    @PreDestroy
    public void destroy(){

    }

    public void refreshIndexSearcherAndReader(){

        try {
            indexReader.close();
            indexReader = DirectoryReader.open(indexWriter);
            indexSearcher = new IndexSearcher(indexReader);
        }
        catch (Exception ex){
            logger.error("problem in refreshing index", ex);
        }
    }

    public IndexReader getIndexReader() {
        return indexReader;
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }

    public Directory getIndexDirectory() {
        return indexDirectory;
    }

    public IndexWriterConfig getIndexWriterConfig() {
        return indexWriterConfig;
    }

    public Directory getEfoIndexDirectory() {
        return efoIndexDirectory;
    }

    public IndexReader getEfoIndexReader() {
        return efoIndexReader;
    }

    public IndexSearcher getEfoIndexSearcher() {
        return efoIndexSearcher;
    }

    public IndexWriter getEfoIndexWriter() {
        return efoIndexWriter;
    }
}