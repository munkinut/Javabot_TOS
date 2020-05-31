package org.javabot.botnet.client;

import net.munki.jbotnet.client.JBot;
import net.munki.jbotnet.client.JBotNetClient;
import net.munki.jbotnet.client.JBotNetClientException;
import net.munki.jbotnet.interfaces.JBotInterface;

import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClientRunner {

    public static void main(String[] args) {
        JavaBotNet jbn = new JavaBotNet();
        JBotNetClient jbnc = jbn.start();
        JBotInterface jb = jbnc.getjBot();
        try {
            jbnc.sendMessage(jb, "hello");
        } catch (JBotNetClientException e) {
            e.printStackTrace();
        }
        jbn.stop();
        System.exit(0);
    }

}

