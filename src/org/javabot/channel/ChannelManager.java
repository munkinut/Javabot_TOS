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


/** Provides channel management functionality.
 */
public class ChannelManager extends Object implements org.javabot.util.MyObserver {
    
    /** Debug flag.
     */    
    private final boolean debug = false;
    /** Channel users keyed by hostmask.
     */    
    private Hashtable channelUsers;
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
    
    /** A handler for the main properties file.
     */    
    private final org.javabot.configuration.PropertyManager pm;
    
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
        if (debug) System.out.println("[CM] : Channel Manager created");
        this.outbound = outbound;
        this.channelUsers = new Hashtable();
        this.timer = new java.util.Timer(true);
        this.pm = org.javabot.configuration.PropertyManager.getInstance();
        this.channel = pm.getChannel();
        this.initialModes = pm.getChannelModes();
        ChannelManager.joinRatio = pm.getJoinRatio();
        this.lastNamesCount = 0;
    }
    
    /** Get number of users in channel.
     * @return Number of users in channel.
     */    
    public int getNamesCount() {
        if (debug) System.out.println("[CM] : getNamesCount() returns " + namesCount);
        return namesCount;
    }
    
    /** Set number of users in channel.
     * @param namesCount Number of users in channel.
     */    
    public void setNamesCount(int namesCount) {
        if (debug) System.out.println("[CM] : setNamesCount() sets namesCount to " + namesCount);
        this.namesCount = namesCount;
    }
    
    /** Get number of users in channel at the last count.
     * @return Number of users in channel at the last count.
     */    
    public int getLastNamesCount() {
        if (debug) System.out.println("[CM] : getLastNamesCount() returns " + lastNamesCount);
        return lastNamesCount;
    }
    
    /** Set number of users in channel at the last count.
     * @param lastNamesCount Number of users in channel at the last count.
     */    
    public void setLastNamesCount(int lastNamesCount) {
        if (debug) System.out.println("[CM] : setLastNamesCount() sets namesCount to " + lastNamesCount);
        this.lastNamesCount = lastNamesCount;
    }
    
    /** Set the channel to be managed.
     * @param channel Channel to be managed.
     */    
    public void setChannel(String channel) {
        if (debug) System.out.println("[CM] : setChannel() sets channel to " + channel);
        this.channel = channel;
    }

    /** Set the initial channel modes.
     * @param initialModes Initial channel modes.
     */    
    public void setInitialModes(String initialModes) {
        if (debug) System.out.println("[CM] : setInitialModes() sets initialModes to " + initialModes);
        this.initialModes = initialModes;
    }

    /** Get the name of the channel being managed.
     * @return Name of the channel being managed.
     */    
    public String getChannel() {
        if (debug) System.out.println("[CM] : getChannel() returns " + channel);
        return this.channel;
    }
    
    /** Get the initial modes for the channel being managed.
     * @return Initial modes for the channel being managed.
     */    
    public String getInitialModes() {
        if (debug) System.out.println("[CM] : getInitialModes() returns " + initialModes);
        return this.initialModes;
    }
    
    /** Get the collection of channel users.
     * @return The collection of channel users.  This is a Hashtable keyed by hostmask.
     */    
    public Hashtable getChannelUsers() {
        if (debug) System.out.println("[CM] : getChannelUsers() returns \n\n" + channelUsers);
        return this.channelUsers;
    }
    
    /** Get a ChannelUser object based on the hostmask.
     * @param hostmask Hostmask of the ChannelUser.
     * @return ChannelUser object.
     */    
    public ChannelUser getChannelUser(String hostmask) {
        if (debug) System.out.println("[CM] : getChannelUser() hostmask = " + hostmask);
        ChannelUser channelUser = null;
        if (channelUsers.containsKey(hostmask)) {
            if (debug) System.out.println("[CM] : getChannelUser() channelUsers contains " + hostmask);
            channelUser = (ChannelUser)channelUsers.get(hostmask);
        }
        return channelUser;
    }
    
    /** Set the collection of channel users.
     * @param channelUsers Collection of channel users.  This is a Hashtable of ChannelUser objects keyed by hostmask.
     */    
    public void setChannelUsers(Hashtable channelUsers) {
        if (debug) System.out.println("[CM] : setChannelUsers() sets channelUsers to \n\n" + channelUsers);
        this.channelUsers = channelUsers;
    }
    
    /** Add a channel user to the collection of channel users.
     * @param hostmask Hostmask of channel user.
     * @param nick Nickname of channel user.
     */    
    public void addChannelUser(String hostmask, String nick) {
        if (debug) System.out.println("[CM] : addChannelUser() hostmask = " + hostmask + ", nick = " + nick);
        if (!channelUsers.containsKey(hostmask)) {
            if (debug) System.out.println("[CM] : addChannelUser() channelUsers does not contain " + hostmask + " ... adding");
            this.channelUsers.put(hostmask, new ChannelUser(nick));
        }
        else {
            if (debug) System.out.println("[CM] : addChannelUser() channelUsers already contains " + hostmask);
            ChannelUser user = (ChannelUser)this.channelUsers.get(hostmask);
            if (!user.getNick().equals(nick)) {
                if (debug) System.out.println("[CM] : addChannelUser() nick does not match for " + hostmask + " ... changing to " + nick);
                user.setNick(nick);
            }
        }
    }
    
    /** Change the nickname of a channel user.
     * @param hostmask Hostmask of the channel user.
     * @param nick New nickname.
     */    
    public void changeChannelUserNick(String hostmask, String nick) {
        if (debug) System.out.println("[CM] : changeChannelUserNick() hostmask = " + hostmask + ", nick = " + nick);
        if (channelUsers.containsKey(hostmask)) {
            if (debug) System.out.println("[CM] : changeChannelUserNick() channelUsers contains " + hostmask);
            ChannelUser user = (ChannelUser)channelUsers.get(hostmask);
            user.setNick(nick);
        }
    }
    
    /** Ignore a channel user.
     * @param hostmask Hostmask of channel user to ignore.
     */    
    public void ignoreUser(String hostmask) {
        ChannelUser channelUser = (ChannelUser)channelUsers.get(hostmask);
        channelUser.setIgnore(true);
    }
    
    /** Stop ignoring a channel user.
     * @param hostmask Hostmask of channel user to stop ignoring.
     */    
    public void unignoreUser(String hostmask) {
        ChannelUser channelUser = (ChannelUser)channelUsers.get(hostmask);
        channelUser.setIgnore(false);
    }
    
    /** Remove a channel user.
     * @param hostmask Hostmask of channel user to remove.
     */    
    public void removeChannelUser(String hostmask) {
        if (debug) System.out.println("[CM] : removeChannelUser() hostmask = " + hostmask);
        if (channelUsers.containsKey(hostmask)) {
            if (debug) System.out.println("[CM] : removeChannelUser() channelUsers contains " + hostmask + " ... removing");
            ChannelUser user = (ChannelUser)this.channelUsers.remove(hostmask);
        }
    }
    
    /** Remove a channel user by nickname.
     * @param nick Nickname of channel user to remove.
     */    
    public void removeChannelUserByNick(String nick) {
        if (debug) System.out.println("[CM] : removeChannelUserByNick() nick = " + nick);
        java.util.Enumeration e = channelUsers.keys();
        ChannelUser user;
        String hostmask;
        while (e.hasMoreElements()) {
            hostmask = (String)e.nextElement();
            user = (ChannelUser)channelUsers.get(hostmask);
            if (user.getNick().equals(nick)) {
                if (debug) System.out.println("[CM] : removeChannelUserByNick() channelUsers contains " + nick + " ... removing");
                ChannelUser channelUser = (ChannelUser)channelUsers.remove(hostmask);
                break;
            }
        }
    }
    
    /** Remove all channel users.
     */    
    public void removeAllChannelUsers() {
        if (debug) System.out.println("[CM] : removeAllChannelUsers()");
        java.util.Enumeration e = channelUsers.keys();
        ChannelUser user;
        String hostmask;
        while (e.hasMoreElements()) {
            hostmask = (String)e.nextElement();
            user = (ChannelUser)channelUsers.remove(hostmask);
        }
    }
    
    /** Limit channel according to the join ratio.
     */    
    public void limitChannel(){
        if (debug) System.out.println("[CM] : limitChannel()");
        org.javabot.task.ChanLimitTask chanLimitTask = new org.javabot.task.ChanLimitTask();
        chanLimitTask.registerInterest(this);
        timer.scheduleAtFixedRate(chanLimitTask, 0, this.getMaxTime(org.javabot.security.FloodCounter.JOIN));
    }
    
    /** Get maximum hits on channel for a particular flood type.
     * @param floodType Flood type as defined in org.javabot.security.FloodCounter.
     * @return Maximum number of hits allowed for flood type.
     */    
    public int getMaxHits(int floodType) {
        if (debug) System.out.println("[CM] : getMaxHits() for floodType " + floodType);
        int maxHits = 0;
        if (floodType == org.javabot.security.FloodCounter.PRIVMSG) {
            // maxHits = Integer.parseInt(ChannelManager.privmsgRatio.substring(0, ChannelManager.privmsgRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for privmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CHANMSG) {
            // maxHits = Integer.parseInt(ChannelManager.chanmsgRatio.substring(0, ChannelManager.chanmsgRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for chanmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CTCP) {
            // maxHits = Integer.parseInt(ChannelManager.ctcpRatio.substring(0, ChannelManager.ctcpRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for ctcp = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.COLOUR) {
            // maxHits = Integer.parseInt(ChannelManager.colourRatio.substring(0, ChannelManager.colourRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for colour = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.DCC) {
            // maxHits = Integer.parseInt(ChannelManager.dccRatio.substring(0, ChannelManager.dccRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for dcc = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.JOIN) {
            maxHits = Integer.parseInt(ChannelManager.joinRatio.substring(0, ChannelManager.joinRatio.indexOf(":")));
            if (debug) System.out.println("[CM] : getMaxHits() maxHits for join = " + maxHits);
        }
        return maxHits;
    }
    
    /** The maximum time frame in which maximum hits are allowed for a particular flood type.
     * @param floodType Flood type as defined in org.javabot.security.FloodCounter.
     * @return Maximum time frame within which maximum hits are allowed for flood type.
     */    
    public int getMaxTime(int floodType) {
        if (debug) System.out.println("[CM] : getMaxTime() for floodType " + floodType);
        int maxHits = 0;
        if (floodType == org.javabot.security.FloodCounter.PRIVMSG) {
            // maxHits = Integer.parseInt(ChannelManager.privmsgRatio.substring(ChannelManager.privmsgRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for privmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CHANMSG) {
            // maxHits = Integer.parseInt(ChannelManager.chanmsgRatio.substring(ChannelManager.chanmsgRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for chanmsg = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.CTCP) {
            // maxHits = Integer.parseInt(ChannelManager.ctcpRatio.substring(ChannelManager.ctcpRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for ctcp = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.COLOUR) {
            // maxHits = Integer.parseInt(ChannelManager.colourRatio.substring(ChannelManager.colourRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for colour = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.DCC) {
            // maxHits = Integer.parseInt(ChannelManager.dccRatio.substring(ChannelManager.dccRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for dcc = " + maxHits);
        }
        else if (floodType == org.javabot.security.FloodCounter.JOIN) {
            maxHits = Integer.parseInt(ChannelManager.joinRatio.substring(ChannelManager.joinRatio.indexOf(":")+1)) * 1000;
            if (debug) System.out.println("[CM] : getMaxTime() maxTime for join = " + maxHits);
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
        if (debug) System.out.println("[CM] : notifyEvent(chanlimit) for event " + event);
        if (event == org.javabot.security.SecurityManager.CHAN_LIMIT) {
            if (debug) System.out.println("[CM] : notifyEvent() event is CHAN_LIMIT");
            // int namesCount = channelUsers.size();
            if ((lastNamesCount != namesCount) || (namesCount == 0)) {
                if (debug) {
                    System.out.println("[CM] : notifyEvent() lastNamesCount does not equal namesCount or namesCount is 0 ... changing channel limit");
                    System.out.println("[CM] : notifyEvent() namesCount is " + namesCount);
                    System.out.println("[CM] : notifyEvent() lastNamesCount is " + lastNamesCount);
                }
                IRCCommands.changeChannelLimit(channel, namesCount + this.getMaxHits(org.javabot.security.FloodCounter.JOIN), outbound);
                lastNamesCount = namesCount;
                if (debug) System.out.println("[CM] : notifyEvent() lastNamesCount set to " + lastNamesCount);
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
            String hostmask = message;
            ChannelUser channelUser = this.getChannelUser(hostmask);
            String nickTo;
            if (type == org.javabot.security.FloodCounter.CHANMSG) {
                this.kickUser(channelUser);
            }
            else if (type == org.javabot.security.FloodCounter.BAN) {
                this.banUser(hostmask);
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
