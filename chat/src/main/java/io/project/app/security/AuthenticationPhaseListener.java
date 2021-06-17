package io.project.app.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.el.ELException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AuthenticationPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private static HashMap<String, String> pagePermissionMapping = null;

    @Override
    public void afterPhase(PhaseEvent event) {
       // printLog(event, "after phase:");
    }

    @Override
    public synchronized void beforePhase(PhaseEvent event) {
       // printLog(event, "before phase:");
        FacesContext context = event.getFacesContext();
        ExternalContext ex = context.getExternalContext();
        try {
            if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
                loadPages();
            }
            String viewId = "/index.xhtml";

            if (context.getViewRoot() != null && context.getViewRoot().getViewId() != null) {
                viewId = context.getViewRoot().getViewId();
            }

            final String permissions = (pagePermissionMapping.get(viewId));

            SessionContext sessionContext = context.getApplication().evaluateExpressionGet(context, "#{sessionContext}", SessionContext.class);

            if (permissions != null && permissions.contains("PUBLIC")) {
                return;
            }

            if (sessionContext.getUser().getId() == null && !viewId.contains("index.xhtml") || !Objects.requireNonNull(permissions).contains("LOGGED")) {
                System.out.println("ViewId  " + viewId);
                System.out.println("permissions  " + permissions);
                System.out.println("URL  " + ex.getRequestContextPath() + "/index.jsf?illegalAccess");
                ex.redirect(ex.getRequestContextPath() + "/index.jsf?illegalAccess");
                ;
            }
        } catch (IOException | ELException ex1) {
            System.out.println("ERROR " + ex1.getMessage());

            try {

                ex.redirect(ex.getRequestContextPath() + "/error/404.jsf?error");
            } catch (IOException ex2) {

            }
        }

    }

    private void loadPages() {
        ////System.out.println("Loading Pages.....");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (pagePermissionMapping == null) {
            pagePermissionMapping = new HashMap();
            try {
                ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "accessProp");
                if (bundle != null) {
                    Enumeration e = bundle.getKeys();
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        String value = bundle.getString(key);
                     ///   System.out.println("PAGES KEY + VALUE " + key + " " + value);
                        pagePermissionMapping.put(key, value);
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    protected void printLog(PhaseEvent event, String msg) {
        UIViewRoot view = event.getFacesContext().getViewRoot();
        String viewID = "no view";
        if (view != null) {
            viewID = view.getViewId();
        }
        System.out.println(msg + event.getPhaseId() + " " + viewID);

        printRequestParameters(event);
        printRequestAttributes(event);
        printSessionAttributes(event);

    }

    private void printSessionAttributes(PhaseEvent event) {
        Map<String, Object> sessAttrs = event.getFacesContext().getExternalContext().getSessionMap();
        StringBuilder sb = new StringBuilder();
        for (String key : sessAttrs.keySet()) {
            sb.append("(" + key + "=" + sessAttrs.get(key) + ") ");
        }
        System.out.println("Session Attributes : " + sb.toString());
    }

    private void printRequestAttributes(PhaseEvent event) {
        Map<String, Object> reqAttrs = event.getFacesContext().getExternalContext().getRequestMap();
        StringBuilder sb = new StringBuilder();
        for (String key : reqAttrs.keySet()) {
            sb.append("(" + key + "=" + reqAttrs.get(key) + ") ");
        }
        System.out.println("Request Attributes : " + sb.toString());
    }

    private void printRequestParameters(PhaseEvent event) {
        Map<String, String> reqParams = event.getFacesContext().getExternalContext().getRequestParameterMap();
        StringBuilder sb = new StringBuilder();
        for (String key : reqParams.keySet()) {
            sb.append("(" + key + "=" + reqParams.get(key) + ") ");
        }
        System.out.println("Request Parameters : " + sb.toString());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
