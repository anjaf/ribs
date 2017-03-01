package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.util.BioStudiesQueryParser;
import uk.ac.ebi.biostudies.api.util.StudyUtils;
import uk.ac.ebi.biostudies.lucene.config.IndexConfig;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.lucene.config.IndexManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by ehsan on 27/02/2017.
 */

@Service
@Scope("singleton")
public class SearchServiceImpl implements SearchService {

    private Logger logger = LogManager.getLogger(SearchServiceImpl.class.getName());

    @Autowired
    IndexConfig indexConfig;

    @Autowired
    IndexManager indexManager;

    @Override
    public String search(String queryString, int page, int pageSize) {

        IndexReader reader = indexManager.getIndexReader();
        IndexSearcher searcher = indexManager.getIndexSearcher();
        String[] fields = indexConfig.getIndexFields();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        if (StringUtils.isEmpty(queryString))  {
            queryString = "*:*";
        } else {
            response.put("query", queryString);
        }
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new BioStudiesQueryParser(fields, analyzer);

        try {
            logger.debug("User queryString: {}",queryString);
            Query query = parser.parse(queryString);
            logger.debug("Lucene query: {}",query.toString());
            TopDocs hits = searcher.search(query, reader.numDocs());
            int hitsPerPage = pageSize;
            int to = page * hitsPerPage > hits.totalHits ? hits.totalHits : page * hitsPerPage;
            response.put("page", page);
            response.put("pageSize", hitsPerPage);
            response.put("totalHits", hits.totalHits);
            if (hits.totalHits > 0) {
                ArrayNode docs = mapper.createArrayNode();
                for (int i = (page - 1) * hitsPerPage; i < to; i++) {
                    ObjectNode docNode = mapper.createObjectNode();
                    Document doc = reader.document(hits.scoreDocs[i].doc);
                    for (BioStudiesField field : BioStudiesField.values()) {
                        if (!field.isRetrieved()) continue;
                        switch (field.getType()) {
                            case LONG:
                                docNode.put(String.valueOf(field), Long.parseLong(doc.get(field.toString())));
                                break;
                            default:
                                docNode.put(String.valueOf(field), doc.get(field.toString()));
                                break;
                        }
                    }

                    docNode.put("isPublic",
                            (" " + doc.get(String.valueOf(BioStudiesField.ACCESS) + " ")).toLowerCase().contains(" public ")
                    );
                    docs.add(docNode);
                }
                response.set("hits", docs);
                logger.debug(hits.totalHits + " hits");
            }
        }
        catch(Throwable error){
            logger.error("problem in searching this query {}", queryString, error);
        }

        return  response.toString();
    }

    @Override
    public String getDetailFile(String accessionNumber) throws IOException {
        String path = StudyUtils.getPartitionedPath(accessionNumber);
        File file = new File(indexConfig.getRepositoryPath() + path + "/"+accessionNumber+".json");
        if (!file.exists()) {
            throw new FileNotFoundException("Study "+accessionNumber+" not found!");        }
        int length = (int)file.length();
        FileInputStream reader  = new FileInputStream(file);
        byte[] buff = new byte[length];
        reader.read(buff);
        String result = new String(buff, "UTF-8");
        return result;
    }
}
