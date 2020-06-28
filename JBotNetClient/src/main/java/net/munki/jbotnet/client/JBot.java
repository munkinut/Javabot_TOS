package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.io.Serializable;
import java.util.Objects;

public class JBot implements JBotInterface, Serializable {

    private static final long serialVersionUID = 225L;

    private String name;
    private String nickname;
    private String description;
    private String ownerEmail;
    private String server;
    private String channel;
    private String ip;
    private int    port;
    private String message;

    public JBot(String name,
                String nickname,
                String description,
                String ownerEmail,
                String server,
                String channel,
                String ip,
                int port,
                String message) {
        super();
        this.name = name;
        this.nickname = nickname;
        this.description = description;
        this.ownerEmail = ownerEmail;
        this.server = server;
        this.channel = channel;
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    @Override
    public String getRealName() {
        return this.name;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    @Override
    public String getServer() {
        return this.server;
    }

    @Override
    public String getChannel() {
        return this.channel;
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JBot jBot = (JBot) o;
        return name.equals(jBot.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
