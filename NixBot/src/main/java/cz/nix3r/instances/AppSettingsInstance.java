package cz.nix3r.instances;

public class AppSettingsInstance {

    private long statsMessageId;
    private long rolesMessageId;

    public AppSettingsInstance(long statsMessageId, long rolesMessageId) {
        this.statsMessageId = statsMessageId;
        this.rolesMessageId = rolesMessageId;
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
}
