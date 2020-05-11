/*
 * FloodCounter.java - counts various kinds of irc message
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

import org.javabot.task.FloodTask;
import org.javabot.security.SecurityManager;

import java.util.logging.Logger;

public class FloodCounter implements org.javabot.util.MyObserver {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final boolean debug = false;
    
    public static final int BAN = 0;
    public final static int PRIVMSG = 1;
    public final static int CHANMSG = 2;
    public final static int CTCP = 3;
    public final static int COLOUR = 4;
    public final static int DCC = 5;
    public final static int JOIN = 6;

    private int privmsg;
    private int chanmsg;
    private int ctcp;
    private int colour;
    private int dcc;
    private int join;
    
    private int floods;
    
    private final java.util.Timer timer;
    private final java.util.ArrayList<FloodTask> floodTasks;

    /** Creates new FloodCounter */
    public FloodCounter() {
        log.info("[FC] : FloodCounter() called");
        privmsg = 0;
        chanmsg = 0;
        ctcp = 0;
        colour = 0;
        dcc = 0;
        join = 0;
        floods = 0;
        this.timer = new java.util.Timer(true);
        this.floodTasks = new java.util.ArrayList<>();
        this.initFloodTasks();
    }
    
    private void initFloodTasks() {
        log.info("[FC] : initFloodTasks() called");
        this.addFloodTask(new FloodTask(FloodCounter.PRIVMSG));
        this.addFloodTask(new FloodTask(FloodCounter.CHANMSG));
    }
    
    private void addFloodTask(FloodTask floodTask) {
        log.info("[FC] : addFloodTask() called");
        int maxTime = this.getMaxTime(floodTask.getFloodType());
        timer.scheduleAtFixedRate(floodTask, maxTime, maxTime);
        floodTask.registerInterest(this);
        this.floodTasks.add(floodTask);
    }
    
    private int getMaxTime(int floodType) {
        log.info("[FC] : getMaxTime() called");
        int maxTime = 0;
        if (floodType == FloodCounter.PRIVMSG) {
            log.info("[FC] : getMaxTime() floodType = PRIVMSG");
            maxTime = Integer.parseInt(SecurityManager.privmsgRatio.substring(SecurityManager.privmsgRatio.indexOf(":")+1)) * 1000;
        }
        else if (floodType == FloodCounter.CHANMSG) {
            log.info("[FC] : getMaxTime() floodType = CHANMSG");
            maxTime = Integer.parseInt(SecurityManager.chanmsgRatio.substring(SecurityManager.chanmsgRatio.indexOf(":")+1)) * 1000;
        }
        else if (floodType == FloodCounter.CTCP) {
            log.info("[FC] : getMaxTime() floodType = CTCP");
            maxTime = Integer.parseInt(SecurityManager.ctcpRatio.substring(SecurityManager.ctcpRatio.indexOf(":")+1)) * 1000;
        }
        else if (floodType == FloodCounter.COLOUR) {
            log.info("[FC] : getMaxTime() floodType = COLOUR");
            maxTime = Integer.parseInt(SecurityManager.colourRatio.substring(SecurityManager.colourRatio.indexOf(":")+1)) * 1000;
        }
        else if (floodType == FloodCounter.DCC) {
            log.info("[FC] : getMaxTime() floodType = DCC");
            maxTime = Integer.parseInt(SecurityManager.dccRatio.substring(SecurityManager.dccRatio.indexOf(":")+1)) * 1000;
        }
        else if (floodType == FloodCounter.JOIN) {
            log.info("[FC] : getMaxTime() floodType = JOIN");
            // maxTime = Integer.parseInt(SecurityManager.joinRatio.substring(SecurityManager.joinRatio.indexOf(":")+1)) * 1000;
        }
        log.info("[FC] : getMaxTime() maxHits = " + maxTime);
        return maxTime;
    }
    
    public void increment(int floodType) {
        if (floodType == FloodCounter.PRIVMSG) privmsg++;
        else if (floodType == FloodCounter.CHANMSG) chanmsg++;
        else if (floodType == FloodCounter.CTCP) ctcp++;
        else if (floodType == FloodCounter.COLOUR) colour++;
        else if (floodType == FloodCounter.DCC) dcc++;
        else if (floodType == FloodCounter.JOIN) join++;
    }
    
    public void reset(int floodType) {
        if (floodType == FloodCounter.PRIVMSG) privmsg = 0;
        else if (floodType == FloodCounter.CHANMSG) chanmsg = 0;
        else if (floodType == FloodCounter.CTCP) ctcp = 0;
        else if (floodType == FloodCounter.COLOUR) colour = 0;
        else if (floodType == FloodCounter.DCC) dcc = 0;
        else if (floodType == FloodCounter.JOIN) join = 0;
    }
    
    public void resetAll() {
        privmsg = 0;
        chanmsg = 0;
        ctcp = 0;
        colour = 0;
        dcc = 0;
        join = 0;
    }
    
    public int get(int floodType) {
        int floodCount = 0;
        if (floodType == FloodCounter.PRIVMSG) floodCount = privmsg;
        else if (floodType == FloodCounter.CHANMSG) floodCount = chanmsg;
        else if (floodType == FloodCounter.CTCP) floodCount = ctcp;
        else if (floodType == FloodCounter.COLOUR) floodCount = colour;
        else if (floodType == FloodCounter.DCC) floodCount = dcc;
        else if (floodType == FloodCounter.JOIN) floodCount = join;
        return floodCount;
    }
    
    public void incrementFloods() {
        floods++;
    }
    
    public int getFloods() {
        return floods;
    }
    
    public void resetFloods() {
        floods = 0;
    }
    
    public void kill() {
        this.killFloodTasks();
        this.killTimer();
    }
    
    private void killFloodTasks() {
        log.info("[FC] : killFloodTasks() called");
        FloodTask floodTask;
        for (int i = 0; i < this.floodTasks.size(); i++) {
            floodTask = (FloodTask)this.floodTasks.get(i);
            floodTask.cancel();
            log.info("[FC] : killFloodTasks() floodTask " + i + " cancelled");
        }
        floodTasks.clear();
    }
    
    private void killTimer() {
        log.info("[FC] : killTimer() called");
        timer.cancel();
    }
    
    public int getPrivmsg() {
        return privmsg;
    }
    
    public int getChanmsg() {
        return chanmsg;
    }
    
    public int getCtcp() {
        return ctcp;
    }
    
    public int getColour() {
        return colour;
    }
    
    public int getDcc() {
        return dcc;
    }
    
    public int getJoin() {
        return join;
    }
    
    public void setPrivmsg(int privmsg) {
        this.privmsg = privmsg;
    }
    
    public void setChanmsg(int chanmsg) {
        this.chanmsg = chanmsg;
    }
    
    public void setCtcp(int ctcp) {
        this.ctcp = ctcp;
    }
    
    public void setColour(int colour) {
        this.colour = colour;
    }
    
    public void setDcc(int dcc) {
        this.dcc = dcc;
    }
    
    public void setJoin(int join) {
        this.join = join;
    }
    
    public void notifyEvent(int event) {
    }
    
    public void notifyEvent(int event, int type) {
        if (event == org.javabot.security.SecurityManager.FLOOD) {
            this.reset(type);
        }
    }
    
    public void notifyEvent(int event, int type, String message) {
    }
    
}
