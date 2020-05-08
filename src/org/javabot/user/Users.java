package org.javabot.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Users {

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
    public boolean setUsers(ArrayList<User> users) {
        this.users = users;
        return true;
    }

}
