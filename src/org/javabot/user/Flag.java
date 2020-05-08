package org.javabot.user;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "net.munki.jaxb.Users")
@XmlType(propOrder = { "name", "truth"})
public class Flag {

    public static final String FRIEND = "friend";
    public static final String OP = "op";
    public static final String MASTER = "master";
    public static final String OWNER = "owner";
    public static final String VOICE = "voice";

    private String name;
    private boolean truth;

    public Flag() {
        this.name = FRIEND;
        this.truth = true;
    }

    public Flag(String name, boolean truth) {
        this.name = name;
        this.truth = truth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }

}
