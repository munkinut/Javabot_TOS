/*
 * Configurator.java - provides the model for JavaBot ConfigUI
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

package org.javabot.gui;

public class Configurator {
    
    private final org.javabot.configuration.PropertyManager pm;
    
    private String server;
    private int port;
    private String name;
    private String nickname;
    private String channel;
    private String channelModes;
    
    private boolean autovoice;
    private boolean autogreet;
    private boolean floodProtection;
    private boolean opme;
    private boolean cycleForOps;
    private boolean dynamicLimit;
    
    private int joinHits;
    private int joinLimit;
    private int ctcpHits;
    private int ctcpLimit;
    private int privmsgHits;
    private int privmsgLimit;
    private int chanmsgHits;
    private int chanmsgLimit;
    private int colourHits;
    private int colourLimit;
    private int dccHits;
    private int dccLimit;

    /** Creates new Configurator */
    public Configurator() {
        pm = org.javabot.configuration.PropertyManager.getInstance();
        this.load();
    }
    
    private int getMaxHits(String ratio) {
        return Integer.parseInt(ratio.substring(0, ratio.indexOf(":")));
    }
    
    private int getMaxLimit(String ratio) {
        return Integer.parseInt(ratio.substring(ratio.indexOf(":")+1));
    }
    
    public String getServer() {
        return this.server;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getNickname() {
        return this.nickname;
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public String getChannelModes() {
        return this.channelModes;
    }
    
    public boolean getAutovoice() {
        return this.autovoice;
    }
    
    public boolean getAutogreet() {
        return this.autogreet;
    }
    
    public boolean getFloodProtection() {
        return this.floodProtection;
    }
    
    public boolean getOpme() {
        return this.opme;
    }
    
    public boolean getCycleForOps() {
        return this.cycleForOps;
    }
    
    public boolean getDynamicLimit() {
        return this.dynamicLimit;
    }
    
    public int getJoinHits() {
        return this.joinHits;
    }
    
    public int getCtcpHits() {
        return this.ctcpHits;
    }
    
    public int getPrivmsgHits() {
        return this.privmsgHits;
    }
    
    public int getChanmsgHits() {
        return this.chanmsgHits;
    }
    
    public int getColourHits() {
        return this.colourHits;
    }
    
    public int getDccHits() {
        return this.dccHits;
    }
    
    public int getJoinLimit() {
        return this.joinLimit;
    }
    
    public int getCtcpLimit() {
        return this.ctcpLimit;
    }
    
    public int getPrivmsgLimit() {
        return this.privmsgLimit;
    }
    
    public int getChanmsgLimit() {
        return this.chanmsgLimit;
    }
    
    public int getColourLimit() {
        return this.colourLimit;
    }
    
    public int getDccLimit() {
        return this.dccLimit;
    }
    
    public void setJoinHits(int joinHits) {
        this.joinHits = joinHits;
    }
    
    public void setCtcpHits(int ctcpHits) {
        this.ctcpHits = ctcpHits;
    }
    
    public void setPrivmsgHits(int privmsgHits) {
        this.privmsgHits = privmsgHits;
    }
    
    public void setChanmsgHits(int chanmsgHits) {
        this.chanmsgHits = chanmsgHits;
    }
    
    public void setColourHits(int colourHits) {
        this.colourHits = colourHits;
    }
    
    public void setDccHits(int dccHits) {
        this.dccHits = dccHits;
    }
    
    public void setJoinLimit(int joinLimit) {
        this.joinLimit = joinLimit;
    }
    
    public void setCtcpLimit(int ctcpLimit) {
        this.ctcpLimit = ctcpLimit;
    }
    
    public void setPrivmsgLimit(int privmsgLimit) {
        this.privmsgLimit = privmsgLimit;
    }
    
    public void setChanmsgLimit(int chanmsgLimit) {
        this.chanmsgLimit = chanmsgLimit;
    }
    
    public void setColourLimit(int colourLimit) {
        this.colourLimit = colourLimit;
    }
    
    public void setDccLimit(int dccLimit) {
        this.dccLimit = dccLimit;
    }
    
    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setChannelModes(String channelModes) {
        this.channelModes = channelModes;
    }
    
    public void setAutovoice(boolean autovoice) {
        this.autovoice = autovoice;
    }
    
    public void setAutogreet(boolean autogreet) {
        this.autogreet = autogreet;
    }
    
    public void setFloodProtection(boolean floodProtection) {
        this.floodProtection = floodProtection;
    }
    
    public void setOpme(boolean opme) {
        this.opme = opme;
    }
    
    public void setCycleForOps(boolean cycleForOps) {
        this.cycleForOps = cycleForOps;
    }
    
    public void setDynamicLimit(boolean dynamicLimit) {
        this.dynamicLimit = dynamicLimit;
    }
    
    public void store() {
        pm.setServer(this.getServer());
        pm.setPort(this.getPort());
        pm.setName(this.getName());
        pm.setNickname(this.getNickname());
        pm.setChannel(this.getChannel());
        pm.setChannelModes(this.getChannelModes());
        pm.setAutovoice(this.getAutovoice());
        pm.setAutogreet(this.getAutogreet());
        pm.setFloodProtection(this.getFloodProtection());
        pm.setOpme(this.getOpme());
        pm.setCycleForOps(this.getCycleForOps());
        pm.setDynamicLimit(this.getDynamicLimit());
        pm.setJoinRatio(this.getJoinHits() + ":" + this.getJoinLimit());
        pm.setCtcpRatio(this.getCtcpHits() + ":" + this.getCtcpLimit());
        pm.setPrivmsgRatio(this.getPrivmsgHits() + ":" + this.getPrivmsgLimit());
        pm.setChanmsgRatio(this.getChanmsgHits() + ":" + this.getChanmsgLimit());
        pm.setColourRatio(this.getColourHits() + ":" + this.getColourLimit());
        pm.setDccRatio(this.getDccHits() + ":" + this.getDccLimit());
        pm.writeProperties();
    }

    public void load() {
        server = pm.getServer();
        port = pm.getPort();
        name = pm.getName();
        nickname = pm.getNickname();
        channel = pm.getChannel();
        channelModes = pm.getChannelModes();
        autovoice = pm.getAutovoice();
        autogreet = pm.getAutogreet();
        floodProtection = pm.getFloodProtection();
        opme = pm.getOpme();
        cycleForOps = pm.getCycleForOps();
        dynamicLimit = pm.getDynamicLimit();
        String joinRatio = pm.getJoinRatio();
        String ctcpRatio = pm.getCtcpRatio();
        String privmsgRatio = pm.getPrivmsgRatio();
        String chanmsgRatio = pm.getChanmsgRatio();
        String colourRatio = pm.getColourRatio();
        String dccRatio = pm.getDccRatio();
        joinHits = this.getMaxHits(joinRatio);
        joinLimit = this.getMaxLimit(joinRatio);
        ctcpHits = this.getMaxHits(ctcpRatio);
        ctcpLimit = this.getMaxLimit(ctcpRatio);
        privmsgHits = this.getMaxHits(privmsgRatio);
        privmsgLimit = this.getMaxLimit(privmsgRatio);
        chanmsgHits = this.getMaxHits(chanmsgRatio);
        chanmsgLimit = this.getMaxLimit(chanmsgRatio);
        colourHits = this.getMaxHits(colourRatio);
        colourLimit = this.getMaxLimit(colourRatio);
        dccHits = this.getMaxHits(dccRatio);
        dccLimit = this.getMaxLimit(dccRatio);
    }
}
