/*
 * JBRMIClient.java - client to the botserver
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

package org.javabot.client;

import java.rmi.*;
import java.util.logging.Logger;

import org.javabot.server.JBotnetInterface;

/** JavaBot RMI client.
 */
public class JBRMIClient implements JBotnetInterface {

    Logger log = Logger.getLogger(this.getClass().getName());

    org.javabot.server.JBSRemoteInterface remote;
    
    /** Creates a new RMI client.
     * @param host Hostname of server.
     * @param port Port of server.
     * @throws Exception Any connection related exception.
     */
    @SuppressWarnings("deprecation")
    public JBRMIClient(String host, int port) throws Exception {
        String remoteURL = "rmi://" + host + ":" + port + "/JBServer";
        remote = (org.javabot.server.JBSRemoteInterface)Naming.lookup(remoteURL);
        java.rmi.server.UnicastRemoteObject.exportObject(this);
        this.register(this);
    }

    /** Get the remote host time.
     * @throws Exception Any exception related to getting the server time.
     * @return String representation of the remote server time.
     */    
    public String getTime() throws Exception {
        return remote.getTime();
    }
    
    /** Authenticate the bot with the server.
     * @param botnick Nickname of the bot.
     * @param key Key required to authenticate the bot.
     * @throws Exception Any authentication related exception.
     * @return Authentication status.
     */    
    public boolean authenticate(String botnick, String key) throws Exception {
        return remote.authenticate(botnick, key);
    }
    
    /** Register the bot with the server.
     * @param client The client to register.
     * @throws Exception Any registration related exception.
     */    
    public void register(JBotnetInterface client) throws Exception {
        remote.register(client);
    }
    
}
