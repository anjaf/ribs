package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import uk.ac.ebi.biostudies.api.util.DataTableColumnInfo;

import java.util.Map;

public interface FilePaginationService {
    String getFileList(String accession, int start, int pageSize, String search, int draw, Map<Integer, DataTableColumnInfo> dataTableUiResult);
    ObjectNode getStudyInfo(String accession);

}
