package uk.ac.ebi.biostudies.config;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class CustomErrorReportValve extends ErrorReportValve {

    private Logger logger = LogManager.getLogger(CustomErrorReportValve.class.getName());

    @Override
    protected void report(Request request, Response response, Throwable throwable) {
        int status = response.getStatus();
        if ( status < 400) return;

        if (throwable!=null) {
            logger.error(throwable);
        }
        try {
            String errorPage = new String( Files.readAllBytes( Paths.get(
                            this.getClass().getClassLoader().getResource("static/error.html").toURI()
                    )));
            response.setContentType(String.valueOf(ContentType.TEXT_HTML));
            response.setContentLength(errorPage.length());
            response.getOutputStream().print(errorPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
