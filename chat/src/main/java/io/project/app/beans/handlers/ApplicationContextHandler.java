package io.project.app.beans.handlers;

import javax.inject.Named;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class ApplicationContextHandler implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationContextHandler.class);
    private static final String ACCOUNT_SERVICE = "account/";
    private static final String FRIEND_SERVICE = "friend/";
  
    private String gatewayUrl = this.getBackendPathBundle().getString("gateway");

    public ApplicationContextHandler() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("ApplicationContextHandler init");
        gatewayUrl = this.getBackendPathBundle().getString("gateway");

    }

    public PropertyResourceBundle getBackendPathBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{path}", PropertyResourceBundle.class);
    }
   

    public String getAccountServicePath() {
        return gatewayUrl + ACCOUNT_SERVICE;
    }

    public String getFriendServicePath() {
        return gatewayUrl + FRIEND_SERVICE;
    }


    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

}
