/*
 * inbound.java - handles inbound messages for JavaBot
 *
 * Copyright (C) 2001 by Torsten Born
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

package org.javabot.engine;

import java.io.*;
import java.util.Timer;
import java.util.logging.Logger;

/** Inbound message handler
 */
public class inbound implements Runnable {

    final Logger log = Logger.getLogger(this.getClass().getName());

    /** Indicates connection status
     */
    private boolean connected = false;

    final LowLevelCmdHandler llch;

    final Commands ircCommands = Commands.getInstance();

    /** Network socket to the server
     */
    private final java.net.Socket ircsocket;
    /** Output stream to the server
     */
    private DataOutputStream outbound;
    /** Input stream from server
     */
    private BufferedReader inbound;
    /** Console for output messages
     */
    private final PrintStream consoleOutput;

    String name;
    public Thread t;

    /** Creates an inbound handler thread
     * @param ircsocket Socket connection to server
     * @param consoleOutput Console for output messages
     */
    public inbound(java.net.Socket ircsocket, PrintStream consoleOutput){
        log.info("inboundRunnable() called");
        name = "inboundRunnableT";
        t = new Thread(this, name);
        this.ircsocket = ircsocket;
        try {
            outbound = new DataOutputStream(ircsocket.getOutputStream());
        } catch (IOException e) {
            // TODO Throw a JavabotException
            e.printStackTrace();
        }

        this.consoleOutput = System.out;
        llch = new LowLevelCmdHandler(outbound);
        /* Timer for timed events
         */
        Timer timer = new Timer(true);
    }

    /** Closes connections
     */    
    public void quit() {
        log.info("quit() called");
        if (outbound != null) {
            ircCommands.quit(outbound, "Boing Boing!");
            llch.removeAllChannelUsers();
            llch.killTimer();
            llch.stopBotNet();
            if (this.connected) this.connected = false;
        }
    }
    
    /** Connect to the server
     * @return Success flag
     */    
    private boolean connect() {
        log.info("connect() called");
        if (!this.connected) {
            try {
                log.info("Connected was FALSE, attempting to connect");
                inbound = new BufferedReader(
                    new InputStreamReader(
                        new DataInputStream(
                            ircsocket.getInputStream()
                        )
                    )
                );
                
                if (outbound != null) {

                    boolean identSent = false;
                    String lineToWrite;
                    for(int i=0;i<6;i++){
                        lineToWrite = inbound.readLine() + "\n";
                        consoleOutput.append(lineToWrite);

                        //IRCCommands.identify(name, nick, outbound);
                        if(lineToWrite.contains("NOTICE *")){
                            log.info("Line contained NOTICE * : skipping");
                        }
                        else if (lineToWrite.startsWith("PING :")){
                            log.info("Line started with PING :");
                            log.info("Server says " + lineToWrite);
                            String quote = lineToWrite.substring(6);
                            String all = "PONG :"+quote+"\n";
                            log.info("Sending " + all);
                            consoleOutput.append(all);
                            ircCommands.writeBytes(all, outbound);
                        }

                        if (!identSent) {
                            llch.identify();
                            identSent = true;
                        }

                    }
                    this.connected = true;
                }
            }
            catch (IOException ioe){
                log.warning("IOException caught : " + ioe.getMessage());
                this.connected = false;
            }
        }
        return this.connected;
    }
    
    /** Handle server messages
     */    
    public void run(){
        log.info("run() called");
        int timeLimit = 0;
        
        if (this.connect()) {
            try {
                log.info("Attempting to join and set modes");
                llch.join();
                llch.setModes();
                llch.limitChannel();

                String responseLine;
                String lineToWrite;
                
                while ((responseLine = inbound.readLine()) != null) {
                    lineToWrite = responseLine + "\n";
                    if (!lineToWrite.startsWith("ERROR :Closing Link:")) {
                        consoleOutput.append(lineToWrite);
                        llch.getMessage(responseLine);
                    }
                }
            }
            catch (IOException ioe){
                log.info("IOException caught : " + ioe.getMessage());
                this.connected = false;
            }
        }
    }
}
