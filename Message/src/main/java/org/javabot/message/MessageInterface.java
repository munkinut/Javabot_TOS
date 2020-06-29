/*
 * MessageInterface.java - defines all methods for an irc message object
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

public interface MessageInterface {
    
    int getMessageType();
    String getNick();
    void setNick(String nick);
    String getHostmask();
    void setHostmask(String hostmask);
    String getChannel();
    void setChannel(String channel);
    String getCmd();
    void setCmd(String cmd);
    String getParams();
    void setParams(String params);
    String getMsgTo();
    void setMsgTo(String msgTo);
    String getChannelType();
    void setChannelType(String channelType);
    String getServer();
    void setServer(String server);
    String getNames();
    void setNames(String names);

}
