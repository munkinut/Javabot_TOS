/*
 * JBServer.java - Javabot server
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

public class JBServer extends java.rmi.server.UnicastRemoteObject implements JBSRemoteInterface {
    
    JBotnetInterface localServer;

    public JBServer(int port) throws Exception {
        super(port);
        localServer = new LocalJBServer();
    }

    public String getTime() throws Exception {
        return localServer.getTime();
    }
    
    public boolean authenticate(String botnick, String key) throws Exception {
        return localServer.authenticate(botnick, key);
    }
    
    public void register(JBotnetInterface client) throws Exception {
        localServer.register(client);
    }
    
}
