package uk.ac.ebi.biostudies.service.impl.efo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.ac.ebi.arrayexpress.utils.efo.EFOLoader;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.arrayexpress.utils.efo.IEFO;
import uk.ac.ebi.biostudies.efo.*;
import uk.ac.ebi.biostudies.config.EFOConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by ehsan on 02/03/2017.
 */
@Service
@Scope("singleton")
public class Ontology implements InitializingBean, DisposableBean {

    private final Logger logger = LogManager.getLogger(Ontology.class.getName());

    private IEFO efo;
    @Autowired
    private EFOExpansionLookupIndex efoExpansionLookupIndex;
    @Autowired
    private EFOConfig efoConfig;
    @Autowired
    Autocompletion autocompletion;

//    private EFOExpansionLookupIndex lookupIndex;


//    private SearchEngine search;
//    private Autocompletion autocompletion;

    private EFOSubclassesOptions assayByMolecule;
    private EFOSubclassesOptions assayByInstrument;



    @Override
    public void afterPropertiesSet() {
        this.assayByMolecule = new EFOSubclassesOptions("All assays by molecule");
        this.assayByInstrument = new EFOSubclassesOptions("All technologies");
    }

    public void update(InputStream ontologyStream) throws IOException, InterruptedException {
        // load custom synonyms to lookup index
        loadCustomSynonyms();

        Long time = System.currentTimeMillis();
        this.efo = removeIgnoredClasses(new EFOLoader().load(ontologyStream));
        logger.debug((System.currentTimeMillis()-time)+"");


        efoExpansionLookupIndex.setEfo(getEfo());
        efoExpansionLookupIndex.buildIndex();


        autocompletion.setEfo(getEfo());
        autocompletion.rebuild();


        this.assayByMolecule.reload(getEfo(), "http://www.ebi.ac.uk/efo/EFO_0002772");
        this.assayByInstrument.reload(getEfo(), "http://www.ebi.ac.uk/efo/EFO_0002773");

    }

    public IEFO getEfo() {
        return this.efo;
    }

    public String getAssayByMoleculeOptions() {
        return this.assayByMolecule.getHtml();
    }

    public String getAssayByInstrumentOptions() {
        return this.assayByInstrument.getHtml();
    }

    public EFOExpansionLookupIndex getExpansionLookupIndex() {
        return efoExpansionLookupIndex;
    }

    private void loadCustomSynonyms() throws IOException {
        String synFileLocation = efoConfig.getSynonymFilename();
        if (null != synFileLocation) {
            try (InputStream resourceInputStream = (new ClassPathResource(synFileLocation)).getInputStream()){
                Map<String, Set<String>> synonyms = new SynonymsFileReader(new InputStreamReader(resourceInputStream)).readSynonyms();
                efoExpansionLookupIndex.setCustomSynonyms(synonyms);
                //lookBack index
                //this.lookBackIndex.setCustomSynonyms(synonyms);
                logger.debug("Loaded custom synonyms from [{}]", synFileLocation);
            } catch (Exception ex){
                logger.error("could not open synonyms file", ex);
            }
        }
    }

    public IEFO removeIgnoredClasses(IEFO efo, String ignoreListFileLocation) throws IOException {
        if (null != ignoreListFileLocation) {
            try (InputStream is = (new ClassPathResource(ignoreListFileLocation)).getInputStream()) {
                Set<String> ignoreList = StringTools.streamToStringSet(is, "UTF-8");

                logger.debug("Loaded EFO ignored classes from [{}]", ignoreListFileLocation);
                for (String id : ignoreList) {
                    if (null != id && !"".equals(id) && !id.startsWith("#") && efo.getMap().containsKey(id)) {
                        removeEFONode(efo, id);
                    }
                }
            }
        }
        return efo;
    }

    private IEFO removeIgnoredClasses(IEFO efo) throws IOException {
        return removeIgnoredClasses(efo, efoConfig.getIgnoreListFilename());
    }

    private void removeEFONode(IEFO efo, String nodeId) {
        EFONode node = efo.getMap().get(nodeId);
        // step 1: for all parents remove node as a child
        for (EFONode parent : node.getParents()) {
            parent.getChildren().remove(node);
        }
        // step 2: for all children remove node as a parent; is child node has no other parents, remove it completely
        for (EFONode child : node.getChildren()) {
            child.getParents().remove(node);
            if (0 == child.getParents().size()) {
                removeEFONode(efo, child.getId());
            }
        }

        // step 3: remove node from efo map
        efo.getMap().remove(nodeId);
        logger.debug("Removed [{}] -> [{}]", node.getId(), node.getTerm());
    }

    @Override
    public void destroy() {

    }


    private class EFOSubclassesOptions extends HTMLOptions {
        private String defaultOption;

        public EFOSubclassesOptions(String defaultOption) {
            this.defaultOption = defaultOption;
            initialize();
        }

        private void initialize() {
            clearOptions();
            addOption("", defaultOption);
        }

        public void reload(IEFO efo, String baseNode) {
            initialize();
            EFONode node = efo.getMap().get(baseNode);
            if (null != node) {
                for (EFONode subclass : node.getChildren()) {
                    addOption("\"" + subclass.getTerm().toLowerCase() + "\"", subclass.getTerm());
                }
            }
        }
    }

}
