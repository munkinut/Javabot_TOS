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

public class Flags extends java.lang.Object {

    public static final String FRIEND = "f";
    public static final String OP = "o";
    public static final String MASTER = "m";
    public static final String OWNER = "n";
    public static final String VOICE = "v";
    
    private final java.util.Hashtable flags;

    public Flags() {
        flags = new java.util.Hashtable();
        flags.put(Flags.FRIEND, Boolean.FALSE);
        flags.put(Flags.OP, Boolean.FALSE);
        flags.put(Flags.MASTER, Boolean.FALSE);
        flags.put(Flags.OWNER, Boolean.FALSE);
        flags.put(Flags.VOICE, Boolean.FALSE);
    }
    
    public Flags(boolean friend, boolean op, boolean master, boolean owner, boolean voice) {
        flags = new java.util.Hashtable();
        flags.put(Flags.FRIEND, friend);
        flags.put(Flags.OP, op);
        flags.put(Flags.MASTER, master);
        flags.put(Flags.OWNER, owner);
        flags.put(Flags.VOICE, voice);
    }
    
    public boolean get(String flag) {
        if (flags.containsKey(flag)) {
            return (Boolean) flags.get(flag);
        }
        else return false;
    }
    
    public void set(String flag, boolean state) {
        if (flags.containsKey(flag)) {
            flags.put(flag, state);
        }
    }
}
