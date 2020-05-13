/*
 * PropertyManager.java - provides property file management for JavaBot
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

package org.javabot.configuration;

import java.util.logging.Logger;

/** Manages the properties object and file
 */
public class PropertyManager {

    final Logger log = Logger.getLogger(this.getClass().getName());

    /** Busy flag to aid in thread synchronization
     */    
    private boolean busy;
    
    /** Properties object for the bot
     */    
    private final java.util.Properties properties;
    /** The singleton instance
     */    
    private static PropertyManager propertyManager;
    
    /** Creates new PropertyManager */
    private PropertyManager() {
        log.info("PropertyManager() called");
        this.busy = false;
        this.properties = new java.util.Properties();
        this.readProperties();
    }
    
    /** Provides the singleton instance
     * @return singleton instance of PropertyManager
     */    
    public static synchronized PropertyManager getInstance() {
        if (propertyManager == null) propertyManager = new PropertyManager();
        return propertyManager;
    }
    
    /** Provides the properties object
     * @return the properties object
     */    
    public synchronized java.util.Properties getProperties() {
        return this.properties;
    }
    
    /** Provides the server name
     * @return Server name
     */    
    public synchronized String getServer() {
        log.info("getServer() called");
        while (busy) {
            try {
                log.info("waiting");
                wait();
            }
            catch (java.lang.InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String server = properties.getProperty("Server");
        busy = false;
        notifyAll();
        return server;
    }
    
    /** Sets the server name
     * @param server Server name
     */    
    public synchronized void setServer(String server) {
        log.info("setServer() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Server", server);
        busy = false;
        notifyAll();
    }
    
    /** Provides the bots name
     * @return Name of the bot
     */    
    public synchronized String getName() {
        log.info("getName() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String name = properties.getProperty("Name");
        busy = false;
        notifyAll();
        return name;
    }
    
    /** Sets the bots name
     * @param name Name of the bot
     */    
    public synchronized void setName(String name) {
        log.info("setName() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Name", name);
        busy = false;
        notifyAll();
    }
    
    /** Provides the bots nickname
     * @return Bots nickname
     */    
    public synchronized String getNickname() {
        log.info("getNickname() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String nickname = properties.getProperty("Nickname");
        busy = false;
        notifyAll();
        return nickname;
    }
    
    /** Sets the bots nickname
     * @param nickname Nickname to set
     */    
    public synchronized void setNickname(String nickname) {
        log.info("setNickname() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Nickname", nickname);
        busy = false;
        notifyAll();
    }
    
    /** Provides the channel the bot will sit on
     * @return Channel name
     */    
    public synchronized String getChannel() {
        log.info("getChannel() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String channel = properties.getProperty("Channel");
        busy = false;
        notifyAll();
        return channel;
    }
    
    /** Sets the channel the bot will sit on
     * @param channel Bots channel
     */    
    public synchronized void setChannel(String channel) {
        log.info("setChannel() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Channel", channel);
        busy = false;
        notifyAll();
    }
    
    /** Provides the intitial channel modes
     * @return Initial channel modes
     */    
    public synchronized String getChannelModes() {
        log.info("getChannel() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String channelmodes = properties.getProperty("ChannelModes");
        busy = false;
        notifyAll();
        return channelmodes;
    }
    
    /** Sets the intial channel modes
     * @param channelmodes Initial channel modes
     */    
    public synchronized void setChannelModes(String channelmodes) {
        log.info("getChannelModes() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("ChannelModes", channelmodes);
        busy = false;
        notifyAll();
    }
    
    /** Provides the channel join ratio
     * @return Channel ratio
     */    
    public synchronized String getJoinRatio() {
        log.info("getJoinRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String joinratio = properties.getProperty("Join");
        busy = false;
        notifyAll();
        return joinratio;
    }
    
    /** Set the channel join ratio
     * @param joinratio Join ratio for the channel
     */    
    public synchronized void setJoinRatio(String joinratio) {
        log.info("getJoinRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Join", joinratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides the private message ratio
     * @return Private message ratio
     */    
    public synchronized String getPrivmsgRatio() {
        log.info("getPrivmsgRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String privmsgratio = properties.getProperty("Privmsg");
        busy = false;
        notifyAll();
        return privmsgratio;
    }
    
    /** Sets the private message ratio
     * @param privmsgratio Private message ratio
     */    
    public synchronized void setPrivmsgRatio(String privmsgratio) {
        log.info("setPrivmsgRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Privmsg", privmsgratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides the channel message ratio
     * @return Channel message ratio
     */    
    public synchronized String getChanmsgRatio() {
        log.info("getChanmsgRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String chanmsgratio = properties.getProperty("Chanmsg");
        busy = false;
        notifyAll();
        return chanmsgratio;
    }
    
    /** Set channel message ratio
     * @param chanmsgratio Channel message ratio
     */    
    public synchronized void setChanmsgRatio(String chanmsgratio) {
        log.info("setChanmsgRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Chanmsg", chanmsgratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides colour flood ratio
     * @return Colour flood ratio
     */    
    public synchronized String getColourRatio() {
        log.info("getColourRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String colourratio = properties.getProperty("Colour");
        busy = false;
        notifyAll();
        return colourratio;
    }
    
    /** Set colour flood ratio
     * @param colourratio Colour flood ratio
     */    
    public synchronized void setColourRatio(String colourratio) {
        log.info("setColourRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Colour", colourratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides the CTCP flood ratio
     * @return CTCP flood ratio
     */    
    public synchronized String getCtcpRatio() {
        log.info("getCtcpRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String ctcpratio = properties.getProperty("Ctcp");
        busy = false;
        notifyAll();
        return ctcpratio;
    }
    
    /** Set the CTCP flood ratio
     * @param ctcpratio CTCP flood ratio
     */    
    public synchronized void setCtcpRatio(String ctcpratio) {
        log.info("setCtcpRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Ctcp", ctcpratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides the DCC flood ratio
     * @return DCC flood ratio
     */    
    public synchronized String getDccRatio() {
        log.info("getDccRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        String dccratio = properties.getProperty("Dcc");
        busy = false;
        notifyAll();
        return dccratio;
    }
    
    /** Set the DCC flood ratio
     * @param dccratio DCC flood ratio
     */    
    public synchronized void setDccRatio(String dccratio) {
        log.info("setDccRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Dcc", dccratio);
        busy = false;
        notifyAll();
    }
    
    /** Provides the port number to connect to
     * @return Port number
     */    
    public synchronized int getPort() {
        log.info("getPort() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        int port = Integer.parseInt(properties.getProperty("Port"));
        busy = false;
        notifyAll();
        return port;
    }
    
    /** Sets the port number to connect to
     * @param port Port number to connect to
     */    
    public synchronized void setPort(int port) {
        log.info("setPort() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Port", Integer.toString(port));
        busy = false;
        notifyAll();
    }
    
    /** Provides the autovoice flag
     * @return Autovoice flag
     */    
    public synchronized boolean getAutovoice() {
        log.info("getAutovoice() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean autovoice = Boolean.parseBoolean(properties.getProperty("Autovoice"));
        busy = false;
        notifyAll();
        return autovoice;
    }
    
    /** Sets the autovoice flag
     * @param autovoice Autovoice flag
     */    
    public synchronized void setAutovoice(boolean autovoice) {
        log.info("setAutovoice() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Autovoice", Boolean.toString(autovoice));
        busy = false;
        notifyAll();
    }
    
    /** Provides the autogreet flag
     * @return Autogreet flag
     */    
    public synchronized boolean getAutogreet() {
        log.info("getAutogreet() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean autogreet = Boolean.parseBoolean(properties.getProperty("Autogreet"));
        busy = false;
        notifyAll();
        return autogreet;
    }
    
    /** Sets the autogreet flag
     * @param autogreet Autogreet flag
     */    
    public synchronized void setAutogreet(boolean autogreet) {
        log.info("setAutogreet() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Autogreet", Boolean.toString(autogreet));
        busy = false;
        notifyAll();
    }
    
    /** Provides the flood protection flag
     * @return Flood protection flag
     */    
    public synchronized boolean getFloodProtection() {
        log.info("getFloodProtection() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean floodprotection = Boolean.parseBoolean(properties.getProperty("Floodprotection"));
        busy = false;
        notifyAll();
        return floodprotection;
    }
    
    /** Sets the flood protection flag
     * @param floodprotection Flood protection flag
     */    
    public synchronized void setFloodProtection(boolean floodprotection) {
        log.info("setFloodProtection() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Floodprotection", Boolean.toString(floodprotection));
        busy = false;
        notifyAll();
    }
    
    /** Provides the opme flag
     * @return Opme flag
     */    
    public synchronized boolean getOpme() {
        log.info("getOpme() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean opme = Boolean.parseBoolean(properties.getProperty("Opme"));
        busy = false;
        notifyAll();
        return opme;
    }
    
    /** Sets the opme flag
     * @param opme Opme flag
     */    
    public synchronized void setOpme(boolean opme) {
        log.info("getOpme() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("Opme", Boolean.toString(opme));
        busy = false;
        notifyAll();
    }
    
    /** Provides the cycle for ops flag
     * @return Cycle for ops flag
     */    
    public synchronized boolean getCycleForOps() {
        log.info("getCycleForOps() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean cycleforops = Boolean.parseBoolean(properties.getProperty("CycleForOps"));
        busy = false;
        notifyAll();
        return cycleforops;
    }
    
    /** Set cycle for ops flag
     * @param cycleforops Cycle for ops flag
     */    
    public synchronized void setCycleForOps(boolean cycleforops) {
        log.info("setCycleForOps() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("CycleForOps", Boolean.toString(cycleforops));
        busy = false;
        notifyAll();
    }
    
    /** Provides dynamic limit flag
     * @return Dynamic limit flag
     */    
    public synchronized boolean getDynamicLimit() {
        log.info("getDynamicLimit() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        boolean dynamiclimit = Boolean.parseBoolean(properties.getProperty("DynamicLimit"));
        busy = false;
        notifyAll();
        return dynamiclimit;
    }
    
    /** Sets dynamic limit flag
     * @param dynamiclimit dynamic limit flag
     */    
    public synchronized void setDynamicLimit(boolean dynamiclimit) {
        log.info("setDynamicLimit() called");
        while (busy) {
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        busy = true;
        properties.setProperty("DynamicLimit", Boolean.toString(dynamiclimit));
        busy = false;
        notifyAll();
    }
    
    /** Writes properties to a file
     */    
    public synchronized void writeProperties() {
        log.info("writeProperties() called");
        while (busy) {
            log.info("writeProperties() - BUSY ... waiting");
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        log.info("writeProperties() - NOT BUSY ... writing");
        busy = true;
        try {
            String fs = java.io.File.separator;
            String currentPath = System.getProperty("user.dir");
            log.info("USER.DIR:" + currentPath);
            String javabotProperties = currentPath + fs + "config" + fs + "javabot.properties";
            log.info("javabotProperties = " + javabotProperties);
            java.io.FileOutputStream out = new java.io.FileOutputStream(javabotProperties);
            properties.store(out, null);
            out.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            this.failProperties("Could not find Properties file : javabot.properties");
            // System.exit(1);
        }
        catch (java.io.IOException ioeStore) {
            this.failProperties("Could not store or close Properties file : javabot.properties");
            // System.exit(1);
        }
        busy = false;
        notifyAll();
    }
    
    /** Read properties from a file
     * @return Success flag
     */    
    public synchronized boolean readProperties() {
        log.info("readProperties() called");
        boolean success = true;
        String server, name, nickname, channel, channelModes, join;
        int port;
        boolean autovoice,flood,opme,cycleForOps,dynamicLimit;
        while (busy) {
            log.info("readProperties() - BUSY ... waiting");
            try {
                wait();
            }
            catch (java.lang.InterruptedException ignored) {
            }
        }
        log.info("readProperties() - NOT BUSY ... reading");
        busy = true;
        try {
            String javabotProperties = System.getProperty("javabot.properties.file");
            log.info("javabot.properties.file = " + javabotProperties);
            log.info("javabotProperties = " + javabotProperties);
            java.io.FileInputStream in = new java.io.FileInputStream(javabotProperties);
            properties.load(in);
            in.close();
        }
        catch (java.io.FileNotFoundException fnfe) {
            success = this.failProperties("Could not find Properties file : javabot.properties");
            // System.exit(1);
        }
        catch (java.io.IOException ioeLoad) {
            success = this.failProperties("Could not load or close Properties file : javabot.properties");
            // System.exit(1);
        }
        busy = false;
        notifyAll();
        if (properties.containsKey("Server")) {
            server = properties.getProperty("Server");
            if (server.equals("")) {
                success = this.failProperties("Properties file contains a blank entry for Server");
                // System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Server.");
            // System.exit(1);
        }
        if (properties.containsKey("Port")) {
            try {
                port = Integer.parseInt(properties.getProperty("Port"));
            }
            catch (java.lang.NumberFormatException nfe) {
                success = this.failProperties("Properties file contains an invalid entry for Port");
                // System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Port.");
            // System.exit(1);
        }
        if (properties.containsKey("Name")) {
            name = properties.getProperty("Name");
            if (name.equals("")) {
                success = this.failProperties("Properties file contains a blank entry for Name");
                //System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Name.");
            // System.exit(1);
        }
        if (properties.containsKey("Nickname")) {
            nickname = properties.getProperty("Nickname");
            if (nickname.equals("")) {
                success = this.failProperties("Properties file contains a blank entry for Nickname");
                //System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Nickname.");
            //System.exit(1);
        }
        if (properties.containsKey("Channel")) {
            channel = properties.getProperty("Channel").toLowerCase();
            if (channel.equals("")) {
                success = this.failProperties("Properties file contains a blank entry for Channel");
                //System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Channel.");
            //System.exit(1);
        }
        if (properties.containsKey("ChannelModes")) {
            channelModes = properties.getProperty("ChannelModes");
            //noinspection StatementWithEmptyBody
            if (channelModes.equals("")) {
                //we dont necessarily care if there are no modes so don't whine about it
                //success = this.failProperties("Properties file contains a blank entry for ChannelModes");
                //System.exit(1);
            }
        }
        else {
            success = this.failProperties("Properties file does not contain ChannelModes.");
            //System.exit(1);
        }
        if (properties.containsKey("Autovoice")) {
            autovoice = Boolean.parseBoolean(properties.getProperty("Autovoice"));
        }
        else {
            success = this.failProperties("Properties file does not contain Autovoice.");
            //System.exit(1);
        }
        if (properties.containsKey("Autogreet")) {
            autovoice = Boolean.parseBoolean(properties.getProperty("Autogreet"));
        }
        else {
            success = this.failProperties("Properties file does not contain Autogreet.");
            //System.exit(1);
        }
        if (properties.containsKey("Floodprotection")) {
            flood = Boolean.parseBoolean(properties.getProperty("Floodprotection"));
        }
        else {
            success = this.failProperties("Properties file does not contain Floodprotection.");
            //System.exit(1);
        }
        if (properties.containsKey("Opme")) {
            opme = Boolean.parseBoolean(properties.getProperty("Opme"));
        }
        else {
            success = this.failProperties("Properties file does not contain Opme.");
            //System.exit(1);
        }
        if (properties.containsKey("CycleForOps")) {
            cycleForOps = Boolean.parseBoolean(properties.getProperty("CycleForOps"));
        }
        else {
            success = this.failProperties("Properties file does not contain CycleForOps.");
            //System.exit(1);
        }
        if (properties.containsKey("DynamicLimit")) {
            dynamicLimit = Boolean.parseBoolean(properties.getProperty("DynamicLimit"));
        }
        else {
            success = this.failProperties("Properties file does not contain DynamicLimit.");
            //System.exit(1);
        }
        if (properties.containsKey("Join")) {
            join = properties.getProperty("Join");
            java.util.StringTokenizer st = new java.util.StringTokenizer(join, ":");
            if (st.countTokens() != 2) {
                success = this.failProperties("Properties file contains invalid ratio for Join");
            }
            else {
                while (st.hasMoreTokens()) {
                    try {
                        int number = Integer.parseInt(st.nextToken());
                    }
                    catch (java.lang.NumberFormatException nfe) {
                        success = this.failProperties("Properties file contains an invalid value for Join");
                    }
                }
            }
        }
        else {
            success = this.failProperties("Properties file does not contain Join.");
            // System.exit(1);
        }
        return success;
    }

    /** Log an error and return false
     * @param error Message to log
     * @return false
     */    
    private boolean failProperties(String error) {
        log.severe(error);
        // consoleOutput.append(error + "\n");
        return false;
    }
    
}
