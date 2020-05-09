package org.javabot.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.logging.Logger;

@XmlRootElement
public class Users {

    Logger log = Logger.getLogger(this.getClass().getName());

    private ArrayList<User> users;

    public Users() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    // XmLElementWrapper generates a wrapper element around XML representation
    // XmlElement sets the name of the entities in collection
    @XmlElement(name = "user")
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
