package io.project.app.beans.friends;

import io.project.app.security.SessionContext;

import io.project.app.http.clients.friends.FriendHttpClient;
import io.project.app.http.clients.friends.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;

@Named(value = "friendListBean")
@ViewScoped
public class FriendListBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FriendListBean.class);

    private static final long serialVersionUID = -8730742845262768978L;

    @Inject
    private FriendHttpClient friendHttpClient;

    @Inject
    private SessionContext sessionContext;

    private List<Person> personList = new ArrayList<>();

    public FriendListBean() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("FriendListBean bean init");
        if (sessionContext.getUser().getId() != null) {
            LOGGER.info("Friends:::");
            personList = friendHttpClient.getAllPersons().getPersons();
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

}
