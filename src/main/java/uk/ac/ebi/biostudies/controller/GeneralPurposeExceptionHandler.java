package uk.ac.ebi.biostudies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by ehsan on 10/04/2017.
 */
@ControllerAdvice
public class GeneralPurposeExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LogManager.getLogger(GeneralPurposeExceptionHandler.class.getName());

    @ExceptionHandler(value = { Exception.class })
    protected ModelAndView handleConflict(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return new ModelAndView("404");
    }
}
