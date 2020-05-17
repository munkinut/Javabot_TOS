/*
 * TestLoadUsers.java - Tests loading of the users file.
 *
 * Copyright (C) 2020 by Warren Milburn
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.javabot.play;

import org.javabot.user.Flag;
import org.javabot.user.Flags;
import org.javabot.user.User;
import org.javabot.user.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TestLoadUsers {

    Logger log = Logger.getLogger(this.getClass().getName());

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
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\users.xml");

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
