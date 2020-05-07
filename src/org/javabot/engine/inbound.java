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

import java.util.Vector;
import java.util.StringTokenizer;

/** Inbound message handler
 */
public class inbound extends Thread {
    
    /** Debug flag
     */    
    private final boolean debug = false;

    /** Indicates connection status
     */    
    private boolean connected = false;

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
    
    /** Network socket to the server
     */    
    private final java.net.Socket ircsocket;
    /** Output stream to the server
     */    
    private java.io.DataOutputStream outbound;
    /** Input stream from server
     */    
    private java.io.BufferedReader inbound;
    /** Console for output messages
     */    
    private final javax.swing.JTextArea consoleOutput;

    /** Manages bot users
     */    
    private final org.javabot.user.UserManager um;
    /** Manages the channel
     */    
    private org.javabot.channel.ChannelManager cm;
    /** Manages bot security
     */    
    private final org.javabot.security.SecurityManager sm;
    /** Manages the ban file
     */    
    private final org.javabot.security.BanManager bm;
    /** Parses message strings into message objects
     */    
    private final org.javabot.message.MessageFactory mf;
    /** Handles script commands
     */
    private org.javabot.script.ScriptHandler sh;

    /** Creates an inbound handler thread
     * @param ircsocket Socket connection to server
     * @param consoleOutput Console for output messages
     */    
    public inbound(java.net.Socket ircsocket, javax.swing.JTextArea consoleOutput){
        this.ircsocket = ircsocket;
        this.consoleOutput = consoleOutput;
        /** Manages the properties file
         */
        org.javabot.configuration.PropertyManager pm = org.javabot.configuration.PropertyManager.getInstance();
        /** Timer for timed events
         */
        java.util.Timer timer = new java.util.Timer(true);
        this.name = pm.getName();
        this.nick = pm.getNickname();
        this.channel = pm.getChannel();
        /** Channel modes
         */
        String channelModes = pm.getChannelModes();
        this.autovoice = pm.getAutovoice();
        /** Flood protection flag
         */
        boolean flood = pm.getFloodProtection();
        this.opme = pm.getOpme();
        this.autogreet = pm.getAutogreet();
        this.dynamicLimit = pm.getDynamicLimit();
        this.um = new org.javabot.user.UserManager();
        this.sm = new org.javabot.security.SecurityManager();
        this.bm = new org.javabot.security.BanManager();
        this.mf = new org.javabot.message.MessageFactory();
    }

    /** Closes connections
     */    
    public void quit() {
        if (outbound != null) {
            cm.removeAllChannelUsers();
            cm.killTimer();
            IRCCommands.quit(outbound, "Boing Boing!");
            if (this.connected) this.connected = false;
        }
    }
    
    /** Connect to the server
     * @return Success flag
     */    
    public boolean connect() {
        if (!this.connected) {
            try {
                outbound = new java.io.DataOutputStream(ircsocket.getOutputStream());
                inbound = new java.io.BufferedReader(
                    new java.io.InputStreamReader(
                        new java.io.DataInputStream(
                            ircsocket.getInputStream()
                        )
                    )
                );
                
                if ((outbound != null) && (inbound != null)) {
                    this.cm = new org.javabot.channel.ChannelManager(outbound);
                    this.sh = new org.javabot.script.ScriptHandler(outbound);
                    this.sm.registerInterest(cm);
                    IRCCommands.identify(name, nick, outbound);
                
                    int go = 0;
                    String ping;
                    String responseLine;
                    String lineToWrite;
                    for(int i=0;i<6;i++){
                        lineToWrite = inbound.readLine() + "\n";
                        consoleOutput.append(lineToWrite);
                        consoleOutput.setCaretPosition(consoleOutput.getCaretPosition() + lineToWrite.length());
                    
                        if(inbound.readLine().equals("NOTICE AUTH :*** No ident response")){
                            go++;
                        }
                    
                        if(go==1){
                            ping = inbound.readLine();
                            String quote = ping.substring(6);
                            String all = "pong "+quote+"\n";
                            IRCCommands.writeBytes(all, outbound);
                        }
                    }
                    this.connected = true;
                }
            }
            catch (java.io.IOException ioe){
                this.connected = false;
            }
        }
        return this.connected;
    }
    
    /** Handle server messages
     */    
    public void run(){
        
        int timeLimit = 0;
        
        if (this.connect()) {
            try {
                cm.join();
                cm.setModes();
                
                if (dynamicLimit) {
                    cm.limitChannel();
                }
                
                String responseLine;
                String lineToWrite;
                
                while ((responseLine = inbound.readLine()) != null) {
                    lineToWrite = responseLine + "\n";
                    consoleOutput.append(lineToWrite);
                    consoleOutput.setCaretPosition(consoleOutput.getCaretPosition() + lineToWrite.length());
                    
                    org.javabot.message.MessageInterface messageT = mf.getMessage(responseLine);
                    this.handleMessage(messageT);
                    
                }
            }
            catch (java.io.IOException ignored){
            }
        }
    }
    
    /** Generic message handler; examines messages and
     * delegates to the appropriate handler
     * @param messageT Message to examine
     */    
    private void handleMessage(org.javabot.message.MessageInterface messageT) {
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
        String params = pingMessage.getParams();
        IRCCommands.pingpong(params, outbound);
    }
    
    /** Handle join message
     * @param joinMessage Join message
     */    
    private void handleJoin(org.javabot.message.MessageInterface joinMessage) {
        String nickFrom = joinMessage.getNick();
        String hostmask = joinMessage.getHostmask();
        String chan = joinMessage.getChannel();
        String fullMask = nickFrom + "!" + hostmask;
        if (bm.matches(fullMask)) {
            IRCCommands.ban(chan, hostmask, outbound);
            IRCCommands.kick(chan, nickFrom, outbound);
        }
        else {
            cm.addChannelUser(hostmask, nickFrom);
            if (!nickFrom.equals(nick)) {
                IRCCommands.names(chan, outbound);
            }
            if (autovoice && um.userIsVoice(hostmask)) IRCCommands.autovoice(chan, nickFrom, outbound);
            if (autogreet) {
                String greet = um.getGreet(hostmask);
                if (greet != null) {
                    IRCCommands.playGreet(chan, outbound, greet);
                }
            }
        }
    }

    /** Handle part message
     * @param partMessage Part message
     */    
    private void handlePart(org.javabot.message.MessageInterface partMessage) {
        String chan = partMessage.getChannel();
        String hostmask = partMessage.getHostmask();
        cm.removeChannelUser(hostmask);
        IRCCommands.names(chan, outbound);
    }

    /** Handle quit message
     * @param quitMessage Quit message
     */    
    private void handleQuit(org.javabot.message.MessageInterface quitMessage) {
        String chan = quitMessage.getChannel();
        String hostmask = quitMessage.getHostmask();
        cm.removeChannelUser(hostmask);
        IRCCommands.names(chan, outbound);
    }
    
    /** Handle kick message
     * @param kickMessage Kick message
     */    
    private void handleKick(org.javabot.message.MessageInterface kickMessage) {
        String chan = kickMessage.getChannel();
        String hostmask = kickMessage.getHostmask();
        String msgTo = kickMessage.getMsgTo();
        cm.removeChannelUserByNick(msgTo);
        IRCCommands.names(chan, outbound);
    }

    /** Handle namesreply message
     * @param namesReplyMessage Namesreply message
     */    
    private void handleNamesReply(org.javabot.message.MessageInterface namesReplyMessage) {
        String names = namesReplyMessage.getNames();
        StringTokenizer st = new StringTokenizer(names, " ");
        cm.setNamesCount(st.countTokens());
        if (st.countTokens() == 1) {
            String token = st.nextToken();
            if (!token.startsWith("@") && (token.equals(nick) || token.equals("+" + nick))) {
                IRCCommands.cycle(channel, outbound);
            }
        }
    }
    
    /** Handle privmg message
     * @param privmsgMessage Privmsg message
     */    
    private void handlePrivmsg(org.javabot.message.MessageInterface privmsgMessage) {
        String nickFrom = privmsgMessage.getNick();
        String hostmask = privmsgMessage.getHostmask();
        String msgTo = privmsgMessage.getMsgTo();
        
        if ((msgTo.equals(nick)) && (!nickFrom.equals(nick))) sm.hitFloodCounter(org.javabot.security.FloodCounter.PRIVMSG, hostmask);
        else if ((msgTo.equals(channel)) && (!nickFrom.equals(nick))) sm.hitFloodCounter(org.javabot.security.FloodCounter.CHANMSG, hostmask);
        
        if (!sm.isIgnored(hostmask)) {
            
            Vector params = this.parseCommand(privmsgMessage.getParams());
            if (!params.isEmpty()) {
                String command = (String)params.elementAt(0);
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
    private void handlePrivateCmd(String command, String nickFrom, String msgTo, String hostmask, Vector params) {
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
    private void handlePublicCmd(String command, String nickFrom, String msgTo, String hostmask, Vector params) {
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
            Vector cmdParams = this.parseParams(params);
            this.handlePubScriptCmd(channel, nickFrom, hostmask, cmdParams);
        }
    }
    
    /** Handle auth command
     * @param nickFrom The originating nick
     * @param hostmask The originating hostmask
     * @param params Command parameters
     */    
    private void handleAuthCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        String botpass = (String)params.elementAt(2);
        if (um.auth(botnick, hostmask, botpass)) {
            IRCCommands.privmsg(nickFrom, "User " + botnick + " authorised", outbound);
            if (autovoice && um.userIsVoice(hostmask)) IRCCommands.autovoice(channel, nickFrom, outbound);
            if (autogreet) {
                String greet = um.getGreet(hostmask);
                if (greet != null) {
                    IRCCommands.playGreet(channel, outbound, greet);
                }
            }
        }
    }

    /** Handle pass command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handlePassCmd(String nickFrom, String hostmask, Vector params) {
        String oldpass = (String)params.elementAt(1);
        String newpass = (String)params.elementAt(2);
        if (um.pass(hostmask, oldpass, newpass)) {
            IRCCommands.privmsg(nickFrom, "Password changed for "+nickFrom+" ("+hostmask+")", outbound);
        }
    }
    
    /** Handle greet command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleGreetCmd(String nickFrom, String hostmask, Vector params) {
        StringBuilder greet = new StringBuilder();
        for (int i=1; i < params.size(); i++) {
            greet.append(params.elementAt(i)).append(" ");
        }
        greet = new StringBuilder(greet.toString().trim());
        if (um.userIsFriend(hostmask)) {
            if (um.greet(hostmask, greet.toString())) {
                IRCCommands.privmsg(nickFrom, "Greet changed to '"+ greet + "'", outbound);
            }
        }
    }
    
    /** Handle flags command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleFlagsCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        String flags = (String)params.elementAt(2);
        char changer = flags.charAt(0);
        if ((changer == '+' || changer == '-') && flags.length() > 1) {
            for (int i = 1;i < flags.length(); i++) {
                char flag = flags.charAt(i);
                if (flag == 'f' && um.userIsOp(hostmask)) {
                    if (changer == '+') {
                        if (um.addFriend(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Friend flag added for "+botnick, outbound);
                        }
                    }
                    else if (changer == '-') {
                        if (um.delFriend(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Friend flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'v' && um.userIsOp(hostmask)) {
                    if (changer == '+') {
                        if (um.addVoice(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Voice flag added for "+botnick, outbound);
                        }
                    }
                    else if (changer == '-') {
                        if (um.delVoice(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Voice flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'o' && um.userIsOwner(hostmask)) {
                    if (changer == '+') {
                        if (um.addOp(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Op flag added for "+botnick, outbound);
                        }
                    }
                    else if (changer == '-') {
                        if (um.delOp(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Op flag deleted for "+botnick, outbound);
                        }
                    }
                }
                if (flag == 'm' && um.userIsOwner(hostmask)) {
                    if (changer == '+') {
                        if (um.addMaster(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Master flag added for "+botnick, outbound);
                        }
                    }
                    else if (changer == '-') {
                        if (um.delMaster(botnick)) {
                            IRCCommands.privmsg(nickFrom, "Master flag deleted for "+botnick, outbound);
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
    private void handleAddUserCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOwner(hostmask)) {
            if (um.addUser(botnick)) {
                IRCCommands.privmsg(nickFrom, "User "+botnick+ " added", outbound);
            }
        }
    }
    
    /** Handle deluser command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelUserCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOwner(hostmask)) {
            if (um.delUser(botnick)) {
                IRCCommands.privmsg(nickFrom, "User "+botnick+ " deleted", outbound);
            }
        }
    }
    
    /** Handle users command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    @SuppressWarnings("BusyWait")
    private void handleUsersCmd(String nickFrom, String hostmask) {
        if (um.userIsOp(hostmask)) {
            Vector users = um.getUsers();
            if (users.isEmpty()) {
                IRCCommands.privmsg(nickFrom, "User list empty", outbound);
            }
            else {
                org.javabot.util.Pager pager = new org.javabot.util.Pager(users,2);
                Vector userPage;
                org.javabot.user.User user;
                String msg;
                while (pager.hasNext()) {
                    userPage = pager.next();
                    for (int i = 0; i < userPage.size(); i++) {
                        user = (org.javabot.user.User)userPage.elementAt(i);
                        msg = user.getNick() + " : " + user.getHostmask();
                        IRCCommands.privmsg(nickFrom, msg, outbound);
                    }
                    try {
                        sleep(1000);
                    }
                    catch (InterruptedException ignored) {
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
    private void handleAddFriendCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOp(hostmask)) {
            if (um.addFriend(botnick)) {
                IRCCommands.privmsg(nickFrom, "Friend flag added for "+botnick, outbound);
            }
        }
    }
    
    /** Handle addvoice command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleAddVoiceCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOp(hostmask)) {
            if (um.addVoice(botnick)) {
                IRCCommands.privmsg(nickFrom, "Voice flag added for "+botnick, outbound);
            }
        }
    }
    
    /** Handle addop command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleAddOpCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsMaster(hostmask)) {
            if (um.addOp(botnick)) {
                IRCCommands.privmsg(nickFrom, "Op flag added for "+botnick, outbound);
            }
        }
    }
    
    /** Handle addmaster command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleAddMasterCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOwner(hostmask)) {
            if (um.addMaster(botnick)) {
                IRCCommands.privmsg(nickFrom, "Master flag added for "+botnick, outbound);
            }
        }
    }
    
    /** Handle delfriend command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelFriendCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOp(hostmask)) {
            if (um.delFriend(botnick)) {
                IRCCommands.privmsg(nickFrom, "Friend flag deleted for "+botnick, outbound);
            }
        }
    }
    
    /** Handle delvoice command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelVoiceCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOp(hostmask)) {
            if (um.delVoice(botnick)) {
                IRCCommands.privmsg(nickFrom, "Voice flag deleted for "+botnick, outbound);
            }
        }
    }
    
    /** Handle delop command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelOpCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsMaster(hostmask)) {
            if (um.delOp(botnick)) {
                IRCCommands.privmsg(nickFrom, "Op flag deleted for "+botnick, outbound);
            }
        }
    }
    
    /** Handle delmaster command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelMasterCmd(String nickFrom, String hostmask, Vector params) {
        String botnick = (String)params.elementAt(1);
        if (um.userIsOwner(hostmask)) {
            if (um.delMaster(botnick)) {
                IRCCommands.privmsg(nickFrom, "Master flag deleted for "+botnick, outbound);
            }
        }
    }
    
    /** Handle kick command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleKickCmd(String hostmask, Vector params) {
        String nickToKick = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            IRCCommands.kick(chan, nickToKick, outbound);
        }
    }
    
    /** Handle ban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleBanCmd(String hostmask, Vector params) {
        String banMask = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            IRCCommands.ban(chan, banMask, outbound);
        }
    }
    
    /** Handle kickban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleKickBanCmd(String hostmask, Vector params) {
        String nickToKick = (String)params.elementAt(1);
        String banMask = (String)params.elementAt(2);
        String chan = (String)params.elementAt(3);
        if (um.userIsOp(hostmask)) {
            IRCCommands.ban(chan, banMask, outbound);
            IRCCommands.kick(chan, nickToKick, outbound);
        }
    }
    
    /** Handle addban command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleAddBanCmd(String nickFrom, String hostmask, Vector params) {
        String banMask = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            if (bm.addBan(banMask)) {
                IRCCommands.ban(chan, hostmask, outbound);
                IRCCommands.kick(chan, nickFrom, outbound);
                IRCCommands.privmsg(nickFrom, "Ban added : " + banMask, outbound);
            }
        }
    }
    
    /** Handle delban command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleDelBanCmd(String nickFrom, String hostmask, Vector params) {
        String banMask = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            if (bm.delBan(banMask)) {
                IRCCommands.unban(chan, banMask, outbound);
                IRCCommands.privmsg(nickFrom, "Ban deleted : " + banMask, outbound);
            }
        }
    }
    
    /** Handle invite command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleInviteCmd(String hostmask, Vector params) {
        String nickname = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            IRCCommands.invite(chan, nickname, outbound);
        }
    }
    
    /** Handle userfile command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    private void handleUserfileCmd(String nickFrom, String hostmask) {
        if (um.userIsOwner(hostmask)) {
            um.reloadUsers();
            IRCCommands.privmsg(nickFrom, "Userfile reloaded", outbound);
        }
    }
    
    /** Handle bans command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    @SuppressWarnings("BusyWait")
    private void handleBansCmd(String nickFrom, String hostmask) {
        if (um.userIsOp(hostmask)) {
            Vector bans = bm.getBans();
            if (bans.isEmpty()) {
                IRCCommands.privmsg(nickFrom, "Banlist empty", outbound);
            }
            else {
                org.javabot.util.Pager pager = new org.javabot.util.Pager(bans,2);
                Vector banPage;
                String msg;
                while (pager.hasNext()) {
                    banPage = pager.next();
                    for (int i = 0; i < banPage.size(); i++) {
                        msg = (String)banPage.elementAt(i);
                        IRCCommands.privmsg(nickFrom, msg, outbound);
                    }
                    try {
                        sleep(1000);
                    }
                    catch (InterruptedException ignored) {
                    }
                }
            }
        }
    }
    
    /** Handle unban command
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handleUnbanCmd(String hostmask, Vector params) {
        String banMask = (String)params.elementAt(1);
        String chan = (String)params.elementAt(2);
        if (um.userIsOp(hostmask)) {
            IRCCommands.unban(chan, banMask, outbound);
        }
    }
    
    /** Handle voiceme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    private void handleVoiceMeCmd(String nickFrom, String hostmask) {
        if (!autovoice && um.userIsVoice(hostmask)) {
            IRCCommands.autovoice(channel, nickFrom, outbound);
        }
    }
    
    /** Handle opme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    private void handleOpMeCmd(String nickFrom, String hostmask) {
        if (um.userIsOp(hostmask)) {
            IRCCommands.opme(channel, nickFrom, outbound);
        }
    }
    
    /** Handle public voiceme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    private void handlePubVoiceMeCmd(String nickFrom, String hostmask) {
        if (!autovoice && um.userIsVoice(hostmask)) {
            IRCCommands.autovoice(channel,nickFrom,outbound);
        }
    }
    
    /** Handle public opme command
     * @param nickFrom Originating nick
     * @param hostmask Originating hostmask
     */    
    private void handlePubOpMeCmd(String nickFrom, String hostmask) {
        if (opme && um.userIsOp(hostmask)) {
            IRCCommands.opme(channel,nickFrom,outbound);
        }
    }
    
    /** Handle public lc command
     * @param hostmask Originating hostmask
     */    
    private void handlePubLcCmd(String hostmask) {
        if (um.userIsOp(hostmask)) {
            IRCCommands.lockChannel(channel,outbound);
        }
    }
    
    /** Handle public uc command
     * @param hostmask Originating hostmask
     */    
    private void handlePubUcCmd(String hostmask) {
        if (um.userIsOp(hostmask)) {
            IRCCommands.unlockChannel(channel,outbound);
        }
    }
    
    /** Handle !<botnick> script command
     * @param channel Channel
     * @param nick Originating nick
     * @param hostmask Originating hostmask
     * @param params Command parameters
     */    
    private void handlePubScriptCmd(String channel, String nick, String hostmask, Vector params) {
        // script commands are handled by the ScriptHandler
        sh.handlePublicCmd(channel, nick, hostmask, params);
    }

    /** Parses the command parameters from a message
     * @param params Command parameter string
     * @return Vector of command parameters
     */    
    private Vector parseCommand(String params) {
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(params, " ");
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            v.add(token);
        }
        return v;
    }

    /** Parse the parameters out of a Vector of command
     * and parameters
     * @param cmd Vector of command and parameters
     * @return Vector containing just parameters
     */    
    private Vector parseParams(Vector cmd) {
        Vector v = new Vector();
        if (cmd.size() > 1) {
            v.addAll(cmd.subList(1,cmd.size()));
        }
        return v;
    }

}
