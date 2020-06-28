package org.javabot.botnet.clientserver.client;

import net.munki.jbotnet.client.JBot;
import net.munki.jbotnet.client.JBotNetClient;
import net.munki.jbotnet.client.JBotNetClientException;
import net.munki.jbotnet.interfaces.JBotInterface;
import org.javabot.configuration.PropertyManager;

import java.rmi.RemoteException;

public class JavaBotNet {

    private static int waitFor = Integer.MAX_VALUE;
    JBotNetClient jbnc = null;
    private PropertyManager pm = PropertyManager.getInstance();

    public JavaBotNet() {
    }

    public JBotNetClient start() {
        String oldMessage = "";
        String botName = pm.getName();
        JBotInterface jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0, oldMessage);
        try {
            jbnc = new JBotNetClient(waitFor, botName, jBot, "localhost");
            jbnc.exportBot(jBot);
            jbnc.register(jBot);
            jbnc.getT().start();
        } catch (JBotNetClientException | RemoteException e) {
            e.printStackTrace();
        }

        return jbnc;
    }

    public void stop() {
        //try {
        //    System.out.println("Waiting for " + jbnc.getName() + " to finish...");
        //    jbnc.getT().join();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }
}

