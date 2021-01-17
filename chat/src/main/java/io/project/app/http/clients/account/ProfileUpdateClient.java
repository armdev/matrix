package io.project.app.http.clients.account;


import io.project.app.api.requests.PasswordUpdateRequest;
import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.domain.Account;
import io.project.app.security.SessionContext;
import io.project.app.util.FrontendGsonConverter;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import io.project.app.util.FrontendConstants;
import io.project.app.utils.GsonConverter;

@Named(value = "profileUpdateClient")
@Dependent
public class ProfileUpdateClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ProfileUpdateClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public ProfileUpdateClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("ProfileUpdateClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/v2/profiles";
    }

    public int changePassword(String id, String password) {

        int status = 0;

        long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("Change Password called ");
            HttpPut request = new HttpPut(service_path + "/account/password");

            PasswordUpdateRequest passwordUpdate = new PasswordUpdateRequest(id, password);
            String toJson = GsonConverter.toJson(passwordUpdate);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", sessionContext.getSessionToken());
            request.setEntity(params);
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    LOGGER.info("Password update status is " + status);
                    status = 200;
                } else {
                    status = 500;
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("ChangePassword request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return status;
    }

    public Account updateAccountProfile(Account account) {

        Account returnedModel = new Account();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPut request = new HttpPut(service_path + "/account");

            String jsonInString = FrontendGsonConverter.fromWithDateTime().toJson(account);

            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", sessionContext.getSessionToken());
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            LOGGER.info("request " + request.toString());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                LOGGER.info("" + httpResponse.getStatusLine().getReasonPhrase());
                LOGGER.info("" + httpResponse.getStatusLine().toString());

                if (httpResponse.getStatusLine().getStatusCode() == 200) {

                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        
                        returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Account.class);
  
                    }

                    return returnedModel;
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return returnedModel;
    }

    public Account getAccountById(String id) {
        LOGGER.info("Find user by id " + id);
        Account returnedModel = new Account();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/account/one?id=" + id);
            request.addHeader("charset", "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    
                    returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), Account.class);

                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Find user by id request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return returnedModel;
    }

}
