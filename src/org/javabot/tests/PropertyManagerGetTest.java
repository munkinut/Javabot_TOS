package org.javabot.tests;

import org.javabot.configuration.PropertyManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyManagerGetTest {

    private PropertyManager pm;
    private PropertyManager testpm;

    @BeforeEach
    void setUp() {
        pm = PropertyManager.getInstance();
        testpm = PropertyManager.getInstance();
    }

    @AfterEach
    void tearDown() {
        pm = null;
        testpm = null;
    }

    @Test
    void getInstance() {
        assertEquals(testpm, pm);
    }

    @Test
    void getServer() {
        assertEquals(pm.getServer(), "192.168.1.103");
    }

    @Test
    void getScriptsLocation() {
        assertEquals(pm.getScriptsLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\scripts\\");
    }

    @Test
    void getBansLocation() {
        assertEquals(pm.getBansLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\bans.xml");
    }

    @Test
    void getUsersLocation() {
        assertEquals(pm.getUsersLocation(), "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\users.xml");
    }

    @Test
    void getName() {
        assertEquals(pm.getName(), "javamunk");
    }

    @Test
    void getNickname() {
        assertEquals(pm.getNickname(), "javamunk");
    }

    @Test
    void getChannel() {
        assertEquals(pm.getChannel(), "#javamunk");
    }

    @Test
    void getChannelModes() {
        assertEquals(pm.getChannelModes(), "snt");
    }

    @Test
    void getJoinRatio() {
        assertEquals(pm.getJoinRatio(), "2:60");
    }

    @Test
    void getPrivmsgRatio() {
        assertEquals(pm.getPrivmsgRatio(), "10:30");
    }

    @Test
    void getChanmsgRatio() {
        assertEquals(pm.getChanmsgRatio(), "5:20");
    }

    @Test
    void getColourRatio() {
        assertEquals(pm.getColourRatio(), "10:30");
    }

    @Test
    void getCtcpRatio() {
        assertEquals(pm.getCtcpRatio(), "5:5");
    }

    @Test
    void getDccRatio() {
        assertEquals(pm.getDccRatio(), "10:30");
    }

    @Test
    void getPort() {
        assertEquals(pm.getPort(), 6667);
    }

    @Test
    void getAutovoice() {
        assertEquals(pm.getAutovoice(), true);
    }

    @Test
    void getAutogreet() {
        assertEquals(pm.getAutogreet(), true);
    }

    @Test
    void getFloodProtection() {
        assertEquals(pm.getFloodProtection(), false);
    }

    @Test
    void getOpme() {
        assertEquals(pm.getOpme(), true);
    }

    @Test
    void getCycleForOps() {
        assertEquals(pm.getCycleForOps(), false);
    }

    @Test
    void getDynamicLimit() {
        assertEquals(pm.getDynamicLimit(), true);
    }

}