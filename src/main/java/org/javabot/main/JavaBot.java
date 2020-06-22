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

package org.javabot.main;

import org.javabot.configuration.PropertyManager;
import org.javabot.engine.inbound;

import java.io.PrintStream;
import java.util.logging.Logger;

class JavaBot extends Thread  {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private final boolean debug = false;

    private boolean connected;
    private final PrintStream consoleOutput;
    private java.net.Socket ircsocket;
    private final PropertyManager propertyManager;
    //private org.javabot.engine.inbound in;
    private inbound in;

    public static void main(String[] args){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        // Create a thread, initialize it but don't start it
        // Add it as a shutdown hook to the VM via Runtime


        //jmenue.main(new String [0]);
        JavaBot jb = new JavaBot(System.out);
        jb.connect();
    }

    /** Creates new JavaBotActivator */
    public JavaBot(PrintStream consoleOutput) {
        log.info("JavaBotActivator() called");
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(this);
        this.consoleOutput = consoleOutput;
        this.connected = false;
        this.propertyManager = org.javabot.configuration.PropertyManager.getInstance();
    }

    @Override
    public void run() {
        log.info("Shutdown hook called");
        this.disconnect();
    }

    public boolean isConnected() {
        return connected;
    }
    
    public void connect() {
        log.info("connect() called");
        try {
            if (!connected) {
                String server = propertyManager.getServer();
                int port = propertyManager.getPort();
                ircsocket = new java.net.Socket(server, port);
                in = new inbound(ircsocket, consoleOutput);
                in.t.start();
                connected = true;
            }
        }
        catch (java.net.UnknownHostException uhe){
            log.warning("UnknownHostException: " + uhe);
            connected = false;
            // System.exit(1);
        }
        catch (java.io.IOException ioe){
            log.warning("IOException: " + ioe);
            connected = false;
        }
    }
    
    public boolean disconnect() {
        log.info("disconnect() called");
        if (connected && (in != null)) {
            in.quit();
            try {
                log.info("Thread alive is " + in.t.isAlive() + " ... calling join()");
                in.t.join(5000);
                log.info("Thread alive is " + in.t.isAlive());
            } catch (InterruptedException e) {
                log.warning("InterruptedException: " + e);
            }
            try {
                if (ircsocket != null) ircsocket.close();
                String status = "Disconnected\n";
                consoleOutput.append(status);
            }
            catch (java.io.IOException ioe) {
                String status = "Failed to disconnect successfully\n";
                consoleOutput.append(status);
            }

          connected = false;
        }
        return connected;
    }
    
}
