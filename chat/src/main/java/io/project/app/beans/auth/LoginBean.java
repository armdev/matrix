package io.project.app.beans.auth;

import io.project.app.api.requests.LoginRequest;
import io.project.app.api.responses.ApiAccountResponse;
import io.project.app.beans.handlers.JSFContextHandler;
import io.project.app.security.SessionContext;

import io.project.app.http.clients.account.AccountLoginClient;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author armdev
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private AccountLoginClient accountLoginClient;

    @Inject
    private JSFContextHandler jSFContextHandler;

    @Inject
    private SessionContext sessionContext;

  

    private LoginRequest loginModel = new LoginRequest();

    public LoginBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("LoginBean called");
        loginModel = new LoginRequest();

    }

    public String loginUser() {

        final ApiAccountResponse loginResponse = accountLoginClient.loginRequest(loginModel);

        if (loginResponse.getAccount().getId() == null) {
            FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("nouser"), jSFContextHandler.getMessageResourceBundle().getString("nouser"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        if (loginResponse.getAccount().getId() != null) {
            sessionContext.setUser(loginResponse.getAccount());
          
            LOGGER.info("User logged in successfully");
            LOGGER.info("this is Session Token " + sessionContext.getSessionToken());

        

            return "profile";
        }


        FacesMessage msg = new FacesMessage(jSFContextHandler.getMessageResourceBundle().getString("nouser"), jSFContextHandler.getMessageResourceBundle().getString("nouser"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;

    }

    public LoginRequest getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginRequest loginModel) {
        this.loginModel = loginModel;
    }

}
