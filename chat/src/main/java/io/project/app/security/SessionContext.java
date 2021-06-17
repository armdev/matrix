package io.project.app.security;


import io.project.app.domain.Account;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SessionContext.class);

    private Account user = new Account();
    
    private String avatarId;
    
    private String sessionToken;

    public SessionContext() {
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

   
    

}
