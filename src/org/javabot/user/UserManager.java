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

import java.util.Vector;
import java.util.Iterator;

public class UserManager extends java.lang.Object {

    private final String userfile;
    private Vector users;

    public UserManager() {
        String fs = java.io.File.separator;
        String currentPath = System.getProperty("user.dir");
        this.userfile = currentPath + fs + "org" + fs + "javabot" + fs + "config" + fs + "users.xml";
        boolean debug = false;
        if (debug) System.out.println("[UM] : userfile = " + userfile);
        users = this.loadUsers();
        if (debug) {
            for (int i = 0; i < users.size(); i++) {
                User user = (User)users.get(i);
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
        boolean success = false;
        if (!this.userBotnickExists(botnick)) {
            User user = new User(botnick);
            users.add(user);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addUser(String botnick, String password) {
        boolean success = false;
        if (!this.userBotnickExists(botnick)) {
            User user = new User(botnick, password);
            users.add(user);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delUser(String botnick) {
        boolean success = false;
        if (this.userBotnickExists(botnick)) {
            User user = this.getUserByBotnick(botnick);
            users.remove(user);
            this.saveUsers();
            success = true;
        }
        return success;
    }

    public boolean addOp(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setOp(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delOp(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setOp(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delFriend(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setFriend(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delVoice(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setVoice(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delMaster(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setMaster(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean delOwner(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setOwner(false);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addVoice(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setVoice(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addMaster(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setMaster(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addFriend(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setFriend(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    public boolean addOwner(String botnick) {
        boolean success = false;
        User user = this.getUserByBotnick(botnick);
        if (user != null) {
            user.setOwner(true);
            this.saveUsers();
            success = true;
        }
        return success;
    }
    
    private User getUser(String hostmask) {
        User returnUser = null;
        User user = null;
        Iterator i = users.iterator();
        while (i.hasNext()) {
            user = (User)i.next();
            if (user.getHostmask().equals(hostmask)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
    
    public Vector getUsers() {
        return this.users;
    }
    
    public User getUserByBotnick(String botnick) {
        User returnUser = null;
        User user = null;
        Iterator i = users.iterator();
        while (i.hasNext()) {
            user = (User)i.next();
            if (user.getNick().equals(botnick)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
    
    public boolean userExists(String hostmask) {
        boolean success = false;
        User user = null;
        Iterator i = users.iterator();
        while (i.hasNext()) {
            user = (User)i.next();
            if ((user.getHostmask()).equals(hostmask)) {
                success = true;
                break;
            }
        }
        return success;
    }
        
    public boolean userBotnickExists(String botnick) {
        boolean success = false;
        User user = null;
        Iterator i = users.iterator();
        while (i.hasNext()) {
            user = (User)i.next();
            if ((user.getNick()).equals(botnick)) {
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
                if (user.isFriend()) {
                    success = true;
                }
            }
            else
            if (flag.equals(Flags.OP)) {
                if (user.isOp()) {
                    success = true;
                }
            }
            else
            if (flag.equals(Flags.MASTER)) {
                if (user.isMaster()) {
                    success = true;
                }
            }
            else
            if (flag.equals(Flags.OWNER)) {
                if (user.isOwner()) {
                    success = true;
                }
            }
            else
            if (flag.equals(Flags.VOICE)) {
                if (user.isVoice()) {
                    success = true;
                }
            }
        }
        return success;
    }
    
    public void reloadUsers() {
        this.users = this.loadUsers();
    }
    
    public void saveUsers() {
        this.storeUsers();
    }

    private synchronized Vector loadUsers() {
        Vector users = new Vector();
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(userfile);
            JSX.ObjIn usersIn = new JSX.ObjIn(in);
            users = (Vector)usersIn.readObject();
            in.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + userfile);
        }
        catch (java.io.IOException ioeLoad) {
            System.err.println("Could not load or close file : " + userfile);
        }
        catch (java.lang.ClassNotFoundException cnfe) {
            System.err.println("Could not load object from file: " + userfile);
        }
        return users;
    }

    private synchronized void storeUsers() {
        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream(userfile);
            JSX.ObjOut usersOut = new JSX.ObjOut(false, out);
            usersOut.writeObject(users);
            out.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + userfile);
        }
        catch (java.io.IOException ioeLoad) {
            System.err.println("Could not store or close file : " + userfile);
        }
    }

}
