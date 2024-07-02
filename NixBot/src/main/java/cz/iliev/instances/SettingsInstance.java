package cz.iliev.instances;

public class SettingsInstance {
    private long statsMessageId;
    private long rolesMessageId;
    private String botToken;
    private String databaseHost;
    private String databaseName;
    private String databaseUsername;
    private String databasePassword;

    public SettingsInstance(long statsMessageId, long rolesMessageId, String botToken, String databaseHost, String databaseName, String databaseUsername, String databasePassword) {
        this.statsMessageId = statsMessageId;
        this.rolesMessageId = rolesMessageId;
        this.botToken = botToken;
        this.databaseHost = databaseHost;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
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

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
