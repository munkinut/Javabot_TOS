/*
 * LowLevelCmdHandler.java - handles irc protocol functions for Javabot
 *
 * Copyright (C) 2020 by Warren Milburn
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

import org.javabot.message.MessageInterface;
import org.javabot.security.SecurityManager;
import org.javabot.channel.ChannelManager;
import org.javabot.configuration.PropertyManager;
import org.javabot.message.MessageFactory;
import org.javabot.script.ScriptHandler;
import org.javabot.security.Ban;
import org.javabot.security.BanManager;
import org.javabot.security.Bans;
import org.javabot.user.User;
import org.javabot.user.UserManager;
import org.javabot.user.Users;
import org.javabot.util.BanPager;
import org.javabot.util.UserPager;

import javax.swing.*;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

class LowLevelCmdHandler {

    final Logger log = Logger.getLogger(this.getClass().getName());

    final IRCCommands ircCommands = IRCCommands.getInstance();

    /** Output stream to the server
     */
    private final DataOutputStream outbound;

    /** Manages bot users
     */
    private final UserManager um;
    /** Manages the channel
     */
    private final ChannelManager cm;
    /** Manages bot security
     */
    private final SecurityManager sm;
    /** Manages the ban file
     */
    private final BanManager bm;
    /** Parses message strings into message objects
     */
    private final MessageFactory mf;
    /** Handles script commands
     */
    private final ScriptHandler sh;
    /** Bots real name
     */
    private final String name;
    /** Bots nickname
     */
    private final String nick;
    /** Channel bot will manage
     */
    private final String channel;

    /** Autovoice flag
     */
    private final boolean autovoice;
    /** Opme flag
     */
    private final boolean opme;
    /** Autogreet flag
     */
    private final boolean autogreet;
    /** Dynamic limit flag
     */
    private final boolean dynamicLimit;


    public LowLevelCmdHandler(JTextArea consoleOutput, DataOutputStream outbound) {
        // TODO if (outbound == null) throw JavabotException
        this.outbound = outbound;
        /** Console for output messages
         */
        this.cm = new ChannelManager(outbound);

        /* Manages the properties file
         */
        PropertyManager pm = PropertyManager.getInstance();
        this.name = pm.getName();
        this.nick = pm.getNickname();
        this.channel = pm.getChannel();
        /* Channel modes
         */
        String channelModes = pm.getChannelModes();
        this.autovoice = pm.getAutovoice();
        /* Flood protection flag
         */
        boolean flood = pm.getFloodProtection();
        this.opme = pm.getOpme();
        this.autogreet = pm.getAutogreet();
        this.dynamicLimit = pm.getDynamicLimit();
        this.um = new org.javabot.user.UserManager();
        this.sm = new org.javabot.security.SecurityManager();
        this.bm = new org.javabot.security.BanManager();
        this.sh = new ScriptHandler(outbound);
        this.mf = new org.javabot.message.MessageFactory();
        this.sm.registerInterest(cm);


    }

    protected void removeAllChannelUsers() {
        cm.removeAllChannelUsers();
    }

    protected void killTimer() {
        cm.killTimer();
    }

    protected void identify() {
        ircCommands.identify(name, nick, outbound);
    }

    /** Generic message handler; examines messages and
     * delegates to the appropriate handler
     * @param messageT Message to examine
     */
    public void handleMessage(MessageInterface messageT) {
        log.info("handleMessage() called");
        if (messageT instanceof org.javabot.message.PingMessage) {
            this.handlePing(messageT);
        }
        else if (messageT instanceof org.javabot.message.JoinMessage) {
            this.handleJoin(messageT);
        }
        else if (messageT instanceof org.javabot.message.PartMessage) {
            this.handlePart(messageT);
        }
        else if (messageT instanceof org.javabot.message.QuitMessage) {
            this.handleQuit(messageT);
        }
        else if (messageT instanceof org.javabot.message.KickMessage) {
            this.handleKick(messageT);
        }
        else if (messageT instanceof org.javabot.message.NamesReplyMessage) {
            this.handleNamesReply(messageT);
        }
        else if (messageT instanceof org.javabot.message.PrivmsgMessage) {
            this.handlePrivmsg(messageT);
        }
    }

    /** Handle ping message
     * @param pingMessage Ping message
     */
    private void handlePing(org.javabot.message.MessageInterface pingMessage) {
        log.info("handlePing() called");
        String params = pingMessage.getParams();
        log.info(params);
        ircCommands.pingpong(params, outbound);
    }

    /** Handle join message
     * @param joinMessage Join message
     */
    private void handleJoin(org.javabot.message.MessageInterface joinMessage) {
        log.info("handleJoin() called");
        String nickFrom = joinMessage.getNick();
        String hostmask = joinMessage.getHostmask();
        String chan = joinMessage.getChannel();
        String fullMask = nickFrom + "!" + hostmask;
        if (bm.matches(fullMask)) {
            ircCommands.ban(chan, hostmask, outbound);
            ircCommands.kick(chan, nickFrom, outbound);
        }
        else {
            cm.addChannelUser(hostmask, nickFrom);
            if (!nickFrom.equals(nick)) {
                ircCommands.names(chan, outbound);
            }
            if (autovoice && um.userIsVoice(hostmask)) ircCommands.autovoice(chan, nickFrom, outbound);
            if (autogreet) {
                String greet = um.getGreet(hostmask);
                if (greet != null) {
                    ircCommands.playGreet(chan, outbound, greet);
                }
            }
        }
    }

    /** Handle part message
     * @param partMessage Part message
     */
    private void handlePart(org.javabot.message.MessageInterface partMessage) {
        log.info("handlePart() called");
        String chan = partMessage.getChannel();
        String hostmask = partMessage.getHostmask();
        cm.removeChannelUser(hostmask);
        ircCommands.names(chan, outbound);
    }

    /** Handle quit message
     * @param quitMessage Quit message
     */
    private void handleQuit(org.javabot.message.MessageInterface quitMessage) {
        log.info("handleQuit() called");
        String chan = quitMessage.getChannel();
        String hostmask = quitMessage.getHostmask();
        cm.removeChannelUser(hostmask);
        ircCommands.names(chan, outbound);
    }

    /** Handle kick message
     * @param kickMessage Kick message
     */
    private void handleKick(org.javabot.message.MessageInterface kickMessage) {
        log.info("handleKick() called");
        String chan = kickMessage.getChannel();
        String hostmask = kickMessage.getHostmask();
        String msgTo = kickMessage.getMsgTo();
        cm.removeChannelUserByNick(msgTo);
        ircCommands.names(chan, outbound);
    }

    /** Handle namesreply message
     * @param namesReplyMessage Namesreply message
     */
    private void handleNamesReply(org.javabot.message.MessageInterface namesReplyMessage) {
        log.info("handleNamesReply() called");
        String names = namesReplyMessage.getNames();
        StringTokenizer st = new StringTokenizer(names, " ");
        cm.setNamesCount(st.countTokens());
        if (st.countTokens() == 1) {
            String token = st.nextToken();
            if (!token.startsWith("@") && (token.equals(nick) || token.equals("+" + nick))) {
                ircCommands.cycle(channel, outbound);
            }
        }
    }

    /** Handle privmg message
     * @param privmsgMessage Privmsg message
     */
    private void handlePrivmsg(org.javabot.message.MessageInterface privmsgMessage) {
        log.info("handlePrivmsg() called");
        String nickFrom = privmsgMessage.getNick();
        String hostmask = privmsgMessage.getHostmask();
        String msgTo = privmsgMessage.getMsgTo();

        if ((msgTo.equals(nick)) && (!nickFrom.equals(nick))) sm.hitFloodCounter(org.javabot.security.FloodCounter.PRIVMSG, hostmask);
        else if ((msgTo.equals(channel)) && (!nickFrom.equals(nick))) sm.hitFloodCounter(org.javabot.security.FloodCounter.CHANMSG, hostmask);

        if (!sm.isIgnored(hostmask)) {

            ArrayList<String> params = this.parseCommand(privmsgMessage.getParams());
            if (!params.isEmpty()) {
                String command = params.get(0);
                if (command.startsWith("!")) {
                    this.handlePublicCmd(command, nickFrom, msgTo, hostmask, params);
                }
                else {
                    this.handlePrivateCmd(command, nickFrom, msgTo, hostmask, params);
                }
            }
        }
    }

    /** Handle private message commands
     * @param command Command
     * @param nickFrom Originating nick
     * @param msgTo Nick / Channel message was sent to
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handlePrivateCmd(String command, String nickFrom, String msgTo, String hostmask, ArrayList<String> params) {
        log.info("handlePrivateCmd() called");
        if ((command.equals("auth")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleAuthCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("pass")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handlePassCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("greet")) && (params.size() >= 2) && (msgTo.equals(nick))) {
            this.handleGreetCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("flags")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleFlagsCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("adduser")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleAddUserCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("deluser")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleDelUserCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("users")) && (params.size() == 1) && (msgTo.equals(nick))) {
            this.handleUsersCmd(nickFrom, hostmask);
        }
        else if ((command.equals("addfriend")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleAddFriendCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("addvoice")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleAddVoiceCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("addop")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleAddOpCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("addmaster")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleAddMasterCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("delfriend")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleDelFriendCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("delvoice")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleDelVoiceCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("delop")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleDelOpCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("delmaster")) && (params.size() == 2) && (msgTo.equals(nick))) {
            this.handleDelMasterCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("kick")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleKickCmd(hostmask, params);
        }
        else if ((command.equals("ban")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleBanCmd(hostmask, params);
        }
        else if ((command.equals("kickban")) && (params.size() == 4) && (msgTo.equals(nick))) {
            this.handleKickBanCmd(hostmask, params);
        }
        else if ((command.equals("addban")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleAddBanCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("delban")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleDelBanCmd(nickFrom, hostmask, params);
        }
        else if ((command.equals("bans")) && (params.size() == 1) && (msgTo.equals(nick))) {
            this.handleBansCmd(nickFrom, hostmask);
        }
        else if ((command.equals("unban")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleUnbanCmd(hostmask, params);
        }
        else if ((command.equals("invite")) && (params.size() == 3) && (msgTo.equals(nick))) {
            this.handleInviteCmd(hostmask, params);
        }
        else if ((command.equals("userfile")) && (params.size() == 1) && (msgTo.equals(nick))) {
            this.handleUserfileCmd(nickFrom, hostmask);
        }
        else if ((command.equals("voiceme")) && (params.size() == 1) && (msgTo.equals(nick))) {
            this.handleVoiceMeCmd(nickFrom, hostmask);
        }
        else if ((command.equals("opme")) && (params.size() == 1) && (msgTo.equals(nick))) {
            this.handleOpMeCmd(nickFrom, hostmask);
        }
    }

    /** Handle public commands sent to the bot via channel
     * @param command Command
     * @param nickFrom Originating nick
     * @param msgTo Nick / Channel message was sent to
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handlePublicCmd(String command, String nickFrom, String msgTo, String hostmask, ArrayList<String> params) {
        log.info("handlePublicCmd() called");
        if ((command.equals("!opme")) && (params.size() == 1) && (msgTo.equals(channel))) {
            this.handlePubOpMeCmd(nickFrom, hostmask);
        }
        else if ((command.equals("!voiceme")) && (params.size() == 1) && (msgTo.equals(channel))) {
            this.handlePubVoiceMeCmd(nickFrom, hostmask);
        }
        else if ((command.equals("!lc")) && (params.size() == 1) && (msgTo.equals(channel))) {
            this.handlePubLcCmd(hostmask);
        }
        else if ((command.equals("!uc")) && (params.size() == 1) && (msgTo.equals(channel))) {
            this.handlePubUcCmd(hostmask);
        }
        else if ((command.equals("!" + nick)) && (params.size() >=2) && (msgTo.equals(channel))) {
            ArrayList<String> cmdParams = this.parseParams(params);
            this.handlePubScriptCmd(channel, nickFrom, hostmask, cmdParams);
        }
    }

    /** Handle auth command
     * @param nickFrom The originating nick
     * @param hostmask The originating hostmask
     * @param params Command parameters
     */
    private void handleAuthCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAuthCmd() called");
        String botnick = params.get(1);
        String botpass = params.get(2);
        if (um.auth(botnick, hostmask, botpass)) {
            ircCommands.privmsg(nickFrom, "User " + botnick + " authorised", outbound);
            if (autovoice && um.userIsVoice(hostmask)) ircCommands.autovoice(channel, nickFrom, outbound);
            if (autogreet) {
                String greet = um.getGreet(hostmask);
                if (greet != null) {
                    ircCommands.playGreet(channel, outbound, greet);
                }
            }
        }
    }

    /** Handle pass command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handlePassCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handlePassCmd() called");
        String oldpass = params.get(1);
        String newpass = params.get(2);
        if (um.pass(hostmask, oldpass, newpass)) {
            ircCommands.privmsg(nickFrom, "Password changed for "+nickFrom+" ("+hostmask+")", outbound);
        }
    }

    /** Handle greet command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleGreetCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleGreetCmd() called");
        StringBuilder greet = new StringBuilder();
        for (int i=1; i < params.size(); i++) {
            greet.append(params.get(i)).append(" ");
        }
        greet = new StringBuilder(greet.toString().trim());
        if (um.userIsFriend(hostmask)) {
            if (um.greet(hostmask, greet.toString())) {
                ircCommands.privmsg(nickFrom, "Greet changed to '"+ greet + "'", outbound);
            }
        }
    }

    /** Handle flags command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleFlagsCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleFlagsCmd() called");
        String botnick = params.get(1);
        String flags = params.get(2);
        char changer = flags.charAt(0);
        if ((changer == '+' || changer == '-') && flags.length() > 1) {
            for (int i = 1;i < flags.length(); i++) {
                char flag = flags.charAt(i);
                if (flag == 'f' && um.userIsOp(hostmask)) {
                    if (changer == '+') {
                        if (um.addFriend(botnick)) {
                            ircCommands.privmsg(nickFrom, "Friend flag added for "+botnick, outbound);
                        }
                    }
                    else {
                        if (um.delFriend(botnick)) {
                            ircCommands.privmsg(nickFrom, "Friend flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'v' && um.userIsOp(hostmask)) {
                    if (changer == '+') {
                        if (um.addVoice(botnick)) {
                            ircCommands.privmsg(nickFrom, "Voice flag added for "+botnick, outbound);
                        }
                    }
                    else {
                        if (um.delVoice(botnick)) {
                            ircCommands.privmsg(nickFrom, "Voice flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'o' && um.userIsOwner(hostmask)) {
                    if (changer == '+') {
                        if (um.addOp(botnick)) {
                            ircCommands.privmsg(nickFrom, "Op flag added for "+botnick, outbound);
                        }
                    }
                    else {
                        if (um.delOp(botnick)) {
                            ircCommands.privmsg(nickFrom, "Op flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'm' && um.userIsOwner(hostmask)) {
                    if (changer == '+') {
                        if (um.addMaster(botnick)) {
                            ircCommands.privmsg(nickFrom, "Master flag added for "+botnick, outbound);
                        }
                    }
                    else {
                        if (um.delMaster(botnick)) {
                            ircCommands.privmsg(nickFrom, "Master flag deleted for "+botnick, outbound);
                        }
                    }
                }
            }
        }
    }

    /** Handle adduser command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddUserCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddUserCmd() called");
        String botnick = params.get(1);
        if (um.userIsOwner(hostmask)) {
            if (um.addUser(botnick)) {
                ircCommands.privmsg(nickFrom, "User "+botnick+ " added", outbound);
            }
        }
    }

    /** Handle deluser command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelUserCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelUserCmd() called");
        String botnick = params.get(1);
        if (um.userIsOwner(hostmask)) {
            if (um.delUser(botnick)) {
                ircCommands.privmsg(nickFrom, "User "+botnick+ " deleted", outbound);
            }
        }
    }

    /** Handle users command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handleUsersCmd(String nickFrom, String hostmask) {
        log.info("handleUsersCmd() called");
        if (um.userIsOp(hostmask)) {
            Users users = um.getUsers();
            if (users.getUsers().isEmpty()) {
                ircCommands.privmsg(nickFrom, "User list empty", outbound);
            }
            else {
                UserPager pager = new UserPager(users.getUsers(),2);
                ArrayList userPage;
                User user;
                String msg;
                while (pager.hasNext()) {
                    userPage = pager.next();
                    for (Object o : userPage) {
                        user = (User) o;
                        msg = user.getNick() + " : " + user.getHostmask();
                        ircCommands.privmsg(nickFrom, msg, outbound);
                    }
                }
            }
        }
    }

    /** Handle addfriend command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddFriendCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddFriendCmd() called");
        String botnick = params.get(1);
        if (um.userIsOp(hostmask)) {
            if (um.addFriend(botnick)) {
                ircCommands.privmsg(nickFrom, "Friend flag added for "+botnick, outbound);
            }
        }
    }

    /** Handle addvoice command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddVoiceCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddVoiceCmd() called");
        String botnick = params.get(1);
        if (um.userIsOp(hostmask)) {
            if (um.addVoice(botnick)) {
                ircCommands.privmsg(nickFrom, "Voice flag added for "+botnick, outbound);
            }
        }
    }

    /** Handle addop command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddOpCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddOpCmd() called");
        String botnick = params.get(1);
        if (um.userIsMaster(hostmask)) {
            if (um.addOp(botnick)) {
                ircCommands.privmsg(nickFrom, "Op flag added for "+botnick, outbound);
            }
        }
    }

    /** Handle addmaster command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddMasterCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddMasterCmd() called");
        String botnick = params.get(1);
        if (um.userIsOwner(hostmask)) {
            if (um.addMaster(botnick)) {
                ircCommands.privmsg(nickFrom, "Master flag added for "+botnick, outbound);
            }
        }
    }

    /** Handle delfriend command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelFriendCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelFriendCmd() called");
        String botnick = params.get(1);
        if (um.userIsOp(hostmask)) {
            if (um.delFriend(botnick)) {
                ircCommands.privmsg(nickFrom, "Friend flag deleted for "+botnick, outbound);
            }
        }
    }

    /** Handle delvoice command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelVoiceCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelVoiceCmd() called");
        String botnick = params.get(1);
        if (um.userIsOp(hostmask)) {
            if (um.delVoice(botnick)) {
                ircCommands.privmsg(nickFrom, "Voice flag deleted for "+botnick, outbound);
            }
        }
    }

    /** Handle delop command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelOpCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelOpCmd() called");
        String botnick = params.get(1);
        if (um.userIsMaster(hostmask)) {
            if (um.delOp(botnick)) {
                ircCommands.privmsg(nickFrom, "Op flag deleted for "+botnick, outbound);
            }
        }
    }

    /** Handle delmaster command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelMasterCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelMasterCmd() called");
        String botnick = params.get(1);
        if (um.userIsOwner(hostmask)) {
            if (um.delMaster(botnick)) {
                ircCommands.privmsg(nickFrom, "Master flag deleted for "+botnick, outbound);
            }
        }
    }

    /** Handle kick command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleKickCmd(String hostmask, ArrayList<String> params) {
        log.info("handleKickCmd() called");
        String nickToKick = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            ircCommands.kick(chan, nickToKick, outbound);
        }
    }

    /** Handle ban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleBanCmd(String hostmask, ArrayList<String> params) {
        log.info("handleBanCmd() called");
        String banMask = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            ircCommands.ban(chan, banMask, outbound);
        }
    }

    /** Handle kickban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleKickBanCmd(String hostmask, ArrayList<String> params) {
        log.info("handleKickBanCmd() called");
        String nickToKick = params.get(1);
        String banMask = params.get(2);
        String chan = params.get(3);
        if (um.userIsOp(hostmask)) {
            ircCommands.ban(chan, banMask, outbound);
            ircCommands.kick(chan, nickToKick, outbound);
        }
    }

    /** Handle addban command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleAddBanCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleAddBanCmd() called");
        String banMask = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            if (bm.addBan(banMask)) {
                ircCommands.ban(chan, hostmask, outbound);
                ircCommands.kick(chan, nickFrom, outbound);
                ircCommands.privmsg(nickFrom, "Ban added : " + banMask, outbound);
            }
        }
    }

    /** Handle delban command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleDelBanCmd(String nickFrom, String hostmask, ArrayList<String> params) {
        log.info("handleDelBanCmd() called");
        String banMask = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            if (bm.delBan(banMask)) {
                ircCommands.unban(chan, banMask, outbound);
                ircCommands.privmsg(nickFrom, "Ban deleted : " + banMask, outbound);
            }
        }
    }

    /** Handle invite command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleInviteCmd(String hostmask, ArrayList<String> params) {
        log.info("handleInviteCmd() called");
        String nickname = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            ircCommands.invite(chan, nickname, outbound);
        }
    }

    /** Handle userfile command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handleUserfileCmd(String nickFrom, String hostmask) {
        log.info("handleUserfileCmd() called");
        if (um.userIsOwner(hostmask)) {
            um.reloadUsers();
            ircCommands.privmsg(nickFrom, "Userfile reloaded", outbound);
        }
    }

    /** Handle bans command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handleBansCmd(String nickFrom, String hostmask) {
        log.info("handleBansCmd() called");
        if (um.userIsOp(hostmask)) {
            Bans bans = bm.getBans();
            ArrayList<Ban> banList = bans.getBans();
            if (banList.isEmpty()) {
                ircCommands.privmsg(nickFrom, "Banlist empty", outbound);
            }
            else {
                BanPager pager = new BanPager(bans,2);
                ArrayList banPage;
                String msg;
                while (pager.hasNext()) {
                    banPage = pager.next();
                    for (Object s : banPage) {
                        if (s instanceof String) {
                            msg = (String)s;
                            ircCommands.privmsg(nickFrom, msg, outbound);
                        }
                        else log.warning("Object was not an instance of String");
                    }
                }
            }
        }
    }

    /** Handle unban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handleUnbanCmd(String hostmask, ArrayList<String> params) {
        log.info("handleUnbanCmd() called");
        String banMask = params.get(1);
        String chan = params.get(2);
        if (um.userIsOp(hostmask)) {
            ircCommands.unban(chan, banMask, outbound);
        }
    }

    /** Handle voiceme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handleVoiceMeCmd(String nickFrom, String hostmask) {
        log.info("handleVoiceMeCmd() called");
        if (!autovoice && um.userIsVoice(hostmask)) {
            //if (um.userIsVoice(hostmask)) {
            ircCommands.autovoice(channel, nickFrom, outbound);
        }
    }

    /** Handle opme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handleOpMeCmd(String nickFrom, String hostmask) {
        log.info("handleOpMeCmd() called with nick = " + nickFrom + " hostmask = " + hostmask);
        if (um.userIsOp(hostmask)) {
            log.info("user is an op");
            ircCommands.opme(channel, nickFrom, outbound);
        }
        else log.info("user is not an op");
    }

    /** Handle public voiceme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handlePubVoiceMeCmd(String nickFrom, String hostmask) {
        log.info("handlePubVoiceCmd() called");
        if (!autovoice && um.userIsVoice(hostmask)) {
            ircCommands.autovoice(channel,nickFrom,outbound);
        }
    }

    /** Handle public opme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */
    private void handlePubOpMeCmd(String nickFrom, String hostmask) {
        log.info("handlePubOpMeCmd() called");
        if (opme && um.userIsOp(hostmask)) {
            ircCommands.opme(channel,nickFrom,outbound);
        }
    }

    /** Handle public lc command
     * @param hostmask Originating hostmask
     */
    private void handlePubLcCmd(String hostmask) {
        log.info("handlePubLcCmd() called");
        if (um.userIsOp(hostmask)) {
            ircCommands.lockChannel(channel,outbound);
        }
    }

    /** Handle public uc command
     * @param hostmask Originating hostmask
     */
    private void handlePubUcCmd(String hostmask) {
        log.info("handlePubUcCmd() called");
        if (um.userIsOp(hostmask)) {
            ircCommands.unlockChannel(channel,outbound);
        }
    }

    /** Handle !<botnick> script command
     * @param channel Channel
     * @param nick Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */
    private void handlePubScriptCmd(String channel, String nick, String hostmask, ArrayList<String> params) {
        log.info("handlePubScriptCmd() called");
        // script commands are handled by the ScriptHandler
        sh.handlePublicCmd(channel, nick, hostmask, params);
    }

    /** Parses the command parameters from a message
     * @param params Command parameter string
     * @return ArrayList of command parameters
     */
    private ArrayList<String> parseCommand(String params) {
        log.info("parseCommand() called");
        ArrayList<String> v = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(params, " ");
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            v.add(token);
        }
        return v;
    }

    /** Parse the parameters out of a ArrayList of command
     * and parameters
     * @param cmd ArrayList of command and parameters
     * @return ArrayList containing just parameters
     */
    private ArrayList<String> parseParams(ArrayList<String> cmd) {
        log.info("parseParams() called");
        ArrayList<String> v = new ArrayList<>();
        if (cmd.size() > 1) {
            v.addAll(cmd.subList(1,cmd.size()));
        }
        return v;
    }

    protected void join() {
        cm.join();
    }

    public void setModes() {
        cm.setModes();
    }

    public void limitChannel() {
        if (dynamicLimit) {
            cm.limitChannel();
        }
    }

    public void getMessage(String responseLine) {
        MessageInterface messageT = mf.getMessage(responseLine);
        handleMessage(messageT);
    }
}
