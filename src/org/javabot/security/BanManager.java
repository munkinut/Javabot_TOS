/*
 * BanManager.java - provides banlist management for JavaBot
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

package org.javabot.security;

import java.util.Vector;
import gnu.regexp.*;

public class BanManager extends java.lang.Object {
    
    private final boolean debug = false;
    
    private final String banfile;
    private Vector bans;

    public BanManager() {
        String fs = java.io.File.separator;
        String currentPath = System.getProperty("user.dir");
        this.banfile = currentPath + fs + "org" + fs + "javabot" + fs + "config" + fs + "bans.xml";
        if (debug) System.out.println("[BM] : banfile = " + banfile);
        bans = this.loadBans();
        if (debug) {
            for (int i = 0; i < bans.size(); i++) {
                String banmask = (String)bans.get(i);
                System.out.println(banmask);
            }
        }
    }
    
    public boolean addBan(String banmask) {
        boolean success = false;
        if (!this.bans.contains(banmask)) {
            bans.add(banmask);
            this.saveBans();
            success = true;
        }
        return success;
    }
    
    public boolean delBan(String banmask) {
        boolean success = false;
        if (this.bans.contains(banmask)) {
            bans.remove(banmask);
            this.saveBans();
            success = true;
        }
        return success;
    }
    
    public boolean matches(String hostmask) {
        boolean matches = false;
        String banmask;
        RE exp;
        for (int i = 0; i < bans.size(); i++) {
            banmask = (String)bans.elementAt(i);
            try {
                exp = new RE(this.regThis(banmask));
                if (exp.isMatch(hostmask)) {
                    matches = true;
                    break;
                }
            }
            catch (gnu.regexp.REException grr) {
            }
        }
        return matches;
    }

    private String regThis(String hostmask) {
        try {
            RE nickReg = new RE("\\*");
            hostmask = nickReg.substituteAll(hostmask, "\\S*");
        }
        catch (gnu.regexp.REException reex) {
        }
        return hostmask;
    }

    public Vector getBans() {
        return this.bans;
    }
    
    public boolean banExists(String banmask) {
        boolean success = false;
        if (bans.contains(banmask)) success = true;
        return success;
    }
        
    public void reloadBans() {
        this.bans = this.loadBans();
    }
    
    public void saveBans() {
        this.storeBans();
    }

    private synchronized Vector loadBans() {
        Vector bans = new Vector();
        try {
            java.io.FileInputStream in = new java.io.FileInputStream(banfile);
            JSX.ObjIn usersIn = new JSX.ObjIn(in);
            bans = (Vector)usersIn.readObject();
            in.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + banfile);
        }
        catch (java.io.IOException ioeLoad) {
            System.err.println("Could not load or close file : " + banfile);
        }
        catch (java.lang.ClassNotFoundException cnfe) {
            System.err.println("Could not load object from file: " + banfile);
        }
        return bans;
    }

    private synchronized void storeBans() {
        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream(banfile);
            JSX.ObjOut usersOut = new JSX.ObjOut(false, out);
            usersOut.writeObject(bans);
            out.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            System.err.println("Could not find file : " + banfile);
        }
        catch (java.io.IOException ioeLoad) {
            System.err.println("Could not store or close file : " + banfile);
        }
    }

}
