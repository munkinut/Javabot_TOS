/*
 * LocalJBServer.java - Local version of the botserver
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

package org.javabot.server;

public class LocalJBServer implements JBotnetInterface {
    
    private java.util.Vector clients;

    public LocalJBServer() {
        clients = new java.util.Vector();
    }

    public String getTime() throws Exception {
        java.util.Date d = new java.util.Date();
        return d.toString();
    }
    
    public boolean authenticate(String botnick, String key) throws Exception {
        return true;
    }
    
    public void register(JBotnetInterface client) throws Exception {
        clients.add(client);
        System.out.println("Client registered");
    }
    
}
