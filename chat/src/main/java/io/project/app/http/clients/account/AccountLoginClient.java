package io.project.app.http.clients.account;


import io.project.app.api.requests.LoginRequest;
import io.project.app.api.responses.ApiAccountResponse;
import io.project.app.beans.handlers.ApplicationContextHandler;

import io.project.app.security.SessionContext;
import io.project.app.util.FrontendGsonConverter;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@Named
@Dependent
public class AccountLoginClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AccountLoginClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public AccountLoginClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AccountLoginClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/v2/accounts";
    }

    public ApiAccountResponse loginRequest(LoginRequest model) {

        ApiAccountResponse returnedModel = new ApiAccountResponse();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut request = new HttpPut(service_path + "/login");
            String toJson = FrontendGsonConverter.toJson(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status Code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Header firstHeader = httpResponse.getFirstHeader("Authorization");
                    LOGGER.info("Header name " + firstHeader.getName());
                    LOGGER.info("Header value " + firstHeader.getValue());
                    if (firstHeader.getValue() != null) {
                        sessionContext.setSessionToken(firstHeader.getValue());
                    } else {
                        LOGGER.error("No session token found");
                        return returnedModel;
                    }

                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        returnedModel = FrontendGsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), ApiAccountResponse.class);

                        return returnedModel;
                    }
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("request/response time in milliseconds: " + elapsedTime);

        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return returnedModel;
    }

}
