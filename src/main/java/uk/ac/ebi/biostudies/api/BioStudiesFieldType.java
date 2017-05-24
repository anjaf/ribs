package uk.ac.ebi.biostudies.api;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * Created by awais on 17/02/2017.
 */
public enum BioStudiesFieldType {
    STRING_TOKENIZED,
    STRING_UNTOKENIZED,
    LONG,
    FACET
    ;

    public static final FieldType TYPE_NOT_ANALYZED = new FieldType();
    static {
        TYPE_NOT_ANALYZED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TYPE_NOT_ANALYZED.setTokenized(false);
        TYPE_NOT_ANALYZED.setStored(true);
    }
}
