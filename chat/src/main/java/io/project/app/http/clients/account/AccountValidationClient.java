package io.project.app.http.clients.account;

import com.google.gson.reflect.TypeToken;
import io.project.app.api.responses.ApiAccountResponse;

import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.security.SessionContext;
import io.project.app.util.FrontendGsonConverter;
import io.project.app.util.FrontendConstants;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@Dependent
public class AccountValidationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AccountValidationClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public AccountValidationClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AccountValidationClient called");

        service_path = applicationContextHandler.getAccountServicePath() + "api/v2/validations";
    }

    public ApiAccountResponse getAccountByEmail(String email) {
        LOGGER.info("Find user by email " + email);
        ApiAccountResponse model = new ApiAccountResponse();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/find/email?email=" + email);
            request.addHeader("charset", "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Type listType = new TypeToken<ApiAccountResponse>() {
                    }.getType();
                    model = FrontendGsonConverter.fromWithDateTime().fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Find user by id request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

    public ApiAccountResponse getAccountById(String id) {
        LOGGER.info("Find user by id " + id);
        ApiAccountResponse model = new ApiAccountResponse();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/find/by/id?id=" + id);
            request.addHeader("charset", "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Type listType = new TypeToken<ApiAccountResponse>() {
                    }.getType();
                    model = FrontendGsonConverter.fromWithDateTime().fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Find user by id request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

    public ApiAccountResponse getAllAccounts() {

        ApiAccountResponse model = new ApiAccountResponse();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/find/all/accounts");
            request.addHeader("charset", "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("AllAccounts Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Type listType = new TypeToken<ApiAccountResponse>() {
                    }.getType();
                    model = FrontendGsonConverter.fromWithDateTime().fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Find all accounts: request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

}
