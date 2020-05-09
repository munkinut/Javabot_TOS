/*
 * JBServerRunner.java - Server registry for JBServer
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

import java.rmi.*;
import java.rmi.registry.*;
import java.util.logging.Logger;

public class JBServerRunner {

    Logger log = Logger.getLogger(this.getClass().getName());

    public JBServerRunner() {
    }
    
    public static void main (String [] args) {
        try {
            int registryPort = 1099;
            int objectPort = 2031;
            Registry reg = LocateRegistry.createRegistry(registryPort);
            JBServer server = new JBServer(objectPort);
            Naming.rebind("//:" + registryPort + "/JBServer", server);
            System.out.println("** JBServer registry running on port : " + registryPort);
            System.out.println("** JBServer object running on port : " + objectPort);
        }
        catch (Exception e) {
            System.err.println("RMI Server Error : " + e.getMessage());
        }
    }

}
