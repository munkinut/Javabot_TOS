package org.javabot.tests;

import org.javabot.configuration.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyManagerGetTest {

    private PropertyManager pm;
    private PropertyManager testpm;

    public PropertyManagerGetTest() {

    }

    @Before
    public void setUp() {
        pm = PropertyManager.getInstance();
        testpm = PropertyManager.getInstance();
    }

    @After
    public void tearDown() {
        pm = null;
        testpm = null;
    }

    @Test
    public void getInstance() {
        assertEquals(testpm, pm);
    }

    @Test
    public void getServer() {
        assertEquals(pm.getServer(), "192.168.1.103");
        //assertEquals(pm.getServer(), "10.4.1.8");
    }

    @Test
    public void getScriptsLocation() {
        assertEquals(pm.getScriptsLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\scripts\\");
    }

    @Test
    public void getBansLocation() {
        assertEquals(pm.getBansLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\bans.xml");
    }

    @Test
    public void getUsersLocation() {
        assertEquals(pm.getUsersLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\users.xml");
    }

    @Test
    public void getName() {
        assertEquals(pm.getName(), "javamunk");
    }

    @Test
    public void getNickname() {
        assertEquals(pm.getNickname(), "javamunk");
    }

    @Test
    public void getChannel() {
        assertEquals(pm.getChannel(), "#javamunk");
    }

    @Test
    public void getChannelModes() {
        assertEquals(pm.getChannelModes(), "snt");
    }

    @Test
    public void getJoinRatio() {
        assertEquals(pm.getJoinRatio(), "2:60");
    }

    @Test
    public void getPrivmsgRatio() {
        assertEquals(pm.getPrivmsgRatio(), "10:30");
    }

    @Test
    public void getChanmsgRatio() {
        assertEquals(pm.getChanmsgRatio(), "5:20");
    }

    @Test
    public void getColourRatio() {
        assertEquals(pm.getColourRatio(), "10:30");
    }

    @Test
    public void getCtcpRatio() {
        assertEquals(pm.getCtcpRatio(), "5:5");
    }

    @Test
    public void getDccRatio() {
        assertEquals(pm.getDccRatio(), "10:30");
    }

    @Test
    public void getPort() {
        assertEquals(pm.getPort(), 6667);
    }

    @Test
    public void getAutovoice() {
        assertEquals(pm.getAutovoice(), true);
    }

    @Test
    public void getAutogreet() {
        assertEquals(pm.getAutogreet(), true);
    }

    @Test
    public void getFloodProtection() {
        assertEquals(pm.getFloodProtection(), false);
    }

    @Test
    public void getOpme() {
        assertEquals(pm.getOpme(), true);
    }

    @Test
    public void getCycleForOps() {
        assertEquals(pm.getCycleForOps(), false);
    }

    @Test
    public void getDynamicLimit() {
        assertEquals(pm.getDynamicLimit(), true);
    }

}