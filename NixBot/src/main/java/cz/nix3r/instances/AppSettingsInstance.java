package cz.nix3r.instances;

public class AppSettingsInstance {

    private long statsMessageId;
    private long rolesMessageId;
    private String botToken;

    public AppSettingsInstance(long statsMessageId, long rolesMessageId, String botToken) {
        this.statsMessageId = statsMessageId;
        this.rolesMessageId = rolesMessageId;
        this.botToken = botToken;
    }

    public long getStatsMessageId() {
        return statsMessageId;
    }

    public void setStatsMessageId(long statsMessageId) {
        this.statsMessageId = statsMessageId;
    }

    public long getRolesMessageId() {
        return rolesMessageId;
    }

    public void setRolesMessageId(long rolesMessageId) {
        this.rolesMessageId = rolesMessageId;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
