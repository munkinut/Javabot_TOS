/*
 * ChanLimitTask.java - extends TimerTask and implements MyObservable
 *                      to notify the ChannelManager when the channel
 *                      limit should be adjusted.
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

package org.javabot.security;

import org.javabot.util.MyObserver;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ChanLimitTask extends java.util.TimerTask implements org.javabot.util.MyObservable {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final ArrayList<MyObserver> observers;

    /** Creates new ChanLimitTask */
    public ChanLimitTask() {
        super();
        observers = new ArrayList<>();
    }
    
    public void run() {
        for (MyObserver observer : observers)
            observer.notifyEvent(SecurityManager.CHAN_LIMIT);
    }
    
    public void registerInterest(MyObserver observer) {
        observers.add(observer);
    }

}
