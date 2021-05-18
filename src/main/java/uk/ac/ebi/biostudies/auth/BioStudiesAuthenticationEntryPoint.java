package uk.ac.ebi.biostudies.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BioStudiesAuthenticationEntryPoint{}  /**implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StringBuffer url = request.getRequestURL();
        if(isAjaxRequest(request)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
        }else{
            String scheme = request.getScheme() + "://";
            String serverName = request.getServerName();
            String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
            String contextPath = request.getContextPath();
            response.sendRedirect(scheme + serverName + serverPort + contextPath+"?login=true");
            }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

}**/
