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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.logging.Logger;

@XmlRootElement(namespace = "net.munki.jaxb.Users")
@XmlType(propOrder = { "nick", "password", "hostmask", "greet", "flags"})
public class User {

    Logger log = Logger.getLogger(this.getClass().getName());

    private static final String DEFAULT_NICK = "munkinut";
    private static final String DEFAULT_PASSWORD = "bibble";
    private static final String DEFAULT_HOSTMASK = "bibble";
    private static final String DEFAULT_GREET = "welcome";
    
    private String nick;
    private String password;
    private String hostmask;
    private String greet;
    private Flags flags;

    public User() {
        this.nick = User.DEFAULT_NICK;
        this.password = User.DEFAULT_PASSWORD;
        this.hostmask = User.DEFAULT_HOSTMASK;
        this.greet = User.DEFAULT_GREET;
        flags = new Flags(false, false, false, false, false);
    }

    public User(String nick) {
        this.nick = nick;
        password = User.DEFAULT_PASSWORD;
        hostmask = User.DEFAULT_HOSTMASK;
        greet = User.DEFAULT_GREET;
        flags = new Flags(false, false, false, false, false);
    }
    
    public User(String nick, String password) {
        this.nick = nick;
        this.password = password;
        hostmask = User.DEFAULT_HOSTMASK;
        greet = User.DEFAULT_GREET;
        flags = new Flags(false, false, false, false, false);
    }
    
    public User(String nick, String password, String hostmask, String greet, Flags flags) {
        this.nick = nick;
        this.password = password;
        this.hostmask = hostmask;
        this.greet = greet;
        this.flags = flags;
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
    
    public String getGreet() {
        return this.greet;
    }

    public Flags getFlags() { return this.flags; }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setHostmask(String hostmask) {
        this.hostmask = hostmask;
    }
    
    public void setGreet(String greet) {
        this.greet = greet;
    }

    public void setFlags(Flags flags) {this.flags = flags; }

    //public boolean get(String flag) {
    //    if (flags.getFlags().containsKey(flag)) {
    //        return flags.getFlags().get(flag);
    //    }
    //    else return false;
    //}

    //public void set(String flag, boolean state) {
    //    if (flags.getFlags().containsKey(flag)) {
    //        flags.getFlags().put(flag, state);
    //    }
    //}

    //public boolean isFriend() {
    //    return this.get(Flags.FRIEND);
    //}

    //public boolean isOp() {
    //    return this.get(Flags.OP);
    //}

    //public boolean isMaster() {
    //    return this.get(Flags.MASTER);
    //}

    //public boolean isOwner() {
    //    return this.get(Flags.OWNER);
    //}

    //public boolean isVoice() {
    //    return this.get(Flags.VOICE);
    //}
    
    //public void setFriend(boolean state) {
    //    this.set(Flags.FRIEND, state);
    //}

    //public void setOp(boolean state) {
    //    this.set(Flags.OP, state);
    //}

    //public void setMaster(boolean state) {
    //    this.set(Flags.MASTER, state);
    //}

    //public void setOwner(boolean state) {
    //    this.set(Flags.OWNER, state);
    //}

    //public void setVoice(boolean state) {
    //    this.set(Flags.VOICE, state);
    //}
    
    public void clearFlags() {
        flags = new Flags(false, false, false, false, false);
    }
    
    public String toString() {
        return this.getNick();
    }

}
