package uk.ac.ebi.biostudies.config;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            IOUtils.copy(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("static/error.html")),
                    response.getOutputStream()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
