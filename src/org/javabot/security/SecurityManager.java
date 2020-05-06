/*
 * SecurityManager.java - provides security management for JavaBot
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
import java.util.Vector;

public class SecurityManager implements MyObserver, org.javabot.util.MyObservable {
    
    public static final int CHAN_LIMIT = 0;
    public static final int FLOOD = 1;
    public static final int IGNORE = 2;
    public static final int SKIM_FLOODERS = 3;

    private Vector observers;

    private boolean debug = false;
    
    private int skimRate = 60*1000*5;  // 5 minutes
    private int ignoreTimeout = 60*1000; // 1 minute
    
    private java.util.Hashtable flooders;
    private Vector ignoreList;
    private java.util.Timer timer;
    
    private org.javabot.configuration.PropertyManager pm;

    public static String privmsgRatio;
    public static String chanmsgRatio;
    public static String ctcpRatio;
    public static String colourRatio;
    public static String dccRatio;

    /** Creates new SecurityManager */
    public SecurityManager() {
        if (debug) System.out.println("[SM] : Security Manager created");
        pm = org.javabot.configuration.PropertyManager.getInstance();
        SecurityManager.privmsgRatio = pm.getPrivmsgRatio();
        SecurityManager.chanmsgRatio = pm.getChanmsgRatio();
        SecurityManager.ctcpRatio = pm.getCtcpRatio();
        SecurityManager.colourRatio = pm.getColourRatio();
        SecurityManager.dccRatio = pm.getDccRatio();
        this.flooders = new java.util.Hashtable();
        this.ignoreList = new Vector();
        this.observers = new Vector();
        timer = new java.util.Timer(true);
        org.javabot.task.SkimmerTask st = new org.javabot.task.SkimmerTask();
        st.registerInterest(this);
        timer.scheduleAtFixedRate(st, this.skimRate, this.skimRate);  // skim the flooders every 5 mins
    }
    
    public void addFlooder(String hostmask) {
        if (debug) System.out.println("[SM] : addFlooder() called ... adding " + hostmask);
        this.flooders.put(hostmask, new FloodCounter());
    }
    
    public void removeFlooder(String hostmask) {
        if (debug) System.out.println("[SM] : removeFlooder() called ... removing " + hostmask);
        FloodCounter fc = (FloodCounter)flooders.get(hostmask);
        fc.kill();
        this.flooders.remove(hostmask);
    }
    
    public void addIgnore(String hostmask) {
        if (debug) System.out.println("[SM] : addIgnore() called");
        this.ignoreList.add(hostmask);
        org.javabot.task.IgnoreTask it = new org.javabot.task.IgnoreTask(hostmask);
        it.registerInterest(this);
        timer.schedule(it, this.ignoreTimeout);  // 60 seconds
    }
    
    public void removeIgnore(String hostmask) {
        if (debug) System.out.println("[SM] : removeIgnore() called");
        this.ignoreList.remove(hostmask);
    }
    
    public boolean isIgnored(String hostmask) {
        if (debug) System.out.println("[SM] : isIgnored() called");
        return this.ignoreList.contains(hostmask);
    }
    
    public void hitFloodCounter(int floodType, String hostmask) {
        if (debug) System.out.println("[SM] : hitFloodCounter() called : floodType = " + floodType + ", hostmask = " + hostmask);
        if (!flooders.containsKey(hostmask)) {
            this.addFlooder(hostmask);
        }
        int maxHits = this.getMaxHits(floodType);
        if (debug) System.out.println("[SM] : hitFloodCounter() maxHits contains " + maxHits);
        FloodCounter fc = (FloodCounter)flooders.get(hostmask);
        fc.increment(floodType);
        if (fc.get(floodType) >= maxHits) {
            fc.incrementFloods();
            if (fc.getFloods() >= 5) {
                if (debug) System.out.println("[SM] : hitFloodCounter() total floods >= 5");
                this.takeAction(FloodCounter.BAN, hostmask);
            }
            fc.reset(floodType);
            if (debug) System.out.println("[SM] : hitFloodCounter() floodCounter for floodType " + floodType + " >= " + maxHits);
            this.takeAction(floodType, hostmask);
        }
    }
    
    public int getMaxHits(int floodType) {
        if (debug) System.out.println("[SM] : getMaxHits() for floodType " + floodType);
        int maxHits = 0;
        if (floodType == FloodCounter.PRIVMSG) {
            maxHits = Integer.parseInt(SecurityManager.privmsgRatio.substring(0, SecurityManager.privmsgRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for privmsg = " + maxHits);
        }
        else if (floodType == FloodCounter.CHANMSG) {
            maxHits = Integer.parseInt(SecurityManager.chanmsgRatio.substring(0, SecurityManager.chanmsgRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for chanmsg = " + maxHits);
        }
        else if (floodType == FloodCounter.CTCP) {
            maxHits = Integer.parseInt(SecurityManager.ctcpRatio.substring(0, SecurityManager.ctcpRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for ctcp = " + maxHits);
        }
        else if (floodType == FloodCounter.COLOUR) {
            maxHits = Integer.parseInt(SecurityManager.colourRatio.substring(0, SecurityManager.colourRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for colour = " + maxHits);
        }
        else if (floodType == FloodCounter.DCC) {
            maxHits = Integer.parseInt(SecurityManager.dccRatio.substring(0, SecurityManager.dccRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for dcc = " + maxHits);
        }
        else if (floodType == FloodCounter.JOIN) {
            // maxHits = Integer.parseInt(SecurityManager.joinRatio.substring(0, SecurityManager.joinRatio.indexOf(":")));
            if (debug) System.out.println("[SM] : getMaxHits() maxHits for join = " + maxHits);
        }
        return maxHits;
    }

    private void takeAction(int floodType, String hostmask) {
        if (debug) System.out.println("[SM] : takeAction() for floodType = " + floodType + " against user " + hostmask);
        if (floodType == FloodCounter.BAN) {
            if (debug) System.out.println("[SM] : takeAction() multiple flood ... banning");
            this.ban(floodType, hostmask);
        }
        else if (floodType == FloodCounter.PRIVMSG) {
            if (debug) System.out.println("[SM] : takeAction() privmsg flood ... ignoring");
            this.addIgnore(hostmask);
        }
        else if (floodType == FloodCounter.CHANMSG) {
            if (debug) System.out.println("[SM] : takeAction() chanmsg flood ... kicking");
            this.kick(floodType, hostmask);
        }
    }
    
    private void kick(int floodType, String hostmask) {
        if (debug) System.out.println("[SM] : kick() for floodType = " + floodType + " against user " + hostmask);
        for (int i = 0; i < observers.size(); i++) {
            ((MyObserver)(observers.elementAt(i))).notifyEvent(org.javabot.security.SecurityManager.FLOOD, floodType, hostmask);
        }
    }
    
    private void ban(int floodType, String hostmask) {
        if (debug) System.out.println("[SM] : ban() for floodType = " + floodType + " against user " + hostmask);
        for (int i = 0; i < observers.size(); i++) {
            ((MyObserver)(observers.elementAt(i))).notifyEvent(org.javabot.security.SecurityManager.FLOOD, floodType, hostmask);
        }
    }
    
    private void skimFlooders() {
        if (debug) System.out.println("[SM] : skimFlooders() called");
        boolean skim;
        String hostmask;
        FloodCounter fc;
        java.util.Enumeration keys = flooders.keys();
        while (keys.hasMoreElements()) {
            skim = true;
            hostmask = (String)keys.nextElement();
            fc = (FloodCounter)flooders.get(hostmask);
            for (int i = FloodCounter.PRIVMSG; i <= FloodCounter.JOIN; i++) {
                if (fc.get(i) != 0) {
                    skim = false;
                }
            }
            if (skim) {
                this.removeFlooder(hostmask);
            }
        }
    }
    
    public void notifyEvent(int event) {
        if (event == org.javabot.security.SecurityManager.SKIM_FLOODERS) {
            this.skimFlooders();
        }
    }
    
    public void notifyEvent(int event, int type) {
    }
    
    public void notifyEvent(int event, int type, String message) {
        if (event == org.javabot.security.SecurityManager.IGNORE) {
            String hostmask = message;
            this.removeIgnore(hostmask);
        }
    }
    
    public void registerInterest(MyObserver observer) {
        observers.add(observer);
    }
    
}
