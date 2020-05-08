package org.javabot.test;

import org.javabot.user.Flag;
import org.javabot.user.Flags;
import org.javabot.user.User;
import org.javabot.user.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class TestLoadUsers {

    public static void main(String[] args) {
        Users users = loadUsers();
        for (User user:users.getUsers()) {
            System.out.println("User nick = " + user.getNick());
            System.out.println("User pass = " + user.getPassword());
            System.out.println("User host = " + user.getHostmask());
            System.out.println("User greeting = " + user.getGreet());
            Flags flags = user.getFlags();
            ArrayList<Flag> myFlags = flags.getFlags();
            for (Flag flag : myFlags) {
                System.out.println("Flag name = " + flag.getName() + " : " + flag.isTruth());
            }
            System.out.println("---------------------------------------------------");
        }
    }

    private static synchronized Users loadUsers() {
        Users users = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\out\\users.xml");

            // this will create Java object - country from the XML file
            users = (Users) jaxbUnmarshaller.unmarshal(XMLfile);

            ArrayList<User> userList = users.getUsers();
            for(User user:userList) {
                System.out.println("User: " + user.getNick() + " aged " + user.getHostmask());
            }

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
        return users;
    }
}
