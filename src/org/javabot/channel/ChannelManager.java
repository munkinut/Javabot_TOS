/*
 * ChannelManager.java - provides channel management for JavaBot
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

package org.javabot.channel;

import org.javabot.engine.IRCCommands;
import java.util.Hashtable;
import java.util.logging.Logger;


/** Provides channel management functionality.
 */
public class ChannelManager implements org.javabot.util.MyObserver {

    final Logger log = Logger.getLogger(this.getClass().getName());

    /** Channel users keyed by hostmask.
     */    
    private Hashtable<String, ChannelUser> channelUsers;
    /** The channel to manage.
     */    
    private String channel;
    /** The initial channel modes for the channel being managed.
     */    
    private String initialModes;
    /** The outbound stream to the server.
     */    
    private final java.io.DataOutputStream outbound;
    /** A timer for tracking timed events.
     */    
    private final java.util.Timer timer;

    /** The number of users in channel.
     */    
    private int namesCount;
    /** The number of users in channel at the last count.
     */    
    private int lastNamesCount;
    
    /** The ratio which restricts the allowed number of joiners per timeframe.
     */    
    protected static String joinRatio;

    /** Creates new ChannelManager
     * @param outbound outbound stream to server
     */
    public ChannelManager(java.io.DataOutputStream outbound) {
        log.info("Channel Manager created...");
        this.outbound = outbound;
        this.channelUsers = new Hashtable<>();
        this.timer = new java.util.Timer(true);
        org.javabot.configuration.PropertyManager pm = org.javabot.configuration.PropertyManager.getInstance();
        this.channel = pm.getChannel();
        this.initialModes = pm.getChannelModes();
        ChannelManager.joinRatio = pm.getJoinRatio();
        this.lastNamesCount = 0;
    }
    
    /** Get number of users in channel.
     * @return Number of users in channel.
     */    
    public int getNamesCount() {
        log.info("getNamesCount() returns " + namesCount);
        return namesCount;
    }
    
    /** Set number of users in channel.
     * @param namesCount Number of users in channel.
     */    
    public void setNamesCount(int namesCount) {
        log.info("setNamesCount() sets namesCount to " + namesCount);
        this.namesCount = namesCount;
    }
    
    /** Get number of users in channel at the last count.
     * @return Number of users in channel at the last count.
     */    
    public int getLastNamesCount() {
        log.info("getLastNamesCount() returns " + lastNamesCount);
        return lastNamesCount;
    }
    
    /** Set number of users in channel at the last count.
     * @param lastNamesCount Number of users in channel at the last count.
     */    
    public void setLastNamesCount(int lastNamesCount) {
        log.info("setLastNamesCount() sets namesCount to " + lastNamesCount);
        this.lastNamesCount = lastNamesCount;
    }
    
    /** Set the channel to be managed.
     * @param channel Channel to be managed.
     */    
    public void setChannel(String channel) {
        log.info("[CM] : setChannel() sets channel to " + channel);
        this.channel = channel;
    }

    /** Set the initial channel modes.
     * @param initialModes Initial channel modes.
     */    
    public void setInitialModes(String initialModes) {
        log.info("[CM] : setInitialModes() sets initialModes to " + initialModes);
        this.initialModes = initialModes;
    }

    /** Get the name of the channel being managed.
     * @return Name of the channel being managed.
     */    
    public String getChannel() {
        log.info("[CM] : getChannel() returns " + channel);
        return this.channel;
    }
    
    /** Get the initial modes for the channel being managed.
     * @return Initial modes for the channel being managed.
     */    
    public String getInitialModes() {
        log.info("[CM] : getInitialModes() returns " + initialModes);
        return this.initialModes;
    }
    
    /** Get the collection of channel users.
     * @return The collection of channel users.  This is a Hashtable keyed by hostmask.
     */    
    public Hashtable<String, ChannelUser> getChannelUsers() {
        log.info("[CM] : getChannelUsers() returns \n\n" + channelUsers);
        return this.channelUsers;
    }
    
    /** Get a ChannelUser object based on the hostmask.
     * @param hostmask Hostmask of the ChannelUser.
     * @return ChannelUser object.
     */    
    public ChannelUser getChannelUser(String hostmask) {
        log.info("[CM] : getChannelUser() hostmask = " + hostmask);
        ChannelUser channelUser = null;
        if (channelUsers.containsKey(hostmask)) {
            log.info("[CM] : getChannelUser() channelUsers contains " + hostmask);
            channelUser = channelUsers.get(hostmask);
        }
        return channelUser;
    }
    
    /** Set the collection of channel users.
     * @param channelUsers Collection of channel users.  This is a Hashtable of ChannelUser objects keyed by hostmask.
     */    
    public void setChannelUsers(Hashtable<String, ChannelUser> channelUsers) {
        log.info("[CM] : setChannelUsers() sets channelUsers to \n\n" + channelUsers);
        this.channelUsers = channelUsers;
    }
    
    /** Add a channel user to the collection of channel users.
     * @param hostmask Hostmask of channel user.
     * @param nick Nickname of channel user.
     */    
    public void addChannelUser(String hostmask, String nick) {
        log.info("[CM] : addChannelUser() hostmask = " + hostmask + ", nick = " + nick);
        if (!channelUsers.containsKey(hostmask)) {
            log.info("[CM] : addChannelUser() channelUsers does not contain " + hostmask + " ... adding");
            this.channelUsers.put(hostmask, new ChannelUser(nick));
        }
        else {
            log.info("[CM] : addChannelUser() channelUsers already contains " + hostmask);
            ChannelUser user = this.channelUsers.get(hostmask);
            if (!user.getNick().equals(nick)) {
                log.info("[CM] : addChannelUser() nick does not match for " + hostmask + " ... changing to " + nick);
                user.setNick(nick);
            }
        }
    }
    
    /** Change the nickname of a channel user.
     * @param hostmask Hostmask of the channel user.
     * @param nick New nickname.
     */    
    public void changeChannelUserNick(String hostmask, String nick) {
        log.info("[CM] : changeChannelUserNick() hostmask = " + hostmask + ", nick = " + nick);
        if (channelUsers.containsKey(hostmask)) {
            log.info("[CM] : changeChannelUserNick() channelUsers contains " + hostmask);
            ChannelUser user = channelUsers.get(hostmask);
            user.setNick(nick);
        }
    }
    
    /** Ignore a channel user.
     * @param hostmask Hostmask of channel user to ignore.
     */    
    public void ignoreUser(String hostmask) {
        ChannelUser channelUser = channelUsers.get(hostmask);
        channelUser.setIgnore(true);
    }
    
    /** Stop ignoring a channel user.
     * @param hostmask Hostmask of channel user to stop ignoring.
     */    
    public void unignoreUser(String hostmask) {
        ChannelUser channelUser = channelUsers.get(hostmask);
        channelUser.setIgnore(false);
    }
    
    /** Remove a channel user.
     * @param hostmask Hostmask of channel user to remove.
     */    
    public void removeChannelUser(String hostmask) {
        log.info("[CM] : removeChannelUser() hostmask = " + hostmask);
        if (channelUsers.containsKey(hostmask)) {
            log.info("[CM] : removeChannelUser() channelUsers contains " + hostmask + " ... removing");
            ChannelUser user = this.channelUsers.remove(hostmask);
        }
    }
    
    /** Remove a channel user by nickname.
     * @param nick Nickname of channel user to remove.
     */    
    public void removeChannelUserByNick(String nick) {
        log.info("[CM] : removeChannelUserByNick() nick = " + nick);
        java.util.Enumeration e = channelUsers.keys();
        ChannelUser user;
        String hostmask;
        while (e.hasMoreElements()) {
            hostmask = (String)e.nextElement();
            user = channelUsers.get(hostmask);
            if (user.getNick().equals(nick)) {
                log.info("[CM] : removeChannelUserByNick() channelUsers contains " + nick + " ... removing");
                ChannelUser channelUser = channelUsers.remove(hostmask);
                break;
            }
        }
    }
    
    /** Remove all channel users.
     */    
    public void removeAllChannelUsers() {
        log.info("[CM] : removeAllChannelUsers()");
        java.util.Enumeration e = channelUsers.keys();
        String hostmask;
        while (e.hasMoreElements()) {
            hostmask = (String)e.nextElement();
            channelUsers.remove(hostmask);
        }
    }
    
    /** Limit channel according to the join ratio.
     */    
    public void limitChannel(){
        log.info("[CM] : limitChannel()");
        org.javabot.task.ChanLimitTask chanLimitTask = new org.javabot.task.ChanLimitTask();
        chanLimitTask.registerInterest(this);
        timer.scheduleAtFixedRate(chanLimitTask, 0, this.getMaxTime(org.javabot.security.FloodCounter.JOIN));
    }
    
    /** Get maximum hits on channel for a particular flood type.
     * @param floodType Flood type as defined in org.javabot.security.FloodCounter.
     * @return Maximum number of hits allowed for flood type.
     */    
    public int getMaxHits(int floodType) {
        log.info("[CM] : getMaxHits() for floodType " + floodType);
        int maxHits = 0;
        if (floodType == org.javabot.security.FloodCounter.PRIVMSG) {
            // maxHits = Integer.parseInt(ChannelManager.privmsgRatio.substring(0, ChannelManager.privmsgRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for privmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CHANMSG) {
            // maxHits = Integer.parseInt(ChannelManager.chanmsgRatio.substring(0, ChannelManager.chanmsgRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for chanmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CTCP) {
            // maxHits = Integer.parseInt(ChannelManager.ctcpRatio.substring(0, ChannelManager.ctcpRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for ctcp = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.COLOUR) {
            // maxHits = Integer.parseInt(ChannelManager.colourRatio.substring(0, ChannelManager.colourRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for colour = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.DCC) {
            // maxHits = Integer.parseInt(ChannelManager.dccRatio.substring(0, ChannelManager.dccRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for dcc = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.JOIN) {
            maxHits = Integer.parseInt(ChannelManager.joinRatio.substring(0, ChannelManager.joinRatio.indexOf(":")));
            log.info("[CM] : getMaxHits() maxHits for join = " + maxHits);
        }
        return maxHits;
    }
    
    /** The maximum time frame in which maximum hits are allowed for a particular flood type.
     * @param floodType Flood type as defined in org.javabot.security.FloodCounter.
     * @return Maximum time frame within which maximum hits are allowed for flood type.
     */    
    public int getMaxTime(int floodType) {
        log.info("[CM] : getMaxTime() for floodType " + floodType);
        int maxHits = 0;
        if (floodType == org.javabot.security.FloodCounter.PRIVMSG) {
            // maxHits = Integer.parseInt(ChannelManager.privmsgRatio.substring(ChannelManager.privmsgRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for privmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CHANMSG) {
            // maxHits = Integer.parseInt(ChannelManager.chanmsgRatio.substring(ChannelManager.chanmsgRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for chanmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CTCP) {
            // maxHits = Integer.parseInt(ChannelManager.ctcpRatio.substring(ChannelManager.ctcpRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for ctcp = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.COLOUR) {
            // maxHits = Integer.parseInt(ChannelManager.colourRatio.substring(ChannelManager.colourRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for colour = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.DCC) {
            // maxHits = Integer.parseInt(ChannelManager.dccRatio.substring(ChannelManager.dccRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for dcc = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.JOIN) {
            maxHits = Integer.parseInt(ChannelManager.joinRatio.substring(ChannelManager.joinRatio.indexOf(":")+1)) * 1000;
            log.info("[CM] : getMaxTime() maxTime for join = " + maxHits);
        }
        return maxHits;
    }
    
    /** Join the channel to be managed.
     */    
    public void join() {
        IRCCommands.join(channel, outbound);
    }
    
    /** Part the channel being managed.
     */    
    public void part() {
        IRCCommands.part(channel, outbound);
    }
    
    /** Set the channel modes to the default initial modes.
     */    
    public void setModes() {
        if (!initialModes.equals("")) IRCCommands.changeChannelModes(channel, "+"+initialModes, outbound);
    }

    /** Set the channel modes.
     * @param modes Channel modes.
     */    
    public void setModes(String modes) {
        if (!modes.equals("")) IRCCommands.changeChannelModes(channel, "+"+modes, outbound);
    }
    
    /** Unset channel modes.
     * @param modes Channel modes to unset.
     */    
    public void unsetModes(String modes) {
        if (!modes.equals("")) IRCCommands.changeChannelModes(channel, "-"+modes, outbound);
    }
    
    /** Kill the timer associated with this object.  Called by the owner of this object on shutdown of JavaBot.
     */    
    public void killTimer() {
        this.timer.cancel();
    }
    
    /** Observer method allowing a TimerTask object to trigger events for ChannelManager to respond to.
     * @param event Event type as defined in org.javabot.util.MyObserver.
     */    
    public void notifyEvent(int event) {
        log.info("[CM] : notifyEvent(chanlimit) for event " + event);
        if (event == org.javabot.security.SecurityManager.CHAN_LIMIT) {
            log.info("[CM] : notifyEvent() event is CHAN_LIMIT");
            // int namesCount = channelUsers.size();
            if ((lastNamesCount != namesCount) || (namesCount == 0)) {
                log.info("notifyEvent() lastNamesCount does not equal namesCount or namesCount is 0 ... changing channel limit");
                log.info("notifyEvent() namesCount is " + namesCount);
                log.info("notifyEvent() lastNamesCount is " + lastNamesCount);
                IRCCommands.changeChannelLimit(channel, namesCount + this.getMaxHits(org.javabot.security.FloodCounter.JOIN), outbound);
                lastNamesCount = namesCount;
                log.info("notifyEvent() lastNamesCount set to " + lastNamesCount);
            }
        }
    }
    
    /** Kick a channel user from the channel.
     * @param channelUser ChannelUser to kick.
     */    
    private void kickUser(ChannelUser channelUser) {
        if (channelUser != null) {
            IRCCommands.kick(channel, channelUser.getNick(), outbound);
        }
    }
    
    /** Ban a channel user from the channel.
     * @param hostmask Hostmask of channel user to ban.
     */    
    private void banUser(String hostmask) {
        IRCCommands.ban(channel, hostmask, outbound);
    }
    
    /** Observer method allowing a TimerTask object to trigger events for ChannelManager to respond to.
     * @param event Event to respond to as defined in org.javabot.util.MyObserver.  Usually used to notify floods and hence accompanied by the flood type and the hostmask of the flooder.
     * @param type The type of flood being notified as defined in org.javabot.security.FloodCounter.
     * @param message Usually the hostmask of the user causing the flood.
     */    
    public void notifyEvent(int event,int type,String message) {
        if (event == org.javabot.security.SecurityManager.FLOOD) {
            ChannelUser channelUser = this.getChannelUser(message);
            String nickTo;
            if (type == org.javabot.security.FloodCounter.CHANMSG) {
                this.kickUser(channelUser);
            }
            else if (type == org.javabot.security.FloodCounter.BAN) {
                this.banUser(message);
                this.kickUser(channelUser);
            }
        }
    }
    
    /** An unused event notifier, required to satisfy the requirements of the MyObserver interface.
     * @param event Event.
     * @param type Event type.
     */    
    public void notifyEvent(int event,int type) {
    }
    
}
