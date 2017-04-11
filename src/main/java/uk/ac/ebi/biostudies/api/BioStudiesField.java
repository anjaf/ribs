package uk.ac.ebi.biostudies.api;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import uk.ac.ebi.biostudies.api.util.analyzer.AccessFieldAnalyzer;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.api.util.analyzer.ExperimentTextAnalyzer;

/**
 * Created by awais on 17/02/2017.
 */
public enum BioStudiesField {

    ACCESS("access", "", BioStudiesFieldType.STRING_TOKENIZED , false, false, new AccessFieldAnalyzer()),
    ACCESSION("accession", "", BioStudiesFieldType.STRING_TOKENIZED, true, false, new AttributeFieldAnalyzer()),
    TYPE("type", "", BioStudiesFieldType.STRING_UNTOKENIZED, true, false,  new AttributeFieldAnalyzer()),
    TITLE("title", "", BioStudiesFieldType.STRING_TOKENIZED, true, false, new ExperimentTextAnalyzer()),
    AUTHORS("authors", "", BioStudiesFieldType.STRING_TOKENIZED, true, false,  new AttributeFieldAnalyzer()),
    CONTENT("content", "", BioStudiesFieldType.STRING_TOKENIZED, false, true, new ExperimentTextAnalyzer()),
    PROJECT("project", "", BioStudiesFieldType.STRING_TOKENIZED, false, false, new AttributeFieldAnalyzer()),
    LINKS("links", "", BioStudiesFieldType.LONG, true, false, null),
    FILES("files", "", BioStudiesFieldType.LONG, true, false, null),
    RELEASE_DATE("release_date", "", BioStudiesFieldType.LONG, true, false, new AttributeFieldAnalyzer()),
    ID("id", "", BioStudiesFieldType.STRING_UNTOKENIZED, false, false, null),
    ORGAN("organ", "Organ", BioStudiesFieldType.FACET, false, false, null),
    TECHNOLOGY("tech", "Assay Technology Type", BioStudiesFieldType.FACET, false, true, null),
    DATATYPE("dataType", "Data Type", BioStudiesFieldType.FACET, false, false, null),
    COMPOUND("compound", "Compound", BioStudiesFieldType.FACET, false, true, null),
    RAWPROCESSED("rawProcessed", "Raw/Processed", BioStudiesFieldType.FACET, false, false, null),
    ;

    private final String name;
    private final String title;

    private final BioStudiesFieldType type;
    private final boolean isRetrieved;
    private final boolean expand;
    private Analyzer analyzer;



    private BioStudiesField(final String name, String title, BioStudiesFieldType type , boolean isRetrieved, boolean expand, Analyzer analayzer) {
        this.name = name;
        this.type = type;
        this.isRetrieved = isRetrieved;
        this.title = title;
        this.expand = expand;
        this.analyzer = analayzer;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isRetrieved() {
        return isRetrieved;
    }

    public BioStudiesFieldType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Analyzer getAnalyzer(){return analyzer;}

    /**
     * expand for textual fields means query expansion and for facet fields means multivalue facet
     * @return
     */
    public boolean isExpand() {
        return expand;
    }
}
