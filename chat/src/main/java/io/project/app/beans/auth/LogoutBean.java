package io.project.app.beans.auth;

import io.project.app.beans.handlers.JSFContextHandler;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@Named
@RequestScoped
public class LogoutBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LogoutBean.class);
    private static final long serialVersionUID = 1L;

    @Inject
    private JSFContextHandler jSFContextHandler;


    public LogoutBean() {

    }

    public String doLogout() {
        LOGGER.info("Logout Called ");


        String localeCode = (String) jSFContextHandler.getExternalContext().getSessionMap().get("localeCode");

        jSFContextHandler.getExternalContext().getSessionMap().remove("sessionContext");
        jSFContextHandler.getExternalContext().getApplicationMap().remove("sessionContext");
        jSFContextHandler.getExternalContext().getSessionMap().clear();
        HttpSession session = (HttpSession) jSFContextHandler.getExternalContext().getSession(true);
        session.invalidate();

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        HttpServletResponse response = (HttpServletResponse) jSFContextHandler.getExternalContext().getResponse();
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        jSFContextHandler.getExternalContext().getSessionMap().put("localeCode", localeCode);

        return "logout";
    }

}
