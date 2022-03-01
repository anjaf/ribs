package uk.ac.ebi.biostudies.service;

import org.apache.lucene.queryparser.classic.ParseException;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.file.download.IDownloadFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileDownloadService {
    IDownloadFile getDownloadFile(String accession, String relativePath, String requestedFilePath, Constants.File.StorageMode storageMode) throws IOException, ParseException;
    void sendFile(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
