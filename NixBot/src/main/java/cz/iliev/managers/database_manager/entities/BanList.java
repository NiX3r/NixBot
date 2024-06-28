package cz.iliev.managers.database_manager.entities;

import cz.iliev.managers.ban_list_manager.enums.BanType;

public class BanList {

    private long id;
    private BanType type;
    private long createDate;
    private long duration;
    private String reason;

    // foreigns
    private Member member;
    private Member admin;

    public BanList(long id, BanType type, long createDate, long duration, String reason) {
        this.id = id;
        this.type = type;
        this.createDate = createDate;
        this.duration = duration;
        this.reason = reason;
    }

    public long getId() {
        return id;
    }

    public BanType getType() {
        return type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getDuration() {
        return duration;
    }

    public String getReason() {
        return reason;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getAdmin() {
        return admin;
    }

    public void setAdmin(Member admin) {
        this.admin = admin;
    }
}
