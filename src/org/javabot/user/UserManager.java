/*
 * UserManager.java - provides user management for JavaBot
 *
 * Copyright (C) 2001 by Warren Milburn
 *
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

package org.javabot.user;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

public class UserManager {

    private final String userfile;
    private Users users;
    private boolean debug = true;

    public UserManager() {
        String fs = java.io.File.separator;
        String currentPath = System.getProperty("user.dir");
        if (debug) System.out.println("[UM] : user.dir = " + currentPath);
        this.userfile = currentPath + fs + "config" + fs + "users.xml";
        if (debug) System.out.println("[UM] : userfile = " + userfile);
        users = this.loadUsers();
        if (debug) {

            ArrayList<User> userList = users.getUsers();
            for(User user:userList) {
                System.out.println(user.getNick());
                System.out.println(user.getPassword());
                System.out.println(user.getHostmask());
                System.out.println(user.getGreet());
            }

        }
    }
    
    public boolean auth(String botnick, String hostmask, String pass) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            if ((user.getPassword().equals(pass)) && (!user.getHostmask().equals(hostmask))) {
                user.setHostmask(hostmask);
                success = true;
                this.saveUsers();
            }
        }
        return success;
    }
    
    public boolean pass(String hostmask, String oldpass, String newpass) {
        boolean success = false;
        User user = this.getUser(hostmask);
        if (user != null) {
            if (user.getPassword().equals(oldpass)) {
                user.setPassword(newpass);
                success = true;
                this.saveUsers();
            }
        }
        return success;
    }
    
    public boolean greet(String hostmask, String greet) {
        boolean success = false;
        User user = this.getUser(hostmask);
        if (user != null) {
            user.setGreet(greet);
            success = true;
            this.saveUsers();
        }
        return success;
    }
    
    public boolean addUser(String botnick) {
        //boolean success = false;
        //if (!this.userBotnickExists(botnick)) {
        //    User user = new User(botnick);
        //    users.add(user);
        //    this.saveUsers();
        //    success = true;
        //}
        return true;
    }
    
    public boolean addUser(String botnick, String password) {
        //boolean success = false;
        //if (!this.userBotnickExists(botnick)) {
        //    User user = new User(botnick, password);
        //    users.add(user);
        //    this.saveUsers();
        //    success = true;
        //}
        return true;
    }
    
    public boolean delUser(String botnick) {
        //boolean success = false;
        //if (this.userBotnickExists(botnick)) {
        //    User user = this.getUserByBotnick(botnick);
        //    users.remove(user);
        //    this.saveUsers();
        //    success = true;
        //}
        return true;
    }

    public boolean addOp(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setOp(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delOp(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setOp(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delFriend(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setFriend(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delVoice(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setVoice(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delMaster(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setMaster(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delOwner(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setOwner(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addVoice(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setVoice(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addMaster(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setMaster(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addFriend(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setFriend(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addOwner(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            //user.setOwner(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    private User getUser(String hostmask) {
        User returnUser = null;

        Iterator i = users.getUsers().iterator();
        while (i.hasNext()) {
            User user = (User)i.next();
            if (user.getHostmask().equals(hostmask)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
    
    public Users getUsers() {
        return this.users;
    }
    
    public User getUserByBotnick(String botnick) {
        User returnUser = null;
        Iterator i = users.getUsers().iterator();
        while (i.hasNext()) {
            User user = (User)i.next();
            if (user.getNick().equals(botnick)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
    
    public boolean userExists(String hostmask) {
        boolean success = false;
        User returnUser = null;

        Iterator i = users.getUsers().iterator();
        while (i.hasNext()) {
            User user = (User)i.next();
            if (user.getHostmask().equals(hostmask)) {
                success = true;
                break;
            }
        }
        return success;
    }
        
    public boolean userBotnickExists(String botnick) {
        boolean success = false;
        User returnUser = null;
        Iterator i = users.getUsers().iterator();
        while (i.hasNext()) {
            User user = (User)i.next();
            if (user.getNick().equals(botnick)) {
                success = true;
                break;
            }
        }
        return success;
    }
        
    public boolean userIsFriend(String hostmask) {
        return this.userIs(hostmask, Flags.FRIEND);
    }

    public boolean userIsOp(String hostmask) {
        return this.userIs(hostmask, Flags.OP);
    }
    
    public boolean userIsMaster(String hostmask) {
        return this.userIs(hostmask, Flags.MASTER);
    }
    
    public boolean userIsOwner(String hostmask) {
        return this.userIs(hostmask, Flags.OWNER);
    }
    
    public boolean userIsVoice(String hostmask) {
        return this.userIs(hostmask, Flags.VOICE);
    }
    
    public String getGreet(String hostmask) {
        User user = this.getUser(hostmask);
        if (user != null) {
            return user.getGreet();
        }
        else {
            return null;
        }
    }
    
    private boolean userIs(String hostmask, String flag) {
        boolean success = false;
        User user = this.getUser(hostmask);
        if (user != null) {
            if (flag.equals(Flags.FRIEND)) {
                //if (user.isFriend()) {
                //    success = true;
                //}
            }
            else
            if (flag.equals(Flags.OP)) {
                //if (user.isOp()) {
                //    success = true;
                //}
            }
            else
            if (flag.equals(Flags.MASTER)) {
                //if (user.isMaster()) {
                //    success = true;
                //}
            }
            else
            if (flag.equals(Flags.OWNER)) {
                //if (user.isOwner()) {
                //    success = true;
                //}
            }
            else
            if (flag.equals(Flags.VOICE)) {
                //if (user.isVoice()) {
                //    success = true;
                //}
            }
        }
        return success;
    }
    
    public void reloadUsers() {
        this.users = this.loadUsers();
    }

    public void saveUsers() {
        this.storeUsers(users);
    }

    private synchronized Users loadUsers() {
        Users users = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\JAXB_Test\\out\\users.xml");

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


    private synchronized void storeUsers(Users users) {

        try {

            // create JAXB context and initializing Marshal
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\JAXB_Test\\out\\users.xml");

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
