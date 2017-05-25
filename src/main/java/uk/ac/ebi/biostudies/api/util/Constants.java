package uk.ac.ebi.biostudies.api.util;

import org.springframework.http.MediaType;

/**
 * Created by ehsan on 30/03/2017.
 */
public interface Constants {
    String RELEASE_DATE = "release_date";
    String RELEVANCE = "relevance";

    String SORT_ORDER = "sortorder";
    String ASCENDING = "ascending";
    String DESCENDING = "descending";
    String ACCESSION = "accession";
    String TITLE = "title";
    String AUTHORS = "authors";
    String LINKS = "links";
    String FILES = "files";
    String STUDIES_JSON_FILE = "studies.json";
    String JSON_UNICODE_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
    String STRING_UNICODE_MEDIA_TYPE = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8";
}
