/*
 * SkimmerTask.java - extends TimerTask and implements MyObservable
 *                    to notify the SecurityManager when the list
 *                    of flooders should be skimmed.
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

import org.javabot.util.MyObserver;

import java.util.logging.Logger;

public class SkimmerTask extends java.util.TimerTask implements org.javabot.util.MyObservable {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final java.util.ArrayList<MyObserver> observers;

    /** Creates new ChanLimitTask */
    public SkimmerTask() {
        super();
        observers = new java.util.ArrayList<>();
    }
    
    public void run() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).notifyEvent(org.javabot.security.SecurityManager.SKIM_FLOODERS);
        }
    }
    
    public void registerInterest(org.javabot.util.MyObserver observer) {
        observers.add(observer);
    }

}
