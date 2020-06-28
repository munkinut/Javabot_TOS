package org.javabot.botnet.jms.client;

import net.munki.jbotnet.client.JBot;
import net.munki.jbotnet.client.JBotNetClient;
import net.munki.jbotnet.client.JBotNetClientException;
import net.munki.jbotnet.interfaces.JBotInterface;
import org.javabot.configuration.PropertyManager;

public class JavaBotNetJMS {

    //JBotNetClientJMS jbnc = null;
    private PropertyManager pm = PropertyManager.getInstance();

    public JavaBotNetJMS() {
    }

    // TODO
    // Start JMS Server
    // Create topic jbotnet
    // Create a connection from here
    // subscribe to jbotnet
    // publish to jbotnet


//    public JBotNetClientJMS start() {
//        String oldMessage = "";
//        String botName = pm.getName();
//        JBotInterface jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0, oldMessage);
        //try {
            //jbnc = new JBotNetClient(waitFor, botName, jBot, "localhost");
            //jbnc.exportBot(jBot);
            //jbnc.register(jBot);
            //jbnc.getT().start();
        //} catch (JBotNetClientException | RemoteException e) {
        //    e.printStackTrace();
        //}
//
//        return jbnc;
//    }
}
