package uk.ac.ebi.biostudies.api;

/**
 * Created by awais on 17/02/2017.
 */
public enum BioStudiesField {

    ACCESS("access", BioStudiesFieldType.STRING_TOKENIZED , false),
    ACCESSION("accession", BioStudiesFieldType.STRING_TOKENIZED, true),
    TYPE("type", BioStudiesFieldType.STRING_UNTOKENIZED, true),
    TITLE("title", BioStudiesFieldType.STRING_TOKENIZED, true),
    AUTHORS("authors", BioStudiesFieldType.STRING_TOKENIZED, true),
    CONTENT("content", BioStudiesFieldType.STRING_TOKENIZED, true),
    LINKS("links", BioStudiesFieldType.LONG, true),
    FILES("files", BioStudiesFieldType.LONG, true),
    ID("id", BioStudiesFieldType.STRING_UNTOKENIZED, false)
    ;

    private final String name;

    private final BioStudiesFieldType type;
    private final boolean isRetrieved;


    private BioStudiesField(final String name, BioStudiesFieldType type , boolean isRetrieved) {
        this.name = name;
        this.type = type;
        this.isRetrieved = isRetrieved;
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

}
