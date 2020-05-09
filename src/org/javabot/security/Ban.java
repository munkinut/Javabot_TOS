package org.javabot.security;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "net.munki.jaxb.Bans")
@XmlType(propOrder = {"hostmask"})
public class Ban {

    private String hostmask;

    public Ban() {
    }

    public Ban(String hostmask) {
        this.hostmask = hostmask;
    }

    public String getHostmask() {
        return hostmask;
    }

    public void setHostmask(String hostmask) {
        this.hostmask = hostmask;
    }

}
