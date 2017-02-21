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
import uk.ac.ebi.fg.biostudies.api.util.StudyUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Created by awais on 14/02/2017.
 */
@Path("/studies")
public class Study {
    public static String INDEX_PATH = "biostudies";
    public static String STUDIES_PATH = "B:/.adm/databases/beta/submission/";

    private static String [] FIELDS = {"accession","authors","content","title","type", "links", "files"};

    @GET
    @Path("{accession}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("accession") String accession) throws IOException, ParseException {

        //TODO: check access
        String path = StudyUtils.getPartitionedPath(accession);
        File file = new File(STUDIES_PATH + path + "/"+accession+".json");
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"errorMessage\":\"Study not found!\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok( file ).build();
    }
}
