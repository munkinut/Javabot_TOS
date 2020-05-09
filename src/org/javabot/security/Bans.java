package org.javabot.security;

import org.javabot.user.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Bans {

    private ArrayList<Ban> bans;

    public Bans() {
        bans = new ArrayList<>();
    }

    public ArrayList<Ban> getBans() {
        return bans;
    }

    @XmlElement(name = "ban")
    public void setBans(ArrayList<Ban> bans) {
        this.bans = bans;
    }


}
