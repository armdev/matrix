package io.project.app.beans.auth;

import io.project.app.api.requests.RegisterRequest;
import io.project.app.beans.handlers.JSFContextHandler;
import io.project.app.domain.Account;
import io.project.app.http.clients.account.AccountRegistrationClient;
import io.project.app.http.clients.account.AccountValidationClient;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author armdev
 */
@Named(value = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(RegisterBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private AccountRegistrationClient accountRegistrationClient;

    @Inject
    private AccountValidationClient accountValidationClient;
    
    @Inject
    private JSFContextHandler jSFContextHandler;

    private RegisterRequest register;

    public RegisterBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("RegisterBean called");
        register = new RegisterRequest();

    }

    public String registerAccount() {
        LOGGER.info("Register new user");
        Account returnedResponse = accountValidationClient.getAccountByEmail(register.getEmail());
        if (returnedResponse.getId() != null) {
            LOGGER.info("User tried to register with busy email ");
            FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("emailbusy"), jSFContextHandler.getMessageResourceBundle().getString("emailbusy"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        Account registerNewAccount = accountRegistrationClient.registerNewAccount(register);

        if (registerNewAccount.getId() != null) {
            return "login";
        } else {
            return "error";
        }
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        if (uiInputPassword != null) {
            String passwd = uiInputPassword.getLocalValue() == null ? ""
                    : uiInputPassword.getLocalValue().toString();
            String passwordId = uiInputPassword.getClientId();

            // get confirm password
            UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
            String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                    : uiInputConfirmPassword.getLocalValue().toString();

            // Let required="true" do its job.
            if (passwd.isEmpty() || confirmPassword.isEmpty()) {
                return;
            }

            if (!passwd.equals(confirmPassword)) {

                FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("mandatory"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(passwordId, msg);
                fc.renderResponse();

            } else {
                FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("mandatory"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(passwordId, msg);
                fc.renderResponse();
            }
        }

    }

 

    public void publishMessage() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, jSFContextHandler.getMessageResourceBundle().getString("registersuccess"), jSFContextHandler.getMessageResourceBundle().getString("registersuccess"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void publishMessageError() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, jSFContextHandler.getMessageResourceBundle().getString("registerfail"), jSFContextHandler.getMessageResourceBundle().getString("registerfail"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
