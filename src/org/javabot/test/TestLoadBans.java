package org.javabot.test;

import org.javabot.security.Ban;
import org.javabot.security.Bans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.logging.Logger;

public class TestLoadBans {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        Bans bans = loadBans();
        for (Ban ban:bans.getBans()) {
            System.out.println("Ban mask = " + ban.getHostmask());
        }
    }

    private static synchronized Bans loadBans() {
        Bans bans = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Bans.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\bans.xml");

            // this will create Java object - country from the XML file
            bans = (Bans) jaxbUnmarshaller.unmarshal(XMLfile);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
        return bans;
    }
}
