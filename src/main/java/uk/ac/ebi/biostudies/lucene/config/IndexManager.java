package uk.ac.ebi.biostudies.lucene.config;

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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private Logger logger = LogManager.getLogger(IndexManager.class.getName());

    @Autowired
    IndexConfig indexConfig;

    @PostConstruct
    public void init(){
        logger.debug("begin initing bioindexmanager");
        logger.info("begin initing bioindexmanager");
        String indexDir = indexConfig.getIndexDirectory();
        String facet = indexConfig.getFacetDirectory();
        try {
            indexDirectory = FSDirectory.open(Paths.get(indexDir));
            indexReader = DirectoryReader.open(getIndexDirectory());
            indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
            getIndexWriterConfig().setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(getIndexDirectory(), getIndexWriterConfig());
            indexReader = DirectoryReader.open(getIndexDirectory());
            indexSearcher = new IndexSearcher(getIndexReader());

        }catch (Throwable error){
            logger.error("Problem in reading lucene indices",error);
        }
    }

    @PreDestroy
    public void destroy(){

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
}
