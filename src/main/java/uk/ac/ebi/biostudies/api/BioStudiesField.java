package uk.ac.ebi.biostudies.api;

/**
 * Created by awais on 17/02/2017.
 */
public enum BioStudiesField {

    ACCESS("access", "", BioStudiesFieldType.STRING_TOKENIZED , false, false),
    ACCESSION("accession", "", BioStudiesFieldType.STRING_TOKENIZED, true, false),
    TYPE("type", "", BioStudiesFieldType.STRING_UNTOKENIZED, true, false),
    TITLE("title", "", BioStudiesFieldType.STRING_TOKENIZED, true, false),
    AUTHORS("authors", "", BioStudiesFieldType.STRING_TOKENIZED, true, false),
    CONTENT("content", "", BioStudiesFieldType.STRING_TOKENIZED, true, true),
    PROJECT("project", "", BioStudiesFieldType.STRING_UNTOKENIZED, false, false),
    LINKS("links", "", BioStudiesFieldType.LONG, true, false),
    FILES("files", "", BioStudiesFieldType.LONG, true, false),
    ID("id", "", BioStudiesFieldType.STRING_UNTOKENIZED, false, false),
    ORGAN("organ", "Organ", BioStudiesFieldType.FACET, false, false),
    TECHNOLOGY("tech", "Assay Technology Type", BioStudiesFieldType.FACET, false, true),
    DATATYPE("dataType", "Data Type", BioStudiesFieldType.FACET, false, false),
    COMPOUND("compound", "Compound", BioStudiesFieldType.FACET, false, true),
    RAWPROCESSED("rawProcessed", "Raw/Processed", BioStudiesFieldType.FACET, false, false),
    ;

    private final String name;
    private final String title;

    private final BioStudiesFieldType type;
    private final boolean isRetrieved;
    private final boolean expand;



    private BioStudiesField(final String name, String title,BioStudiesFieldType type , boolean isRetrieved, boolean expand) {
        this.name = name;
        this.type = type;
        this.isRetrieved = isRetrieved;
        this.title = title;
        this.expand = expand;
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

    /**
     * expand for textual fields means query expansion and for facet fields means multivalue facet
     * @return
     */
    public boolean isExpand() {
        return expand;
    }
}
