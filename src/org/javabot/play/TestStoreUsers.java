package org.javabot.play;

import org.javabot.user.Flags;
import org.javabot.user.User;
import org.javabot.user.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TestStoreUsers {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        Users users = new Users();
        User user = new User("munki", "munki123");

        Flags flags = new Flags();
        user.setFlags(flags);
        ArrayList<User> userList = new ArrayList<>();
        boolean success = userList.add(user);
        users.setUsers(userList);

        storeUsers(users);
    }

    private static synchronized void storeUsers(Users users) {

        try {

            // create JAXB context and initializing Marshal
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\users.xml");

            // Writing to XML file
            jaxbMarshaller.marshal(users, XMLfile);
            // Writing to console
            jaxbMarshaller.marshal(users, System.out);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }


    }

}