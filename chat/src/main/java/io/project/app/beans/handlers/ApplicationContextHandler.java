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
    private static final String DICTIONARY_SERVICE = "dictionary/";
    private static final String ENCOUNTER_SERVICE = "encounter/";
    private static final String PRACTITIONER_SERVICE = "practitioner/";
    private static final String PATIENT_SERVICE = "patient/";
    private static final String EVENT_SERVICE = "event/";
    private static final String TELEGRAM_SERVICE = "telegram/";

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
    
     public String getTelegramServicePath() {
        return gatewayUrl + TELEGRAM_SERVICE;
    }

    public String getEncounterServicePath() {
        return gatewayUrl + ENCOUNTER_SERVICE;
    }

    public String getPatientServicePath() {
        return gatewayUrl + PATIENT_SERVICE;
    }

    public String getPractitionerServicePath() {
        return gatewayUrl + PRACTITIONER_SERVICE;
    }

    public String getAccountServicePath() {
        return gatewayUrl + ACCOUNT_SERVICE;
    }

    public String getDictionaryServicePath() {
        return gatewayUrl + DICTIONARY_SERVICE;
    }

    public String getEventServicePath() {
        return gatewayUrl + EVENT_SERVICE;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

}
