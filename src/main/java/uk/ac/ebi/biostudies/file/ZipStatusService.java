package uk.ac.ebi.biostudies.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.IndexConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ehsan on 20/03/2017.
 */

@Service
@Scope("singleton")
public class ZipStatusService {
    private static Set<String> filesBeingProcessed = new HashSet<>();

    @Autowired
    IndexConfig indexConfig;

    public static void addFile(String file) {
        filesBeingProcessed.add(file);
    }

    public static void removeFile(String file) {
        filesBeingProcessed.remove(file);
    }

    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "must-revalidate");
        response.addHeader("Expires", "Fri, 16 May 2008 10:00:00 GMT"); // some date in the past

        try (PrintWriter out = response.getWriter()) {
            String status = "invalid";
            try {
                String uuid = UUID.fromString(request.getParameter("filename")).toString();

                boolean fileExists = new File(indexConfig.getZipTempDir() + "/" + uuid + ".zip").exists();

                if (fileExists) {
                    if (filesBeingProcessed.contains(uuid)) {
                        status = "processing";
                    } else {
                        status = "done";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            out.println("{ \"status\": \"" + status + "\"}");
            out.flush();
        }

    }

}
