/*
 * TestStoreUsers.java - Tests the storing of the users file
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
