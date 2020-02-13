package uk.ac.ebi.biostudies.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static uk.ac.ebi.biostudies.api.util.Constants.*;

/**
 * Created by ehsan on 09/03/2017.
 */
@Component
@Scope("singleton")
public class TaxonomyManager {
    private Logger logger = LogManager.getLogger(TaxonomyManager.class.getName());

    private TaxonomyWriter taxonomyWriter;
    private TaxonomyReader taxonomyReader;
    private FacetsConfig facetsConfig;
    private Directory taxoDirectory;
    public JsonNode PROJECT_FACET;

    @Autowired
    IndexConfig indexConfig;

//    @Autowired //to force spring first init main index readers then begin creating taxonomy readers
//    IndexManager indexManager;

    public void init(Collection<JsonNode> allFields){
        facetsConfig = new FacetsConfig();

        for (JsonNode jsonNode : allFields) {
            if (jsonNode.get(IndexEntryAttributes.FIELD_TYPE).textValue().equalsIgnoreCase(IndexEntryAttributes.FieldTypeValues.FACET)) {
                if (!jsonNode.has(IndexEntryAttributes.MULTIVALUED)
                        || jsonNode.get(IndexEntryAttributes.MULTIVALUED).asBoolean(true)) {
                    getFacetsConfig().setMultiValued(jsonNode.get(IndexEntryAttributes.NAME).asText(), true);
                }
                if (jsonNode.get(IndexEntryAttributes.NAME).textValue().equalsIgnoreCase(Facets.PROJECT)) {
                    PROJECT_FACET = jsonNode;
                }
            }
        }

        try {
            if(taxonomyWriter!=null && taxonomyReader!=null)
            {
                taxonomyWriter.commit();
                taxonomyWriter.close();
                taxonomyReader.close();
            }
            taxoDirectory = FSDirectory.open(new File(indexConfig.getFacetDirectory()).toPath());
            taxonomyWriter = new DirectoryTaxonomyWriter(getTaxoDirectory(), IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            taxonomyReader = new DirectoryTaxonomyReader(taxoDirectory);
        } catch (Throwable e) {
            logger.error("can not create taxonomy writer or reader", e);
        }

    }


    public void commitTaxonomy(){
        try {
            getTaxonomyWriter().commit();
        } catch (IOException e) {
            logger.error("problem in commiting taxonomy writer", e);
        }
    }

    public TaxonomyWriter getTaxonomyWriter() {
        return taxonomyWriter;
    }

    public void refreshTaxonomyReader(){
        TaxonomyReader tempReader;
        try {
            if(taxonomyReader==null)
                taxonomyReader = new DirectoryTaxonomyReader(taxoDirectory);
            if((tempReader=TaxonomyReader.openIfChanged(taxonomyReader))!= null)
                taxonomyReader = tempReader;
        } catch (IOException e) {
            logger.error("problem in refreshing taxonomy", e);
        }
    }

    public void resetTaxonomyWriter() throws IOException {
        try {
            taxonomyWriter.commit();
            taxonomyWriter.close();
        } catch (AlreadyClosedException ex) {
            logger.error(ex);
        }
        taxonomyWriter = new DirectoryTaxonomyWriter(getTaxoDirectory(), IndexWriterConfig.OpenMode.CREATE);
    }

    public TaxonomyReader getTaxonomyReader() {
        return taxonomyReader;
    }

    public FacetsConfig getFacetsConfig() {
        return facetsConfig;
    }

    public Directory getTaxoDirectory() {
        return taxoDirectory;
    }
}
