package uk.ac.ebi.biostudies.service;

import uk.ac.ebi.biostudies.api.util.DataTableColumnInfo;

import java.util.Map;

public interface FilePaginationService {
    String getFileList(String accession, int start, int pageSize, String search, int draw, Map<Integer, DataTableColumnInfo> dataTableUiResult);
    String getColumns(String accession);

}
