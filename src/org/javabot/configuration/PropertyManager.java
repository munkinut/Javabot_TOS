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

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
    private PropertiesConfiguration properties = null;

    private final FileBasedConfigurationBuilder<PropertiesConfiguration> builder;

    /** The singleton instance
     */
    private static PropertyManager propertyManager;

    /** Creates new PropertyManager */
    private PropertyManager() {
        log.info("PropertyManager() called");
        busy = false;
        Configurations configs = new Configurations();
        builder = configs.propertiesBuilder("config/javabot.properties");
        try {
            properties = builder.getConfiguration();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
    }
    
    /** Provides the singleton instance
     * @return singleton instance of PropertyManager
     */    
    public static synchronized PropertyManager getInstance() {
        if (propertyManager == null) propertyManager = new PropertyManager();
        return propertyManager;
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
            catch (InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String server = properties.getString("Server");
        busy = false;
        notifyAll();
        return server;
    }

    public synchronized String getScriptsLocation() {
        log.info("getScriptsLocation() called");
        while (busy) {
            try {
                log.info("waiting");
                wait();
            }
            catch (InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String scriptsLocation = properties.getString("Scripts_Location");
        busy = false;
        notifyAll();
        return scriptsLocation;
    }

    public synchronized String getBansLocation() {
        log.info("getBansLocation() called");
        while (busy) {
            try {
                log.info("waiting");
                wait();
            }
            catch (InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String bansLocation = properties.getString("Bans_Location");
        busy = false;
        notifyAll();
        return bansLocation;
    }

    public synchronized String getUsersLocation() {
        log.info("getUsersLocation() called");
        while (busy) {
            try {
                log.info("waiting");
                wait();
            }
            catch (InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String usersLocation = properties.getString("Users_Location");
        busy = false;
        notifyAll();
        return usersLocation;
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Server", server);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String name = properties.getString("Name");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Name", name);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String nickname = properties.getString("Nickname");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Nickname", nickname);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String channel = properties.getString("Channel");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Channel", channel);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String channelmodes = properties.getString("ChannelModes");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("ChannelModes", channelmodes);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String joinratio = properties.getString("Join");
        busy = false;
        notifyAll();
        return joinratio;
    }
    
    /** Set the channel join ratio
     * @param joinratio Join ratio for the channel
     */    
    public synchronized void setJoinRatio(String joinratio) {
        log.info("setJoinRatio() called");
        while (busy) {
            try {
                wait();
            }
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Join", joinratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String privmsgratio = properties.getString("Privmsg");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Privmsg", privmsgratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String chanmsgratio = properties.getString("Chanmsg");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Chanmsg", chanmsgratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String colourratio = properties.getString("Colour");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Colour", colourratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String ctcpratio = properties.getString("Ctcp");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Ctcp", ctcpratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        String dccratio = properties.getString("Dcc");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Dcc", dccratio);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        int port = properties.getInt("Port");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Port", port);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean autovoice = properties.getBoolean("Autovoice");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Autovoice", autovoice);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean autogreet = properties.getBoolean("Autogreet");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Autogreet", autogreet);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean floodprotection = properties.getBoolean("Floodprotection");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Floodprotection", floodprotection);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean opme = properties.getBoolean("Opme");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("Opme", opme);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean cycleforops = properties.getBoolean("CycleForOps");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("CycleForOps", cycleforops);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        boolean dynamiclimit = properties.getBoolean("DynamicLimit");
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
            catch (InterruptedException ignored) {
            }
        }
        busy = true;
        if (properties != null) properties.setProperty("DynamicLimit", dynamiclimit);
        try {
            builder.save();
        } catch (ConfigurationException e) {
            log.warning(e.getMessage());
        }
        busy = false;
        notifyAll();
    }

    public synchronized void resetFromConfigBackup() {
        log.info("resetFromConfigBackup() called");
        while (busy) {
            try {
                log.info("waiting");
                wait();
            }
            catch (InterruptedException ie) {
                log.warning("Thread interrupted " + ie.getMessage());
            }
        }
        busy = true;
        String srcDir = properties.getString("SrcDir");
        String srcFile = srcDir + "javabot.properties";
        log.info(srcFile);
        String destFile = srcDir + "javabot.properties.bak";
        log.info(destFile);
        File src = new File(srcFile);
        File dest = new File(destFile);
        String backupsLocation = properties.getString("Backups_Location");
        File source = new File(backupsLocation + "javabot.properties");

        try {
            FileUtils.copyFile(src, dest);
            FileUtils.copyFileToDirectory(source, new File(srcDir));
        } catch (IOException e) {
            log.info(e.getMessage());
            busy = false;
        }
        busy = false;
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
