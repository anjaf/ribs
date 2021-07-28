package uk.ac.ebi.biostudies.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import uk.ac.ebi.biostudies.api.util.HttpTools;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ehsan on 15/03/2017.
 */
@Component
public class CookieFilter implements Filter {

    private Logger logger = LogManager.getLogger(CookieFilter.class.getName());


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        String sessionCookie = HttpTools.getCookie((HttpServletRequest) servletRequest, HttpTools.TOKEN_COOKIE);
        if (Session.getCurrentUser() != null && (sessionCookie == null || !sessionCookie.equalsIgnoreCase(Session.getCurrentUser().token))) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {

    }
}
