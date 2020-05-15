package org.javabot.play;

import org.javabot.security.Ban;
import org.javabot.security.Bans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TestStoreBans {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        Bans bans = new Bans();
        Ban ban = new Ban("idiot!idiot@idiotsville.com");

        ArrayList<Ban> banList = new ArrayList<>();
        boolean success = banList.add(ban);
        bans.setBans(banList);

        storeBans(bans);
    }

    private static synchronized void storeBans(Bans bans) {

        try {

            // create JAXB context and initializing Marshal
            JAXBContext jaxbContext = JAXBContext.newInstance(Bans.class);
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\bans.xml");

            // Writing to XML file
            jaxbMarshaller.marshal(bans, XMLfile);
            // Writing to console
            jaxbMarshaller.marshal(bans, System.out);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }


    }

}
