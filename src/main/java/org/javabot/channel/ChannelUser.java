/*
 * ChannelUser.java - representation of an irc channel user
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

import java.util.logging.Logger;

/** Represents a user in a channel.
 */
public class ChannelUser {

    final Logger log = Logger.getLogger(this.getClass().getName());

    /** Debug flag.
     */    
    private final boolean debug = false;
    
    /** User nickname.
     */    
    private String nick;
    /** Ignore flag.
     */    
    private boolean ignore;

    /** Creates new ChannelUser.
     * @param nick User nickname.
     */
    public ChannelUser(String nick) {
        log.info("ChannelUser() called for user " + nick);
        this.nick = nick;
        this.ignore = false;
    }
    
    /** Get the ignore status of the user.
     * @return Ignore status.
     */    
    public boolean getIgnore() {
        return ignore;
    }
    
    /** Sets ignore status of the user.
     * @param ignore Ignore status of the user.
     */    
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
    
    /** Set user nickname.
     * @param nick User nickname.
     */    
    public void setNick(String nick) {
        log.info("setNick() called with nick = " + nick);
        this.nick = nick;
    }
    
    /** Get user nickname.
     * @return User nickname.
     */    
    public String getNick() {
        log.info("getNick() called");
        return this.nick;
    }
    
}
