/*
 * JavaBotActivator.java - provides activation / deactivation for JavaBot
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

package org.javabot.gui;

public class JavaBotActivator extends java.lang.Object {
    
    private final boolean debug = false;

    private boolean connected;
    private final javax.swing.JTextArea consoleOutput;
    private java.net.Socket ircsocket;
    private final org.javabot.configuration.PropertyManager propertyManager;
    private org.javabot.engine.inbound in;

    /** Creates new JavaBotActivator */
    public JavaBotActivator(javax.swing.JTextArea consoleOutput) {
        this.consoleOutput = consoleOutput;
        this.connected = false;
        this.propertyManager = org.javabot.configuration.PropertyManager.getInstance();
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public boolean connect() {
        try {
            if (!connected) {
                String server = propertyManager.getServer();
                int port = propertyManager.getPort();
                ircsocket = new java.net.Socket(server, port);
                in = new org.javabot.engine.inbound(ircsocket, consoleOutput);
                in.start();
                connected = true;
            }
        }
        catch (java.net.UnknownHostException uhe){
            System.err.println("UnknownHostException: " + uhe);
            connected = false;
            // System.exit(1);
        }
        catch (java.io.IOException ioe){
            System.err.println("IOException: " + ioe);
            connected = false;
            // System.exit(1);
        }
        return connected;
    }
    
    public boolean disconnect() {
        if (connected && (in != null)) {
            in.quit();
            try {
                if (ircsocket != null) ircsocket.close();
                String status = "Disconnected\n";
                consoleOutput.append(status);
                //consoleOutput.setCaretPosition(consoleOutput.getCaretPosition() + status.length());
            }
            catch (java.io.IOException ioe) {
                String status = "Failed to disconnect successfully\n";
                consoleOutput.append(status);
                //consoleOutput.setCaretPosition(consoleOutput.getCaretPosition() + status.length());
            }
            connected = false;
        }
        return !connected;
    }
    
    public boolean exit() {
        if (this.disconnect()) {
            // tell us we disconnected ok
        }
        else {
            // tell us we did not disconnect ok
        }
        return connected;
    }

}
