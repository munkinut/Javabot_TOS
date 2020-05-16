package org.javabot.tests;

import org.javabot.configuration.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyManagerSetTest {

    private PropertyManager pm;

    public PropertyManagerSetTest() {

    }

    @Before
    public void setUp() {
        pm = PropertyManager.getInstance();
    }

    @After
    public void tearDown() {
        pm.resetFromConfigBackup();
        pm = null;
    }

    @Test
    public void setServer() {
        String newServer = "irc.ircnet.org";
        pm.setServer(newServer);
        String server = pm.getServer();
        assertEquals(newServer, server);
    }

    @Test
    public void setName() {
        String newName = "munkinut";
        pm.setName(newName);
        String name = pm.getName();
        assertEquals(newName, name);
    }

    @Test
    public void setNickname() {
        String newNickname = "munkinut";
        pm.setNickname(newNickname);
        String nickName = pm.getNickname();
        assertEquals(newNickname, nickName);
    }

    @Test
    public void setChannel() {
        String newChannel = "#munkinut";
        pm.setChannel(newChannel);
        String channel = pm.getChannel();
        assertEquals(newChannel, channel);
    }

    @Test
    public void setChannelModes() {
        String newChannelModes = "snt";
        pm.setChannelModes(newChannelModes);
        String channelModes = pm.getChannelModes();
        assertEquals(newChannelModes, channelModes);
    }

    @Test
    public void setJoinRatio() {
        String newJoinRatio = "15:40";
        pm.setJoinRatio(newJoinRatio);
        String joinRatio = pm.getJoinRatio();
        assertEquals(newJoinRatio, joinRatio);
    }

    @Test
    public void setPrivmsgRatio() {
        String newPrivmsgRatio = "15:40";
        pm.setPrivmsgRatio(newPrivmsgRatio);
        String privmsgRatio = pm.getPrivmsgRatio();
        assertEquals(newPrivmsgRatio, privmsgRatio);
    }

    @Test
    public void setChanmsgRatio() {
        String newChanmsgRatio = "15:40";
        pm.setChanmsgRatio(newChanmsgRatio);
        String chanmsgRatio = pm.getChanmsgRatio();
        assertEquals(newChanmsgRatio, chanmsgRatio);
    }

    @Test
    public void setColourRatio() {
        String newColourRatio = "15:40";
        pm.setColourRatio(newColourRatio);
        String colourRatio = pm.getColourRatio();
        assertEquals(newColourRatio, colourRatio);
    }

    @Test
    public void setCtcpRatio() {
        String newCtcpRatio = "15:40";
        pm.setCtcpRatio(newCtcpRatio);
        String ctcpRatio = pm.getCtcpRatio();
        assertEquals(newCtcpRatio, ctcpRatio);
    }

    @Test
    public void setDccRatio() {
        String newDccRatio = "15:40";
        pm.setDccRatio(newDccRatio);
        String dccRatio = pm.getDccRatio();
        assertEquals(newDccRatio, dccRatio);
    }

    @Test
    public void setPort() {
        int newPort = 6665;
        pm.setPort(newPort);
        int port = pm.getPort();
        assertEquals(newPort, port);
    }

    @Test
    public void setAutovoice() {
        boolean newAutovoice = false;
        pm.setAutovoice(newAutovoice);
        boolean autovoice = pm.getAutovoice();
        assertEquals(newAutovoice, autovoice);
    }

    @Test
    public void setAutogreet() {
        boolean newAutogreet = false;
        pm.setAutogreet(newAutogreet);
        boolean autogreet = pm.getAutogreet();
        assertEquals(newAutogreet, autogreet);
    }

    @Test
    public void setFloodProtection() {
        boolean newFlood = false;
        pm.setFloodProtection(newFlood);
        boolean flood = pm.getFloodProtection();
        assertEquals(newFlood, flood);
    }

    @Test
    public void setOpme() {
        boolean newOpme = false;
        pm.setOpme(newOpme);
        boolean opme = pm.getOpme();
        assertEquals(newOpme, opme);
    }

    @Test
    public void setCycleForOps() {
        boolean newCycle = false;
        pm.setCycleForOps(newCycle);
        boolean cycle = pm.getCycleForOps();
        assertEquals(newCycle, cycle);
    }

    @Test
    public void setDynamicLimit() {
        boolean newDynamic = false;
        pm.setDynamicLimit(newDynamic);
        boolean dynamic = pm.getDynamicLimit();
        assertEquals(newDynamic, dynamic);
    }
}