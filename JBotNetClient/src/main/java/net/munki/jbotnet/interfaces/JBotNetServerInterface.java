package net.munki.jbotnet.interfaces;
 
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public interface JBotNetServerInterface extends Remote {
    boolean register(JBotInterface jBotInterface) throws RemoteException;
    boolean deregister(JBotInterface jBotInterface) throws RemoteException;
    CopyOnWriteArrayList<JBotInterface> getBots() throws RemoteException;
    JBotInterface getABot(String name) throws RemoteException;
}
