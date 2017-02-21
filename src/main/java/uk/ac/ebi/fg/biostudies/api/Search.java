package uk.ac.ebi.fg.biostudies.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;
import uk.ac.ebi.fg.biostudies.api.util.BioStudiesQueryParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Created by awais on 14/02/2017.
 */
@Path("search")
public class Search {
    public static String INDEX_PATH = "biostudies";

    private static String [] FIELDS = {"accession","authors","content","title","type", "links", "files"};

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String search(@QueryParam("query") String queryString,
                                @DefaultValue("1") @QueryParam("page") Integer page)
            throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        if (StringUtils.isBlank(queryString))  {
            queryString = "*:*";
        } else {
            response.put("query", queryString);
        }

        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_PATH)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new BioStudiesQueryParser(FIELDS, analyzer);
        Query query = parser.parse(queryString);
        System.out.println(query.toString());
        TopDocs hits = searcher.search(query, reader.numDocs());

        int hitsPerPage = 20;
        int to = page * hitsPerPage  > hits.totalHits ? hits.totalHits : page * hitsPerPage;
        response.put("page", page);
        response.put("pageSize", hitsPerPage);
        response.put("totalHits", hits.totalHits);

        if (hits.totalHits>0) {
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

                docNode.put("isPublic" ,
                        (" "+ doc.get(String.valueOf(BioStudiesField.ACCESS)+" ")).toLowerCase().contains(" public ")
                );
                docs.add(docNode);
            }
            response.set("hits", docs);
            System.out.println( hits.totalHits + " hits");
        }

        reader.close();
        return response.toString();
    }
}
