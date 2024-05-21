package cz.nix3r.instances.logInstances;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;

public class LogTextChannel {

    private long id;
    private String name;
    private LogServer server;
    private LogServerCategory category;
    private String type;

    public LogTextChannel(long id, String name, LogServer server, LogServerCategory category, String type) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.category = category;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LogServer getServer() {
        return server;
    }

    public void setServer(LogServer server) {
        this.server = server;
    }

    public LogServerCategory getCategory() {
        return category;
    }

    public void setCategory(LogServerCategory category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
