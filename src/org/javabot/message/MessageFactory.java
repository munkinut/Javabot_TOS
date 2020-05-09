/*
 * MessageFactory.java - manufactures irc message interface objects
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

package org.javabot.message;

import java.util.logging.Logger;

public class MessageFactory {

    Logger log = Logger.getLogger(this.getClass().getName());

    // Constants describing different message types
    // From RFC2812
    
    public static final int PASS = 0;
    public static final int NICK = 1;
    public static final int USER = 2;
    public static final int OPER = 3;
    public static final int MODE = 4; // note this could be user mode or channel mode
    public static final int SERVICE = 5;
    public static final int QUIT = 6;
    public static final int SQUIT = 7;
    public static final int JOIN = 8;
    public static final int PART = 9;
    public static final int TOPIC = 10;
    public static final int NAMES = 11;
    public static final int LIST = 12;
    public static final int INVITE = 13;
    public static final int KICK = 14;
    public static final int PRIVMSG = 15;
    public static final int NOTICE = 16;
    public static final int MOTD = 17;
    public static final int LUSERS = 18;
    public static final int VERSION = 19;
    public static final int STATS = 20;
    public static final int LINKS = 21;
    public static final int TIME = 22;
    public static final int CONNECT = 23;
    public static final int TRACE = 24;
    public static final int ADMIN = 25;
    public static final int INFO = 26;
    public static final int SERVLIST = 27;
    public static final int SQUERY = 28;
    public static final int WHO = 29;
    public static final int WHOIS = 30;
    public static final int WHOWAS = 31;
    public static final int KILL = 32;
    public static final int PING = 33;
    public static final int PONG = 34;
    public static final int ERROR = 35;
    public static final int AWAY = 36;
    public static final int REHASH = 37;
    public static final int DIE = 38;
    public static final int RESTART = 39;
    public static final int SUMMON = 40;
    public static final int USERS = 41;
    public static final int OPERWALL = 42;
    public static final int USERHOST = 43;
    public static final int ISON = 44;
    
    // Constants defining different numeric messages
    
    public static final int RPL_NAMREPLY = 353;
    public static final int RPL_ENDOFNAMES = 366;

    /** Creates new MessageFactory */
    public MessageFactory() {
    }
    
    public MessageInterface getMessage(String inbound) {
        MessageInterface returnMessage = null;
        if (inbound.startsWith("PING ")) {
            String params = inbound.substring(inbound.indexOf(":")+1);
            returnMessage = this.createPingMessage(params);
        }
        else
        if (inbound.startsWith(":")) {
            returnMessage = this.createPrefixedMessage(inbound);
        }
        return returnMessage;
    }
    
    private MessageInterface createPrefixedMessage(String inbound) {
        MessageInterface returnMessage = null;
        String prefix = inbound.substring(inbound.indexOf(":")+1, inbound.indexOf(" "));
        if (prefix.contains("!")) {
            // we have a nickname
            String nick = prefix.substring(0, prefix.indexOf("!"));
            String hostmask = prefix.substring(prefix.indexOf("!")+1);
            String msg = inbound.substring(inbound.indexOf(" ")+1);
            if (msg.startsWith("JOIN ")) {
                String channel = msg.substring(msg.indexOf(":")+1);
                returnMessage = this.createJoinMessage(nick, hostmask, channel);
            }
            else
            if (msg.startsWith("PART ")) {
                String channel = msg.substring(msg.indexOf(" ")+1);
                returnMessage = this.createPartMessage(nick, hostmask, channel);
            }
            else
            if (msg.startsWith("KICK ")) {
                String chanMsgTo = msg.substring(msg.indexOf(" ")+1, msg.indexOf(" :"));
                String channel = chanMsgTo.substring(0, chanMsgTo.indexOf(" "));
                String msgTo = chanMsgTo.substring(chanMsgTo.indexOf(" ")+1);
                String params = msg.substring(msg.indexOf(":")+1);
                returnMessage = this.createKickMessage(nick, hostmask, channel, msgTo, params);
            }
            else
            if (msg.startsWith("QUIT ")) {
                String channel = msg.substring(msg.indexOf(":")+1);
                returnMessage = this.createQuitMessage(nick, hostmask, channel);
            }
            else
            if (msg.startsWith("PRIVMSG ")) {
                String msgTo = msg.substring(msg.indexOf(" ")+1, msg.indexOf(" :"));
                String params = msg.substring(msg.indexOf(":")+1);
                returnMessage = this.createPrivmsgMessage(nick, hostmask, msgTo, params);
            }
        }
        else {
            // we have a servername
            String msg = inbound.substring(inbound.indexOf(" ")+1);
            if (msg.startsWith("" + MessageFactory.RPL_NAMREPLY + " ")) {
                // 353 RPL_NAMREPLY
                String names = msg.substring(msg.indexOf(" :")+2);
                String theRest = msg.substring(0,msg.indexOf(" :"));
                String channelType = "";
                String channel = "";
                String msgTo = "";
                if (theRest.contains(" = ")) {
                    channelType = "public";
                    msgTo = theRest.substring(theRest.indexOf(" ")+1, theRest.indexOf(" = "));
                    channel = theRest.substring(theRest.indexOf(" = ")+3);
                }
                else
                if (theRest.contains(" * ")) {
                    channelType = "private";
                    msgTo = theRest.substring(theRest.indexOf(" ")+1, theRest.indexOf(" * "));
                    channel = theRest.substring(theRest.indexOf(" * ")+3);
                }
                else
                if (theRest.contains(" @ ")) {
                    channelType = "secret";
                    msgTo = theRest.substring(theRest.indexOf(" ")+1, theRest.indexOf(" @ "));
                    channel = theRest.substring(theRest.indexOf(" @ ")+3);
                }
                returnMessage = this.createNamesReplyMessage(prefix, msgTo, channelType, channel, names);
            }
        }
        return returnMessage;
    }
    
    private MessageInterface createNamesReplyMessage(String server, String msgTo, String channelType, String channel, String names) {
        MessageInterface returnMessage = new NamesReplyMessage();
        returnMessage.setServer(server);
        returnMessage.setMsgTo(msgTo);
        returnMessage.setChannelType(channelType);
        returnMessage.setChannel(channel);
        returnMessage.setNames(names);
        return returnMessage;
    }        
    
    private MessageInterface createPassMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createNickMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createUserMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createOperMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createModeMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createServiceMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createQuitMessage(String nick, String hostmask, String channel) {
        MessageInterface quitMessage = new QuitMessage();
        quitMessage.setNick(nick);
        quitMessage.setHostmask(hostmask);
        quitMessage.setChannel(channel);
        return quitMessage;
    }
    
    private MessageInterface createSquitMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createJoinMessage(String nick, String hostmask, String channel) {
        MessageInterface returnMessage = new JoinMessage();
        returnMessage.setNick(nick);
        returnMessage.setHostmask(hostmask);
        returnMessage.setChannel(channel);
        return returnMessage;
    }
    
    private MessageInterface createPartMessage(String nick, String hostmask, String channel) {
        MessageInterface returnMessage = new PartMessage();
        returnMessage.setNick(nick);
        returnMessage.setHostmask(hostmask);
        returnMessage.setChannel(channel);
        return returnMessage;
    }
    
    private MessageInterface createTopicMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createNamesMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createListMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createInviteMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createKickMessage(String nick, String hostmask, String channel, String msgTo, String params) {
        MessageInterface kickMessage = new KickMessage();
        kickMessage.setNick(nick);
        kickMessage.setHostmask(hostmask);
        kickMessage.setChannel(channel);
        kickMessage.setMsgTo(msgTo);
        kickMessage.setParams(params);
        return kickMessage;
    }
    
    private MessageInterface createPrivmsgMessage(String nick, String hostmask, String msgTo, String params) {
        MessageInterface privmsgMessage = new PrivmsgMessage();
        privmsgMessage.setNick(nick);
        privmsgMessage.setHostmask(hostmask);
        privmsgMessage.setMsgTo(msgTo);
        privmsgMessage.setParams(params);
        return privmsgMessage;
    }
    
    private MessageInterface createNoticeMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createMotdMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createLusersMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createVersionMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createStatsMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createLinksMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createTimeMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createConnectMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createTraceMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createAdminMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createInfoMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createServlistMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createSqueryMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createWhoMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createWhoisMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createWhowasMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createKillMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createPingMessage(String params) {
        MessageInterface pingMessage = new PingMessage();
        pingMessage.setParams(params); // equates to the args of PING
        return pingMessage;
    }

    private MessageInterface createPongMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createErrorMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createAwayMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createRehashMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createDieMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createRestartMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createSummonMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createUsersMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createOperwallMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createUserhostMessage(String inbound) {
        return null;
    }
    
    private MessageInterface createIsonMessage(String inbound) {
        return null;
    }
    
}
