package uk.ac.ebi.biostudies.api.util;

import org.springframework.http.MediaType;

/**
 * Created by ehsan on 30/03/2017.
 */
public interface Constants {

    String RELEASE_DATE = "release_date";
    String RELEVANCE = "relevance";
    String STUDIES_JSON_FILE = "studies.json";
    String JSON_UNICODE_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
    String STRING_UNICODE_MEDIA_TYPE = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8";
    String NA = "n/a";
    String PUBLIC = "public";

    int TOP_FACET_COUNT = 10;

    interface SortOrder {
        String ASCENDING = "ascending";
        String DESCENDING = "descending";
    }



    interface IndexEntryAttributes {
        String FIELD_TYPE = "fieldType";
        String NAME = "name";
        String TITLE = "title";
        String RETRIEVED = "retrieved";
        String EXPANDED = "expanded";
        String SORTABLE = "sortable";
        String ANALYZER = "analyzer";
        String MULTIVALUED = "multiValued";
        String JSON_PATH = "jsonPath";
        String DEFAULT_VALUE = "defaultValue";
        String VISIBLE = "visible";
        String PRIVATE = "private";

        interface FieldTypeValues {
            String TOKENIZED_STRING = "tokenized_string";
            String UNTOKENIZED_STRING = "untokenized_string";
            String LONG = "long";
            String FACET = "facet";
        }

    }

    interface Facets {
        String PROJECT = "facet.project";
        String FILE_TYPE = "facet.file_type";
        String RELEASED_YEAR_FACET = "facet.released_year";
        String MODIFICATION_YEAR_FACET="facet.modification_year";

        String DELIMITER = "|";
    }

    interface Fields {
        String ID = "id";
        String ACCESSION = "accession";
        String TITLE = "title";
        String AUTHOR = "author";
        String LINKS = "links";
        String FILES = "files";
        String TYPE = "type";
        String CONTENT = "content";
        String ACCESS = "access";
        String SECRET_KEY = "seckey";
        String ORCID = "orcid";
        String RELEASE_TIME = "rtime";
        String MODIFICATION_TIME="mtime";
        String CREATION_TIME = "ctime";
        String RELEASE_DATE = "release_date";
    }

}
