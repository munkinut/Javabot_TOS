package net.munki.jbotnet.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JBotInterface extends Remote {

    String getRealName() throws RemoteException;
    String getNickname() throws RemoteException;
    String getDescription() throws RemoteException;
    String getOwnerEmail() throws RemoteException;
    String getServer() throws RemoteException;
    String getChannel() throws RemoteException;
    String getIp() throws RemoteException;
    int getPort() throws RemoteException;
    String getMessage() throws RemoteException;
    void setNickname(String nickname) throws RemoteException;
    void setDescription(String description) throws RemoteException;
    void setOwnerEmail(String ownerEmail) throws RemoteException;
    void setServer(String server) throws RemoteException;
    void setChannel(String channel) throws RemoteException;
    void setMessage(String message) throws RemoteException;

}
