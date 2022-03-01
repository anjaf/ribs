package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import uk.ac.ebi.biostudies.api.util.DataTableColumnInfo;

import java.io.IOException;
import java.util.Map;

public interface FilePaginationService {
    Document getFileDocument(String accession, String path) throws ParseException, IOException;

    ObjectNode getFileList(String accession, int start, int pageSize, String search, int draw, boolean metadata, Map<Integer, DataTableColumnInfo> dataTableUiResult, String secretKey) throws SubmissionNotAccessibleException;
    ObjectNode getStudyInfo(String accession, String secretKey) throws SubmissionNotAccessibleException;

}
