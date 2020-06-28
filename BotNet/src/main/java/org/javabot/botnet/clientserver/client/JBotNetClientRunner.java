package org.javabot.botnet.clientserver.client;

import net.munki.jbotnet.client.JBotNetClient;
import net.munki.jbotnet.client.JBotNetClientException;
import net.munki.jbotnet.interfaces.JBotInterface;

public class JBotNetClientRunner {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

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

