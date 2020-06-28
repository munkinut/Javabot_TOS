package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;
import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClient implements Runnable {

    private String name;
    private Thread t;
    private JBotInterface jBot;
    private int randomInt;

    private JBotNetServerInterface jBotNetServerInterface;
    private final static String SERVICE = "JBotNetServerInterface";
    private Registry registry;

    public JBotNetClient(int randomInt, String name, JBotInterface jBot, String rmiRegistryHost) throws JBotNetClientException {
        this.randomInt = randomInt;
        this.name = name;
        this.jBot = jBot;
        try {
            registry = LocateRegistry.getRegistry(rmiRegistryHost);
            jBotNetServerInterface = (JBotNetServerInterface)registry.lookup(SERVICE);
            t = new Thread(this, name);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        } catch (NotBoundException e) {
            throw new JBotNetClientException(e);
        }
    }

    public String getName() {
        return name;
    }

    public Thread getT() {
        return t;
    }

    public JBotInterface getjBot() {
        return jBot;
    }

    public boolean register(JBotInterface jBot) throws RemoteException {
        System.out.println("Registering " + jBot.getRealName() + "...");
        return jBotNetServerInterface.register(jBot);
    }

    public boolean deregister(JBotInterface jBot) throws JBotNetClientException {
        try {
            System.out.println("Deregistering " + jBot.getRealName() + "...");
            return jBotNetServerInterface.deregister(jBot);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    private CopyOnWriteArrayList<JBotInterface> getBots() throws JBotNetClientException {
        try {
            return jBotNetServerInterface.getBots();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public void exportBot(JBotInterface jBot) throws JBotNetClientException {
        try {
            System.out.println("Exporting " + jBot.getRealName() + "...");
            String name = jBot.getRealName();
            JBotInterface stub =
                    (JBotInterface) UnicastRemoteObject.exportObject(jBot, 0);
            registry.rebind(name, stub);
            System.out.println("JBotInterface bound");
            // Thread.sleep(1000);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void unbind(JBotInterface jBot) throws JBotNetClientException {
        try {
            String name = jBot.getRealName();
            System.out.println("Unbinding " + name + "...");
            registry.unbind(name);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        } catch (NotBoundException e) {
            throw new JBotNetClientException(e);
        }

    }

    public JBotInterface getBot(String botName) throws JBotNetClientException {
        try {
            return jBotNetServerInterface.getABot(botName);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public void waitForMessages(String oldMessage) throws JBotNetClientException {
        long startTime = System.currentTimeMillis();
        System.out.println(this.getName() + " waiting for messages for " + randomInt + " mSec...");
        while (false||(System.currentTimeMillis()-startTime)<(randomInt)) {
            String message = poll(jBot);
            if (!message.equals(oldMessage)) {
                sendMessage(jBot, message); // here, jBot is to be excluded
                oldMessage = message;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(jBot.getRealName() + " no longer waiting for messages...");
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
        deregister(getjBot());
        unbind(jBot);
    }

    public String poll(JBotInterface jBot) throws JBotNetClientException {
        try {
            return jBot.getMessage();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public void sendMessage(JBotInterface excludeBot, String message) throws JBotNetClientException {
        try {
            CopyOnWriteArrayList<JBotInterface> bots = getBots();
            for(JBotInterface bot : bots) {
                if (!bot.equals(excludeBot)) bot.setMessage(message);
            }
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    @Override
    public void run() {
        try {
            this.waitForMessages("");
        } catch (JBotNetClientException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
