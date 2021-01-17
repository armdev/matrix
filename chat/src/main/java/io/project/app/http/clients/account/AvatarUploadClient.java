package io.project.app.http.clients.account;


import io.project.app.api.requests.FileRequest;
import io.project.app.beans.handlers.ApplicationContextHandler;
import io.project.app.security.SessionContext;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import io.project.app.util.FrontendConstants;
import io.project.app.utils.GsonConverter;

@Named(value = "avatarUploadClient")
@Dependent
public class AvatarUploadClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(AvatarUploadClient.class);

    @Inject
    private ApplicationContextHandler applicationContextHandler;

    private  String service_path ;
    
    @Inject
    private SessionContext sessionContext;

    public AvatarUploadClient() {

    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AvatarUploadClient called");
        service_path = applicationContextHandler.getAccountServicePath() + "api/v2/photos";
    }

    //search with file id, not user id!
    public FileRequest findUserAvatarById(String id) {
        FileRequest fileDTO = new FileRequest();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("FindUserAvatar file started ");
            HttpGet request = new HttpGet(service_path + "/account/avatar?id=" + id);

            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", sessionContext.getSessionToken());

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("FindUserAvatar file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileRequest.class);
                    LOGGER.info("File name is " + fileDTO.getFileName());
                    LOGGER.info("File id is " + fileDTO.getId());
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("findUserAvatar File started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("$$$$$Exception caught.", e);
        }
        return fileDTO;
    }

    public FileRequest getFileByUserId(String userId) {
        FileRequest fileDTO = new FileRequest();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("getFileById file started ");
            HttpGet request = new HttpGet(service_path + "/account/avatar/user/id?userId=" + userId);

            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", sessionContext.getSessionToken());

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Get file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileRequest.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Get File started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return fileDTO;
    }

    @Deprecated
    public FileRequest deleteFile(String id) {
        FileRequest fileDTO = new FileRequest();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("Upload file started ");
            HttpDelete request = new HttpDelete(service_path + "/delete?id=" + id);

            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", sessionContext.getSessionToken());

            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("delete file  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileDTO = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), FileRequest.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("delete file started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return fileDTO;
    }

    public String saveFile(FileRequest fileDTO) {
        String fileId = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            LOGGER.info("Upload file started ");
            HttpPut request = new HttpPut(service_path+"/insert");

            String toJson = GsonConverter.toJson(fileDTO);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", FrontendConstants.CONTENT_TYPE_JSON);
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            request.addHeader("Authorization", sessionContext.getSessionToken());
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                LOGGER.info("Upload file started  status code " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    fileId = GsonConverter.fromJson(EntityUtils.toString(httpResponse.getEntity()), String.class);
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            LOGGER.info("Upload file started  request/response time in milliseconds: " + elapsedTime);
        } catch (IOException e) {
            LOGGER.error("Exception caught.", e);
        }
        return fileId;
    }

 
}
