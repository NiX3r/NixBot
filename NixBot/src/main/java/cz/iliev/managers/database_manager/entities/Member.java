package cz.iliev.managers.database_manager.entities;

public class Member {

    private long id;
    private String nickname;
    private long createDate;
    private String email;

    // foreigns
    private Verification verification;

    public Member(long id, String nickname, long createDate, String email){

        this.id = id;
        this.nickname = nickname;
        this.createDate = createDate;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }
}
