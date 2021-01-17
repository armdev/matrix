package io.project.app.http.clients.account;

import com.google.gson.reflect.TypeToken;

import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.domain.Account;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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

    private  String service_path ;

    @Inject
    private SessionContext sessionContext;

    public AccountValidationClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AccountValidationClient called");
        
       service_path= applicationContextHandler.getAccountServicePath() + "api/v2/validations";
    }

    public Integer findUserByPhone(String phone) {

        Integer statusCode = 0;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost(service_path);
            String jsonInString = FrontendGsonConverter.toJson(phone);

            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                statusCode = httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return httpResponse.getStatusLine().getStatusCode();
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return statusCode;
    }

    public Account getAccountByEmail(String email) {
        LOGGER.info("Find user by email " + email);
        Account model = new Account();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/email?email=" + email);
            request.addHeader("charset", "UTF-8");           
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Type listType = new TypeToken<Account>() {
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

  
}