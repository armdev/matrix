package io.project.app.http.clients.friends;

import com.google.gson.reflect.TypeToken;

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
public class FriendHttpClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(FriendHttpClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private String service_path;

    @Inject
    private SessionContext sessionContext;

    public FriendHttpClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("FriendHttpClient called");

        service_path = applicationContextHandler.getFriendServicePath()+ "api/v2/people";
    }

    public PersonResponse getAllPersons() {
        LOGGER.info("Find all persons from neo4j ");
        PersonResponse model = new PersonResponse();
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(service_path + "/person/all/data");
            request.addHeader("charset", "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Type listType = new TypeToken<PersonResponse>() {
                    }.getType();
                    model = FrontendGsonConverter.fromWithDateTime().fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Find all request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return model;
    }

   
}
