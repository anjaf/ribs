/*
 * Copyright 2009-2016 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package uk.ac.ebi.biostudies.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import net.minidev.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.SecurityConfig;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserSecurityService {
    public static final String X_SESSION_TOKEN = "X-Session-Token";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Cache<Object, Object> userAuthCache;
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private SecurityConfig securityConfig;

    private JsonNode sendAuthenticationCheckRequest(String token) throws IOException {
        JsonNode responseJSON;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(securityConfig.getAuthCheckUrl());
        httpGet.setHeader(X_SESSION_TOKEN, token);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            responseJSON = mapper.readTree(EntityUtils.toString(response.getEntity()));
        }

        return responseJSON;
    }

    private JsonNode sendLoginRequest(String username, String password) throws IOException {
        JsonNode responseJSON;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(securityConfig.getLoginUrl());
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        ObjectNode creds = mapper.createObjectNode();
        creds.put("login", username);
        creds.put("password", password);
        httpPost.setEntity(new StringEntity( mapper.writeValueAsString(creds)));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            responseJSON = mapper.readTree(EntityUtils.toString(response.getEntity()));
        }
        return responseJSON;
    }


    public UserSecurityService() {
        // TODO: move password cache timeout to config file
        userAuthCache = CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .build();
    }

    public User login(String username, String password) throws IOException {
        User user = createUserFromJSONResponse(sendLoginRequest(username, password));
        if (user == null) return null;
        return user;
    }

    public void logout() {
        userAuthCache.invalidate(Session.getCurrentUser().getToken());
    }

    User checkAccess(String token) throws IOException {
        if (token == null) return null;

        User user = (User) userAuthCache.getIfPresent(token);
        if (user != null && token.equals(user.getToken())) {
            logger.debug("Found authentication for user [{}] in local cache", user.getLogin());
            return user;
        }

        logger.debug("Trying to authenticate user remotely");
        user = createUserFromJSONResponse(sendAuthenticationCheckRequest(token));
        if (user == null) return null;
        return user;
    }

    private User createUserFromJSONResponse(JsonNode responseJSON) throws IOException {
        User user;
        if (responseJSON == null || !responseJSON.has("status") ||
                (responseJSON.has("status") && !responseJSON.get("status").asText().equalsIgnoreCase("ok"))) {
            return null;
        }
        user = new User();
        user.setFullName(responseJSON.get("fullname").textValue());
        user.setLogin(responseJSON.get("username").textValue());
        user.setToken(responseJSON.get("sessid").textValue());
        String[] allow = mapper.convertValue(responseJSON.get("allow"), String[].class);
        String[] deny = mapper.convertValue(responseJSON.get("deny"), String[].class);
        Set allowedSet = Sets.difference(Sets.newHashSet(allow), Sets.newHashSet(deny));
        user.setAllow((String[]) allowedSet.toArray(new String[allowedSet.size()]));
        user.setDeny(deny);
        user.setSuperUser(responseJSON.get("superuser").asBoolean(false));
        userAuthCache.put(user.getToken(), user);
        return user;
    }

    //TODO: handle password flow
    public String remindPassword(String nameOrEmail, String accession) throws IOException {
       /* nameOrEmail = StringEscapeUtils.escapeXml(nameOrEmail);
        accession = null != accession ? accession.toUpperCase() : "";

        try {
            List users = null;

            Object userIds = this.userMap.getValue(accession);
            if (userIds instanceof Set) {
                Set<String> uids = (Set<String>) (userIds);
                String ids = StringTools.arrayToString(uids.toArray(new String[uids.size()]), ",");

                users = this.saxon.evaluateXPath(
                        getRootNode()
                        , "/users/user[(name|email = '" + nameOrEmail + "') and id = (" + ids + ")]"
                );
            }

            String reportMessage;
            String result = "Unable to find matching account information, please contact us for assistance.";
            if (null != users && users.size() > 0) {
                if (1 == users.size()) {
                    String username = this.saxon.evaluateXPathSingle((NodeInfo) users.get(0), "string(name)").getStringValue();
                    String email = this.saxon.evaluateXPathSingle((NodeInfo) users.get(0), "string(email)").getStringValue();
                    String password = this.saxon.evaluateXPathSingle((NodeInfo) users.get(0), "string(password)").getStringValue();

                    getApplication().sendEmail(
                            getPreferences().getString("bs.password-remind.originator")
                            , new String[]{email}
                            , getPreferences().getString("bs.password-remind.subject")
                            , "Dear " + username + "," + StringTools.EOL
                                    + StringTools.EOL
                                    + "Your ArrayExpress account information is:" + StringTools.EOL
                                    + StringTools.EOL
                                    + "    User name: " + username + StringTools.EOL
                                    + "    Password: " + password + StringTools.EOL
                                    + StringTools.EOL
                                    + "Regards," + StringTools.EOL
                                    + "ArrayExpress." + StringTools.EOL
                                    + StringTools.EOL
                    );


                    reportMessage = "Sent account information to the user [" + username + "], email [" + email + "], accession [" + accession + "]";
                    result = "Account information sent, please check your email";
                } else {
                    // multiple results, report this to administrators
                    reportMessage = "Request failed: found multiple users for name/email [" + nameOrEmail + "] accessing [" + accession + "].";
                }
            } else {
                // no results, report this to administrators
                reportMessage = "Request failed: found no users for name/email [" + nameOrEmail + "] accessing [" + accession + "].";
            }

            getApplication().sendEmail(
                    getPreferences().getString("bs.password-remind.originator")
                    , getPreferences().getStringArray("bs.password-remind.recipients")
                    , "ArrayExpress account information request"
                    , reportMessage + StringTools.EOL
                            + StringTools.EOL
                            + "Sent by [${variable.appname}] running on [${variable.hostname}]" + StringTools.EOL
                            + StringTools.EOL
            );
            return result;

        } catch (XPathException x) {
            throw new RuntimeException(x);
        }*/
        return null;
    }

}
