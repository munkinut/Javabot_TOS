/*
 * JBClientUser.java - uses JBRMIClient to execute remote methods
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

import java.util.logging.Logger;

/** Test client for JavaBot RMI Botnet.
 */
public class JBClientUser {

    Logger log = Logger.getLogger(this.getClass().getName());

    /** Creates a new JBClientUser.
     */    
    public JBClientUser() {
    }

    /** Creates an RMI client connection and registers itself with the server.
     * @param args Command line parameters.
     */    
    public static void main (String[] args) {
        try {
            org.javabot.server.JBotnetInterface client = new JBRMIClient("localhost", 1099);
            String time = client.getTime();
            System.out.println("Remote system time : " + time);
            System.exit(0);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

}
