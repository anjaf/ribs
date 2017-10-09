package uk.ac.ebi.biostudies.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.BioStudiesFieldType;

import java.io.File;
import java.io.IOException;

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

    @Autowired
    IndexConfig indexConfig;

//    @Autowired //to force spring first init main index readers then begin creating taxonomy readers
//    IndexManager indexManager;

    public void init(){
        facetsConfig = new FacetsConfig();
        //TODO delete this
        facetsConfig.setMultiValued("fundingagency", true);
        for(BioStudiesField bioStudiesField:BioStudiesField.values())
            if(bioStudiesField.getType()== BioStudiesFieldType.FACET)
                if(bioStudiesField.isExpand())
                    getFacetsConfig().setMultiValued(bioStudiesField.toString(), true);

        try {
            taxoDirectory = FSDirectory.open(new File(indexConfig.getFacetDirectory()).toPath());
            taxonomyWriter = new DirectoryTaxonomyWriter(getTaxoDirectory(), IndexWriterConfig.OpenMode.CREATE);
            taxonomyReader = new DirectoryTaxonomyReader(taxoDirectory);
        } catch (IOException e) {
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
