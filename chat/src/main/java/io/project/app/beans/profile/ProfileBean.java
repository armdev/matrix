package io.project.app.beans.profile;


import io.project.app.domain.Account;
import io.project.app.security.SessionContext;

import io.project.app.http.clients.account.ProfileUpdateClient;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;

@Named(value = "profileBean")
@ViewScoped
public class ProfileBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProfileBean.class);
    private static final long serialVersionUID = -8730742845262768978L;

    @Inject
    private ProfileUpdateClient profileUpdateClient;

    @Inject
    private SessionContext sessionContext;

    private Account userModel = null;

    private String userPassword;

    public ProfileBean() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("profile bean init");
        if (sessionContext.getUser().getId() != null) {
            LOGGER.info("Loading current logged user profile");
            userModel = profileUpdateClient.getAccountById(sessionContext.getUser().getId());
        }
    }

    public String updatePassword() {

        int changePassword = profileUpdateClient.changePassword(userModel.getId(), userPassword);

        if (changePassword != 200) {

            FacesMessage msg = new FacesMessage("Notificaation", "Password update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            userPassword = null;

        } else {
            FacesMessage msg = new FacesMessage("Notificaation", "Password update success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            userPassword = null;
        }
        return null;
    }

    public String updateProfile() {

        Account updateProfile = profileUpdateClient.updateAccountProfile(userModel);

        if (updateProfile.getEmail() != null) {
            sessionContext.setUser(updateProfile);
            FacesMessage msg = new FacesMessage("System message", "Profile updated");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error", "Profile update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return null;
    }

  

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Account getUserModel() {
        return userModel;
    }

    public void setUserModel(Account userModel) {
        this.userModel = userModel;
    }

}
