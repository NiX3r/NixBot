package cz.iliev.instances;

public class SettingsInstance {
    private long statsMessageId;
    private long rolesMessageId;
    private String botToken;
    private String openWeatherApiKey;

    public SettingsInstance(long statsMessageId, long rolesMessageId, String botToken, String openWeatherApiKey) {
        this.statsMessageId = statsMessageId;
        this.rolesMessageId = rolesMessageId;
        this.botToken = botToken;
        this.openWeatherApiKey = openWeatherApiKey;
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

    public String getOpenWeatherApiKey() {
        return openWeatherApiKey;
    }
}
