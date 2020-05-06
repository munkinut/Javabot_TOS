/*
 * PrivmsgMessage.java
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

public class PrivmsgMessage extends Object implements MessageInterface {
    
    private String nick;
    private String hostmask;
    private String msgTo;
    private String params;

    /** Creates new PrivmsgMessage */
    public PrivmsgMessage() {
    }

    public int getMessageType() {
        return MessageFactory.PRIVMSG;
    }
    
    public String getNick() {
        return this.nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public String getHostmask() {
        return this.hostmask;
    }
    
    public void setHostmask(String hostmask) {
        this.hostmask = hostmask;
    }
    
    public String getChannel() {
        return null;
    }
    
    public void setChannel(String channel) {
    }
    
    public String getCmd() {
        return null;
    }
    
    public void setCmd(String cmd) {
    }
    
    public String getParams() {
        return this.params;
    }
    
    public void setParams(String params) {
        this.params = params;
    }
    
    public String getChannelType() {
        return null;
    }
    
    public void setChannelType(String channelType) {
    }
    
    public String getMsgTo() {
        return this.msgTo;
    }
    
    public String getNames() {
        return null;
    }
    
    public String getServer() {
        return null;
    }
    
    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }
    
    public void setNames(String names) {
    }
    
    public void setServer(String server) {
    }
    
}
