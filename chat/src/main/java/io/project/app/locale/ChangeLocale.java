package io.project.app.locale;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Data;

@Named
@RequestScoped
@Data
public class ChangeLocale implements Serializable {

    private static final long serialVersionUID = 6187192972313171129L;

    private FacesContext context;
    private ExternalContext ex;

    private String languageCode;

    private String english = "en";

    private String russian = "ru";

    private String armenian = "hy";

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public ChangeLocale() {

    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        ex = context.getExternalContext();

    }

    public void changeLocaleToAm() {
        try {
            context.getViewRoot().setLocale(new Locale(armenian));
            ex.getSessionMap().put("localeCode", armenian);
            String viewId = context.getViewRoot().getViewId();
            locale = new Locale(armenian);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(armenian));
           // redirectAfterChangeLocale();
        } catch (Exception ignored) {
        }
    }

    public void changeLocaleToEn() {
        try {
            context.getViewRoot().setLocale(new Locale(english));
            ex.getSessionMap().put("localeCode", english);
            String viewId = context.getViewRoot().getViewId();
            locale = new Locale(english);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(english));
           // redirectAfterChangeLocale();
        } catch (Exception e) {
        }

    }

    public void changeLocaleToRu() {
        try {
            ex.getSessionMap().put("localeCode", russian);
            context.getViewRoot().setLocale(new Locale(russian));
            String viewId = context.getViewRoot().getViewId();
            locale = new Locale(russian);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(russian));
           // redirectAfterChangeLocale();
        } catch (Exception e) {

        }
    }

    public void redirectAfterChangeLocale() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf?locale=" + ex.getSessionMap().get("localeCode"));
        } catch (IOException ex) {
            Logger.getLogger(ChangeLocale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
