package cz.nix3r.instances;

public class RoleSetterInstance {

    private long roleId;
    private boolean adding;
    private String emoji;
    private String componentLabel;

    public RoleSetterInstance(long roleId, boolean adding, String emoji, String componentLabel) {
        this.roleId = roleId;
        this.adding = adding;
        this.emoji = emoji;
        this.componentLabel = componentLabel;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getComponentLabel() {
        return componentLabel;
    }

    public void setComponentLabel(String componentLabel) {
        this.componentLabel = componentLabel;
    }
}
