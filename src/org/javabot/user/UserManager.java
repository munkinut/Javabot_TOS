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

import org.javabot.configuration.PropertyManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class UserManager {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private Users users;
    private final String usersPath;

    public UserManager() {
        //String fs = java.io.File.separator;
        Properties properties = PropertyManager.getInstance().getProperties();
        usersPath = properties.getProperty("Users_Location");
        log.info("[UM] : userfile = " + usersPath);
        users = this.loadUsers();
        ArrayList<User> userList = users.getUsers();
        for(User user:userList) {
            log.info(user.getNick());
            log.info(user.getPassword());
            log.info(user.getHostmask());
            log.info(user.getGreet());
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

        for (User user : users.getUsers()) {
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
        for (User user : users.getUsers()) {
            if (user.getNick().equals(botnick)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
    
    public boolean userExists(String hostmask) {
        boolean success = false;
        User returnUser = null;

        for (User user : users.getUsers()) {
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
        for (User user : users.getUsers()) {
            if (user.getNick().equals(botnick)) {
                success = true;
                break;
            }
        }
        return success;
    }
        
    public boolean userIsFriend(String hostmask) {
        return this.userIs(hostmask, Flag.FRIEND);
    }

    public boolean userIsOp(String hostmask) {
        log.info("userIsOp() called with hostmask = " + hostmask);
        return this.userIs(hostmask, Flag.OP);
    }
    
    public boolean userIsMaster(String hostmask) {
        return this.userIs(hostmask, Flag.MASTER);
    }
    
    public boolean userIsOwner(String hostmask) {
        return this.userIs(hostmask, Flag.OWNER);
    }
    
    public boolean userIsVoice(String hostmask) {
        log.info("userIsVoice() called with hostmask = " + hostmask);
        return this.userIs(hostmask, Flag.VOICE);
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

    private boolean testFlag(ArrayList<Flag> flags, String flagStr) {
        log.info("testFlag() called with flagStr = " + flagStr);
        boolean success = false;
        for(Flag flag : flags) {
            String flagName = flag.getName();
            log.info("flagName was " + flagName);
            boolean flagStrTest = flagName.equals(flagStr);
            log.info("flagStrTest comes back " + flagStrTest);
            boolean flagTest = flag.isTruth();
            log.info("flagTest comes back " + flagTest);
            if (flagStrTest && flagTest) {
                log.info("Flag tests true");
                success = true;
            }
            else {
                log.info("Flag tests false");
                success = false;
            }
            if (success) break;

        }
        return success;

    }
    
    private boolean userIs(String hostmask, String flagStr) {
        log.info("userIs() called with hostmask = " + hostmask + " flagStr = " + flagStr);
        boolean success = false;
        User user = this.getUser(hostmask);
        if (user != null) {
            log.info("user came back not null");
            ArrayList<Flag> flags = user.getFlags().getFlags();
            switch (flagStr) {
                case Flag.FRIEND:
                    //if (user.isFriend()) {
                    //    success = true;
                    //}
                    break;
                case Flag.OP:
                    log.info("case Flag.OP entered");
                    success = testFlag(flags,flagStr);
                    log.info("success is + " + success);
                    break;
                case Flag.MASTER:
                    //if (user.isMaster()) {
                    //    success = true;
                    //}
                    break;
                case Flag.OWNER:
                    //if (user.isOwner()) {
                    //    success = true;
                    //}
                    break;
                case Flag.VOICE:
                    log.info("case Flag.VOICE entered");
                    success = testFlag(flags,flagStr);
                    log.info("success is + " + success);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + flagStr);
            }
        }
        else {
            log.info("user came back null");
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
            File XMLfile = new File(usersPath);

            // this will create Java object - Users from the XML file
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
            File XMLfile = new File(usersPath);

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
