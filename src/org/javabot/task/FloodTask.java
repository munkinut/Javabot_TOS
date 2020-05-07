/*
 * FloodTask.java - extends TimerTask and implements MyObservable
 *                  to notify observers (ChannelUsers) when they
 *                  can reset their flood counters.
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

package org.javabot.task;

import org.javabot.util.MyObservable;
import org.javabot.util.MyObserver;

import java.util.TimerTask;
import java.util.Vector;

public class FloodTask extends TimerTask implements MyObservable {
    
    private final Vector<MyObserver> observers;
    private final int floodType;

    /** Creates new ChanLimitTask */
    public FloodTask(int floodType) {
        super();
        observers = new Vector<>();
        this.floodType = floodType;
    }
    
    public int getFloodType() {
        return this.floodType;
    }
    
    public void run() {
        for (int i = 0; i < observers.size(); i++)
            observers.elementAt(i).notifyEvent(org.javabot.security.SecurityManager.FLOOD, this.floodType);
    }
    
    public void registerInterest(MyObserver observer) {
        observers.add(observer);
    }

}
