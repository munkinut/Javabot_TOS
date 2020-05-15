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
import java.util.logging.Logger;

public class SecurityManager implements MyObserver, org.javabot.util.MyObservable {

    final Logger log = Logger.getLogger(this.getClass().getName());

    public static final int CHAN_LIMIT = 0;
    public static final int FLOOD = 1;
    public static final int IGNORE = 2;
    public static final int SKIM_FLOODERS = 3;

    private final Vector<MyObserver> observers;

    private final boolean debug = false;

    private final java.util.Hashtable<String, FloodCounter> flooders;
    private final Vector<String> ignoreList;
    private final java.util.Timer timer;

    public static String privmsgRatio;
    public static String chanmsgRatio;
    public static String ctcpRatio;
    public static String colourRatio;
    public static String dccRatio;

    /** Creates new SecurityManager */
    public SecurityManager() {
        log.info("[SM] : Security Manager created");
        org.javabot.configuration.PropertyManagerApache pm = org.javabot.configuration.PropertyManagerApache.getInstance();
        SecurityManager.privmsgRatio = pm.getPrivmsgRatio();
        SecurityManager.chanmsgRatio = pm.getChanmsgRatio();
        SecurityManager.ctcpRatio = pm.getCtcpRatio();
        SecurityManager.colourRatio = pm.getColourRatio();
        SecurityManager.dccRatio = pm.getDccRatio();
        this.flooders = new java.util.Hashtable<>();
        this.ignoreList = new Vector<>();
        this.observers = new Vector<>();
        timer = new java.util.Timer(true);
        org.javabot.task.SkimmerTask st = new org.javabot.task.SkimmerTask();
        st.registerInterest(this);
        // 5 minutes
        int skimRate = 60 * 1000 * 5;
        timer.scheduleAtFixedRate(st, skimRate, skimRate);  // skim the flooders every 5 mins
    }
    
    public void addFlooder(String hostmask) {
        log.info("[SM] : addFlooder() called ... adding " + hostmask);
        this.flooders.put(hostmask, new FloodCounter());
    }
    
    public void removeFlooder(String hostmask) {
        log.info("[SM] : removeFlooder() called ... removing " + hostmask);
        FloodCounter fc = flooders.get(hostmask);
        fc.kill();
        this.flooders.remove(hostmask);
    }
    
    public void addIgnore(String hostmask) {
        log.info("[SM] : addIgnore() called");
        this.ignoreList.add(hostmask);
        org.javabot.task.IgnoreTask it = new org.javabot.task.IgnoreTask(hostmask);
        it.registerInterest(this);
        // 1 minute
        int ignoreTimeout = 60 * 1000;
        timer.schedule(it, ignoreTimeout);  // 60 seconds
    }
    
    public void removeIgnore(String hostmask) {
        log.info("[SM] : removeIgnore() called");
        this.ignoreList.remove(hostmask);
    }
    
    public boolean isIgnored(String hostmask) {
        log.info("[SM] : isIgnored() called");
        return this.ignoreList.contains(hostmask);
    }
    
    public void hitFloodCounter(int floodType, String hostmask) {
        log.info("[SM] : hitFloodCounter() called : floodType = " + floodType + ", hostmask = " + hostmask);
        if (!flooders.containsKey(hostmask)) {
            this.addFlooder(hostmask);
        }
        int maxHits = this.getMaxHits(floodType);
        log.info("[SM] : hitFloodCounter() maxHits contains " + maxHits);
        FloodCounter fc = flooders.get(hostmask);
        fc.increment(floodType);
        if (fc.get(floodType) >= maxHits) {
            fc.incrementFloods();
            if (fc.getFloods() >= 5) {
                log.info("[SM] : hitFloodCounter() total floods >= 5");
                this.takeAction(FloodCounter.BAN, hostmask);
            }
            fc.reset(floodType);
            log.info("[SM] : hitFloodCounter() floodCounter for floodType " + floodType + " >= " + maxHits);
            this.takeAction(floodType, hostmask);
        }
    }
    
    public int getMaxHits(int floodType) {
        log.info("[SM] : getMaxHits() for floodType " + floodType);
        int maxHits = 0;
        switch (floodType) {
            case FloodCounter.PRIVMSG:
                maxHits = Integer.parseInt(SecurityManager.privmsgRatio.substring(0, SecurityManager.privmsgRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for privmsg = " + maxHits);
                break;
            case FloodCounter.CHANMSG:
                maxHits = Integer.parseInt(SecurityManager.chanmsgRatio.substring(0, SecurityManager.chanmsgRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for chanmsg = " + maxHits);
                break;
            case FloodCounter.CTCP:
                maxHits = Integer.parseInt(SecurityManager.ctcpRatio.substring(0, SecurityManager.ctcpRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for ctcp = " + maxHits);
                break;
            case FloodCounter.COLOUR:
                maxHits = Integer.parseInt(SecurityManager.colourRatio.substring(0, SecurityManager.colourRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for colour = " + maxHits);
                break;
            case FloodCounter.DCC:
                maxHits = Integer.parseInt(SecurityManager.dccRatio.substring(0, SecurityManager.dccRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for dcc = " + maxHits);
                break;
            case FloodCounter.JOIN:
                // maxHits = Integer.parseInt(SecurityManager.joinRatio.substring(0, SecurityManager.joinRatio.indexOf(":")));
                log.info("[SM] : getMaxHits() maxHits for join = " + maxHits);
                break;
        }
        return maxHits;
    }

    private void takeAction(int floodType, String hostmask) {
        log.info("[SM] : takeAction() for floodType = " + floodType + " against user " + hostmask);
        switch (floodType) {
            case FloodCounter.BAN -> {
                log.info("[SM] : takeAction() multiple flood ... banning");
                this.ban(floodType, hostmask);
            }
            case FloodCounter.PRIVMSG -> {
                log.info("[SM] : takeAction() privmsg flood ... ignoring");
                this.addIgnore(hostmask);
            }
            case FloodCounter.CHANMSG -> {
                log.info("[SM] : takeAction() chanmsg flood ... kicking");
                this.kick(floodType, hostmask);
            }
            default -> throw new IllegalStateException("Unexpected value: " + floodType);
        }
    }
    
    private void kick(int floodType, String hostmask) {
        log.info("[SM] : kick() for floodType = " + floodType + " against user " + hostmask);
        for (int i = 0; i < observers.size(); i++) {
            observers.elementAt(i).notifyEvent(org.javabot.security.SecurityManager.FLOOD, floodType, hostmask);
        }
    }
    
    private void ban(int floodType, String hostmask) {
        log.info("[SM] : ban() for floodType = " + floodType + " against user " + hostmask);
        for (int i = 0; i < observers.size(); i++) {
            observers.elementAt(i).notifyEvent(org.javabot.security.SecurityManager.FLOOD, floodType, hostmask);
        }
    }
    
    private void skimFlooders() {
        log.info("[SM] : skimFlooders() called");
        boolean skim;
        String hostmask;
        FloodCounter fc;
        java.util.Enumeration keys = flooders.keys();
        while (keys.hasMoreElements()) {
            skim = true;
            hostmask = (String)keys.nextElement();
            fc = flooders.get(hostmask);
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
            this.removeIgnore(message);
        }
    }
    
    public void registerInterest(MyObserver observer) {
        observers.add(observer);
    }
    
}
