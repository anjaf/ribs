package uk.ac.ebi.biostudies.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import uk.ac.ebi.biostudies.config.SecurityConfig;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ehsan on 15/03/2017.
 */
@WebFilter(urlPatterns = "/api/v1/index/*")
public class AdminFilter implements Filter {

    private Logger logger = LogManager.getLogger(AdminFilter.class.getName());


    @Autowired
    UserSecurity users;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        HttpServletRequest request= ((HttpServletRequest) servletRequest);

        try {
            String ip = request.getHeader("X-Cluster-Client-IP");
            if (ip == null || ip.equalsIgnoreCase("")) {
                logger.warn("Header X-Cluster-Client-IP not found");
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.equalsIgnoreCase("")) {
                logger.warn("Header X-Forwarded-For not found");
                ip = request.getRemoteAddr();
            }

            String hn = InetAddress.getByName(ip).getCanonicalHostName();
            Pattern allow = Pattern.compile(securityConfig.getAdminIPWhitelist());
            Matcher matcher = allow.matcher(hn);
            if (!matcher.matches()) {
                logger.warn("Rejecting admin URL request from {} {}", ip, hn);
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            logger.warn("Accepting admin URL request from {} {}", ip, hn);
        } catch (Exception ex) {
            logger.error(ex);
            return;
        }

        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {

    }
}
