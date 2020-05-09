/*
 * NamesReplyMessage.java
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

public class NamesReplyMessage implements MessageInterface {

    Logger log = Logger.getLogger(this.getClass().getName());

    private String server;
    private String msgTo;
    private String channelType;
    private String channel;
    private String names;

    /** Creates new NamesReplyMessage */
    public NamesReplyMessage() {
        log.info("NamesReplyMessage() called");
    }

    public int getMessageType() {
        return MessageFactory.RPL_NAMREPLY;
    }
    
    public String getNick() {
        return null;
    }
    
    public void setNick(String nick) {
    }
    
    public String getHostmask() {
        return null;
    }
    
    public void setHostmask(String hostmask) {
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getCmd() {
        return null;
    }
    
    public void setCmd(String cmd) {
    }
    
    public String getParams() {
        return null;
    }
    
    public void setParams(String params) {
    }
    
    public String getMsgTo() {
        return this.msgTo;
    }
    
    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }
    
    public String getChannelType() {
        return this.channelType;
    }
    
    public String getNames() {
        return this.names;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    
    public void setNames(String names) {
        this.names = names;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
    
}
