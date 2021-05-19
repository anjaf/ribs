package uk.ac.ebi.biostudies.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.ebi.biostudies.api.util.HttpTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomRememberMeCookieService implements RememberMeServices {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String cookieName = HttpTools.TOKEN_COOKIE;
    private String cookieDomain;
    private String parameter = "r";
    private boolean alwaysRemember;


    @Autowired
    UserSecurityService userSecurityService;

    @Override
    public Authentication autoLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String rememberMeCookie = this.extractRememberMeCookie(httpServletRequest);
        if (rememberMeCookie == null) {
            return null;
        }
        else {
            this.logger.debug("Remember-me cookie detected");
            if (rememberMeCookie.length() == 0) {
                this.logger.debug("Cookie was empty");
                this.cancelCookie(httpServletRequest, httpServletResponse);
                return null;
            } else {
                User user = null;

                try {
                        user = userSecurityService.createUserFromJSONResponse(userSecurityService.sendAuthenticationCheckRequest(rememberMeCookie));
                        if(user==null || user.getAllow()==null)
                            return null;
                        this.logger.debug("Remember-me cookie accepted");
                        return this.createSuccessfulAuthentication(httpServletRequest, user);
                } catch (CookieTheftException var6) {
                    this.cancelCookie(httpServletRequest, httpServletResponse);
                    throw var6;
                } catch (UsernameNotFoundException var7) {
                    this.logger.debug("Remember-me login was valid but corresponding user not found.", var7);
                } catch (InvalidCookieException var8) {
                    this.logger.debug("Invalid remember-me cookie: " + var8.getMessage());
                } catch (AccountStatusException var9) {
                    this.logger.debug("Invalid UserDetails: " + var9.getMessage());
                } catch (Throwable var10) {
                    this.logger.debug(var10.getMessage());
                }

                this.cancelCookie(httpServletRequest, httpServletResponse);
                return null;
            }
        }
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug("Interactive login attempt was unsuccessful.");
//        this.cancelCookie(request, response);
        this.onLoginFail(request, response);
    }

    protected void onLoginFail(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public void loginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        if (!this.rememberMeRequested(httpServletRequest, this.parameter)) {
            this.logger.debug("Remember-me login not requested.");
        } else {
            this.onLoginSuccess(httpServletRequest, httpServletResponse, authentication);
        }


    }

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        return true;
//        if (this.alwaysRemember) {
//            return true;
//        } else {
//            String paramValue = request.getParameter(parameter);
//            if (paramValue != null && (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on") || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1"))) {
//                return true;
//            } else {
//                if (this.logger.isDebugEnabled()) {
//                    this.logger.debug("Did not send remember-me cookie (principal did not set parameter '" + parameter + "')");
//                }
//
//                return false;
//            }
//        }
    }

    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String token = ((User)successfulAuthentication.getPrincipal()).getToken();
        if (!StringUtils.hasLength(token)) {
            this.logger.debug("Unable to retrieve token for cookie");
        }
        Integer maxAge = 31557600;
        HttpTools.setCookie(response, HttpTools.TOKEN_COOKIE, token, maxAge);
        HttpTools.setCookie(response, HttpTools.AUTH_MESSAGE_COOKIE, null, 0);

    }

    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, User user) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
//        auth.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return auth;
    }

    protected String extractRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }

            return null;
        } else {
            return null;
        }
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        this.logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(this.cookieName, (String)null);
        cookie.setMaxAge(0);
        cookie.setPath(this.getCookiePath(request));
        if (this.cookieDomain != null) {
            cookie.setDomain(this.cookieDomain);
        }

        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

}
