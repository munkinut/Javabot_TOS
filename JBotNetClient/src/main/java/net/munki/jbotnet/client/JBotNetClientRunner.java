package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClientRunner {

    static int waitFor = 60000;
    static int numOfBots = 5;

    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            CopyOnWriteArrayList<JBotNetClient> clientList = new CopyOnWriteArrayList<>();
            CopyOnWriteArrayList<JBotInterface> botList = new CopyOnWriteArrayList<>();

            for (int i = 0; i < numOfBots; i++) {
                String oldMessage = "";
                String botName = new StringBuilder("javamunk_").append(i).toString();
                JBotInterface jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0, oldMessage);
                double random = Math.random() * waitFor;
                int randomInt = (int) random;
                JBotNetClient jbnc = new JBotNetClient(randomInt, botName, jBot, args[0]); // registryHost

                jbnc.exportBot(jBot);

                try {
                    if (jbnc.register(jBot)) {
                        botList.add(jBot);
                        clientList.add(jbnc);
                    }
                    jbnc.getT().start();
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
            for(JBotNetClient jc : clientList) {
                try {
                    System.out.println("Joining " + jc.getName() + "...");
                    jc.getT().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                botList.remove(jc.getjBot());
                clientList.remove(jc);
            }

        } catch (JBotNetClientException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        System.exit(0);
    }

}

