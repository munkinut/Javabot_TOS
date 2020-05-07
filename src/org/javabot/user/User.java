/*
 * User.java - represents a registered bot user
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

public class User {
    
    private static final String DEFAULT_PASSWORD = "bibble";
    
    private String nick;
    private String password;
    private String hostmask;
    private Flags flags;
    private String greet;

    public User() {
        nick = "";
        password = "";
        flags = new Flags(false, false, false, false, false);
        hostmask = "";
        greet = "";
    }
    
    public User(String nick) {
        this.nick = nick;
        password = User.DEFAULT_PASSWORD;
        hostmask = "";
        flags = new Flags(false, false, false, false, false);
        greet = "Wooo hooo ... welcome " + nick;
    }
    
    public User(String nick, String password) {
        this.nick = nick;
        this.password = password;
        hostmask = "";
        flags = new Flags(false, false, false, false, false);
        greet = "Wooo hooo ... welcome " + nick;
    }
    
    public User(String nick, String password, String hostmask, Flags flags, String greet) {
        this.nick = nick;
        this.password = password;
        this.hostmask = hostmask;
        this.flags = flags;
        this.greet = greet;
    }
    
    public String getNick() {
        return this.nick;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getHostmask() {
        return this.hostmask;
    }
    
    public Flags getFlags() {
        return this.flags;
    }
    
    public String getGreet() {
        return this.greet;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setHostmask(String hostmask) {
        this.hostmask = hostmask;
    }
    
    public void setFlags(Flags flags) {
        this.flags = flags;
    }
    
    public void setGreet(String greet) {
        this.greet = greet;
    }
    
    public boolean isFriend() {
        return flags.get(Flags.FRIEND);
    }

    public boolean isOp() {
        return flags.get(Flags.OP);
    }

    public boolean isMaster() {
        return flags.get(Flags.MASTER);
    }

    public boolean isOwner() {
        return flags.get(Flags.OWNER);
    }

    public boolean isVoice() {
        return flags.get(Flags.VOICE);
    }
    
    public void setFriend(boolean state) {
        flags.set(Flags.FRIEND, state);
    }

    public void setOp(boolean state) {
        flags.set(Flags.OP, state);
    }

    public void setMaster(boolean state) {
        flags.set(Flags.MASTER, state);
    }

    public void setOwner(boolean state) {
        flags.set(Flags.OWNER, state);
    }

    public void setVoice(boolean state) {
        flags.set(Flags.VOICE, state);
    }
    
    public void clearFlags() {
        flags = new Flags(false, false, false, false, false);
    }
    
    public String toString() {
        return this.getNick();
    }

}
