/*
 * InitUsersXML.java - utility to create an initial user file
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

public class InitUsersXML {

    private java.util.Vector users;
    private final User defaultUser;
    private final String userfile;
    
    /** Creates new InitUsersXML */
    public InitUsersXML() {
        users = new java.util.Vector();
        defaultUser = new User();
        defaultUser.setNick("daBoss");
        defaultUser.setPassword("letmein");
        defaultUser.setFriend(false);
        defaultUser.setVoice(false);
        defaultUser.setOp(false);
        defaultUser.setMaster(false);
	defaultUser.setOwner(true);
        defaultUser.setHostmask("---");
        defaultUser.setGreet("Welcome new user");
        users.add(defaultUser);
        userfile = "newusers.xml";
    }
    
    public void setNick(String nick) {
        defaultUser.setNick(nick);
    }
    
    public void setPassword(String password) {
        defaultUser.setPassword(password);
    }
    
    public String getUserfile() {
        return this.userfile;
    }
    
    public void load() {
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(userfile);
            JSX.ObjIn usersIn = new JSX.ObjIn(in);
            users = (java.util.Vector)usersIn.readObject();
            in.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + userfile);
            System.exit(1);
        }
        catch (java.io.IOException ioeLoad) {
            System.err.println("Could not load or close file : " + userfile);
            System.exit(1);
        }
        catch (java.lang.ClassNotFoundException cnfe) {
            System.err.println("Could not load object from file: " + userfile);
            System.exit(1);
        }
    }
    
    public boolean store() {
        boolean success = false;
        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream(userfile);
            JSX.ObjOut usersOut = new JSX.ObjOut(false, out);
            usersOut.writeObject(users);
            out.close();
            success = true;
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + userfile);
            // System.exit(1);
            success = false;
        }
        catch (java.io.IOException ioeStore) {
            System.err.println("Could not load or close file : " + userfile);
            // System.exit(1);
            success = false;
        }
        return success;
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            InitUsersXML.usage();
        }
        else {
            String username = args[0];
            String password = args[1];
            InitUsersXML jsxt = new InitUsersXML();
            jsxt.setNick(username);
            jsxt.setPassword(password);
            if (jsxt.store()) {
                System.out.println("Default userfile " + jsxt.getUserfile() + " created");
                System.out.println("Copy or move " + jsxt.getUserfile() + " to users.xml");
                System.out.println("Read the JavaBot Users Guide for more information on how to proceed");
            }
            else {
                System.out.println("Default userfile " + jsxt.getUserfile() + " creation failed");
            }
        }
        System.exit(0);
    }
    
    public static void usage() {
        System.out.print("Usage : java InitUsersXML <username> <password>\n\n");
        System.out.print("This creates an initial userfile containing bot owner username (botnick) and ");
        System.out.print("password.  You must copy or move this file to the filename users.xml in ");
        System.out.print("order for JavaBot to use it.\n\n");
        System.out.print("For more information on how to proceed please read the user guide.\n");
    }
    
    public boolean create(String username, String password) {
        defaultUser.setNick(username);
        defaultUser.setPassword(password);
        return this.store();
    }

}
