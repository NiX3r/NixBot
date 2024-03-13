package cz.nix3r.instances;

public class AppSettingsInstance {

    private long statsMessageId;

    public AppSettingsInstance(long statsMessageId) {
        this.statsMessageId = statsMessageId;
    }

    public long getStatsMessageId() {
        return statsMessageId;
    }

    public void setStatsMessageId(long statsMessageId) {
        this.statsMessageId = statsMessageId;
    }
}
