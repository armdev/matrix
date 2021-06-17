package io.project.app.beans.handlers;

import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class JSFContextHandler implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JSFContextHandler.class);

    public JSFContextHandler() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("JSFContextHandler init");

    }

    @Produces
    @ViewScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @ViewScoped
    public ExternalContext getExternalContext() {
        return this.getFacesContext().getExternalContext();
    }

    public void publishMessage(String message, String message2) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessageResourceBundle().getString(message), getMessageResourceBundle().getString(message2));
        this.getFacesContext().addMessage(null, msg);
    }

    public PropertyResourceBundle getMessageResourceBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }


    public String getRequestParameter(String paramName) {
        String returnValue = null;
        if (getExternalContext().getRequestParameterMap().containsKey(paramName)) {
            returnValue = (getExternalContext().getRequestParameterMap().get(paramName));
        }
        return returnValue;
    }

}
