package cz.iliev.instances;

import java.util.List;

public class SettingsInstance {
    private long statsMessageId;
    private long rolesMessageId;
    private String botToken;
    private String databaseHost;
    private String databaseName;
    private String databaseUsername;
    private String databasePassword;
    private String welcomeChannelId;
    private String newsChannelId;
    private String nixBotChannelId;
    private String commandChannelId;
    private String roleChannelId;
    private String tempChannelId;
    private String tempCategoryId;
    private String ticketChannelId;
    private String ticketCategoryId;
    private List<String> defaultRolesId;


    public SettingsInstance(long statsMessageId, long rolesMessageId, String botToken, String databaseHost, String databaseName, String databaseUsername, String databasePassword, String welcomeChannelId, String newsChannelId, String nixBotChannelId, String commandChannelId, String roleChannelId, String tempChannelId, String tempCategoryId, String ticketChannelId, String ticketCategoryId, List<String> defaultRolesId) {
        this.statsMessageId = statsMessageId;
        this.rolesMessageId = rolesMessageId;
        this.botToken = botToken;
        this.databaseHost = databaseHost;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.welcomeChannelId = welcomeChannelId;
        this.newsChannelId = newsChannelId;
        this.nixBotChannelId = nixBotChannelId;
        this.commandChannelId = commandChannelId;
        this.roleChannelId = roleChannelId;
        this.tempChannelId = tempChannelId;
        this.tempCategoryId = tempCategoryId;
        this.ticketChannelId = ticketChannelId;
        this.ticketCategoryId = ticketCategoryId;
        this.defaultRolesId = defaultRolesId;
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

    public String getWelcomeChannelId() {
        return welcomeChannelId;
    }

    public void setWelcomeChannelId(String welcomeChannelId) {
        this.welcomeChannelId = welcomeChannelId;
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public String getNixBotChannelId() {
        return nixBotChannelId;
    }

    public void setNixBotChannelId(String nixBotChannelId) {
        this.nixBotChannelId = nixBotChannelId;
    }

    public String getCommandChannelId() {
        return commandChannelId;
    }

    public void setCommandChannelId(String commandChannelId) {
        this.commandChannelId = commandChannelId;
    }

    public String getRoleChannelId() {
        return roleChannelId;
    }

    public void setRoleChannelId(String roleChannelId) {
        this.roleChannelId = roleChannelId;
    }

    public String getTempChannelId() {
        return tempChannelId;
    }

    public void setTempChannelId(String tempChannelId) {
        this.tempChannelId = tempChannelId;
    }

    public String getTempCategoryId() {
        return tempCategoryId;
    }

    public void setTempCategoryId(String tempCategoryId) {
        this.tempCategoryId = tempCategoryId;
    }

    public String getTicketChannelId() {
        return ticketChannelId;
    }

    public void setTicketChannelId(String ticketChannelId) {
        this.ticketChannelId = ticketChannelId;
    }

    public String getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(String ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public List<String> getDefaultRolesId() {
        return defaultRolesId;
    }

    public void setDefaultRolesId(List<String> defaultRolesId) {
        this.defaultRolesId = defaultRolesId;
    }
}
