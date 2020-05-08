/*
 * Flags.java - defines a set of flags for a User (registered bot user)
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(namespace = "net.munki.jaxb.Users")
public class Flags {

    private ArrayList<Flag> myflags = new ArrayList<>();

    public Flags() {
        myflags.add(new Flag(Flag.FRIEND, true));
        myflags.add(new Flag(Flag.OP, false));
        myflags.add(new Flag(Flag.MASTER, false));
        myflags.add(new Flag(Flag.OWNER, false));
        myflags.add(new Flag(Flag.VOICE, false));
    }
    
    public Flags(boolean friend, boolean op, boolean master, boolean owner, boolean voice) {
        myflags.add(new Flag(Flag.FRIEND, friend));
        myflags.add(new Flag(Flag.OP, op));
        myflags.add(new Flag(Flag.MASTER, master));
        myflags.add(new Flag(Flag.OWNER, owner));
        myflags.add(new Flag(Flag.VOICE, voice));
    }

    public ArrayList<Flag> getFlags() {
        return myflags;
    }

    @XmlElement(name = "flag")
    public void setFlags(ArrayList<Flag> flags) {
        this.myflags = flags;
    }

}
