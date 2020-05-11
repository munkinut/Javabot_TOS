/*
 * ScriptResource.java - provides a packaged set of resources and commands
 * for use by JavaBot's BeanShell scripts
 *
 * Copyright (C) 2002 by Warren Milburn
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

package org.javabot.script;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class ScriptResource {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private final DataOutputStream outbound;
    private final String channel;
    private final String nick;
    private final String hostmask;
    private final ArrayList<String> params;

    /** Creates new ScriptResource */
    public ScriptResource(
        DataOutputStream outbound,
        String channel,
        String nick,
        String hostmask,
        ArrayList<String> params) {

        log.info("ScriptResource() called");
            this.outbound = outbound;
            this.channel = channel;
            this.nick = nick;
            this.hostmask = hostmask;
            this.params = params;
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public String getNick() {
        return this.nick;
    }
    
    public String getHostmask() {
        return this.hostmask;
    }
    
    public ArrayList getParams() {
        return this.params;
    }
    
    public void msgNick(String message) {
        try {
            this.outbound.writeBytes("PRIVMSG " + this.nick +" :" + message + "\r\n");
        }
        catch (IOException ioe){
            System.out.println("IOException: " + ioe);
        }
    }

    public void msgChannel(String message) {
        
        try {
            this.outbound.writeBytes("PRIVMSG " + this.channel + " :" + message + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }

}
