package uk.ac.ebi.biostudies.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ZipDownloadService {

    void sendZip(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
