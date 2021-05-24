package uk.ac.ebi.biostudies.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.biostudies.api.util.HttpTools;
import uk.ac.ebi.biostudies.auth.RestBasedAuthenticationProvider;
import uk.ac.ebi.biostudies.auth.Session;
import uk.ac.ebi.biostudies.auth.User;
import uk.ac.ebi.biostudies.auth.UserSecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ehsan on 15/03/2017.
 */

@RestController
public class Authentication {

    private Logger logger = LogManager.getLogger(Authentication.class.getName());


    @Autowired
    UserSecurityService users;
    @Autowired
    RestBasedAuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnURL = request.getHeader(HttpTools.REFERER_HEADER);
        String username = request.getParameter("u");
        String password = request.getParameter("p");
        String remember = request.getParameter("r");
        String requestURL = request.getRequestURL().toString();
        String applicationRoot = requestURL.substring(0, requestURL.indexOf(request.getServletPath()));

        if (returnURL !=null && !returnURL.replaceFirst("https","").replaceFirst("http","").startsWith(applicationRoot.replaceFirst("https","").replaceFirst("http",""))) {
            returnURL = applicationRoot;
        }
        boolean isLoginSuccessful = false;
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        org.springframework.security.core.Authentication userPassAuth = authenticationProvider.authenticate(authRequest);
        isLoginSuccessful = userPassAuth != null;
        // 31,557,600 is a standard year in seconds
        Integer maxAge = "on".equals(remember) ? 31557600 : null;
//        Integer maxAge = 31557600;
        if (isLoginSuccessful) {
            logger.info("Successfully authenticated user [{}]", username);
            HttpTools.setCookie(response, HttpTools.TOKEN_COOKIE, ((User)userPassAuth.getPrincipal()).getToken(), maxAge);
            HttpTools.setCookie(response, HttpTools.AUTH_MESSAGE_COOKIE, null, 0);
        } else {
            HttpTools.setCookie(response, HttpTools.TOKEN_COOKIE, null, null);
            String message = Session.getUserMessage();
            if(message!=null && message.length()>0){
                HttpTools.setCookie(response, HttpTools.AUTH_MESSAGE_COOKIE, URLEncoder.encode(message, "UTF-8"), 5);
            }else {
                HttpTools.setCookie(response, HttpTools.AUTH_MESSAGE_COOKIE, URLEncoder.encode("Invalid username or password", "UTF-8"), 5);
            }
            Session.clearMessage();
        }

        sendRedirect(response, returnURL, isLoginSuccessful);
    }

    private void sendRedirect(HttpServletResponse response, String returnURL, boolean isSuccessful) throws IOException {
        if (null != returnURL) {
            if (isSuccessful && returnURL.matches("^http[:]//www(dev)?[.]ebi[.]ac[.]uk/.+")) {
                returnURL = returnURL.replaceFirst("^http[:]//", "https://");
            }
            logger.debug("Will redirect to [{}]", returnURL);
            response.sendRedirect(returnURL);
        } else {
            response.setContentType("text/plain; charset=UTF-8");
            // Disable cache no matter what (or we're fucked on IE side)
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Expires", "Fri, 16 May 2008 10:00:00 GMT"); // some date in the past
        }
    }

    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public void logout(@CookieValue(HttpTools.TOKEN_COOKIE) String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = Session.getCurrentUser();
            logger.info("Logging out user [{}]", user.getLogin());
            users.logout();
            HttpTools.setCookie(response, HttpTools.TOKEN_COOKIE, null, 0);
            HttpTools.setCookie(response, HttpTools.AUTH_MESSAGE_COOKIE, null, 0);
            HttpSession session = request.getSession(false);
            if (session != null) {
                this.logger.debug("Invalidating session: " + session.getId());
                session.invalidate();
            }
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication((org.springframework.security.core.Authentication)null);
            SecurityContextHolder.clearContext();
            String returnURL = request.getHeader(HttpTools.REFERER_HEADER);
            sendRedirect(response, returnURL, true);
        } catch (Exception ex) {
            logger.error("logout exception", ex);
        }
    }
}
