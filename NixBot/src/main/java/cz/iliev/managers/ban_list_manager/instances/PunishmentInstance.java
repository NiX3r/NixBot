package cz.iliev.managers.ban_list_manager.instances;

import cz.iliev.managers.ban_list_manager.enums.BanType;

public class PunishmentInstance {

    private BanType type;
    private MemberInstance member;
    private MemberInstance admin;
    private long time;
    private long duration;
    private String description;

    public PunishmentInstance(BanType type, MemberInstance member, MemberInstance admin, long time, long duration, String description) {
        this.type = type;
        this.member = member;
        this.admin = admin;
        this.time = time;
        this.duration = duration;
        this.description = description;
    }

    public BanType getType() {
        return type;
    }

    public void setType(BanType type) {
        this.type = type;
    }

    public MemberInstance getMember() {
        return member;
    }

    public void setMember(MemberInstance member) {
        this.member = member;
    }

    public MemberInstance getAdmin() {
        return admin;
    }

    public void setAdmin(MemberInstance admin) {
        this.admin = admin;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
