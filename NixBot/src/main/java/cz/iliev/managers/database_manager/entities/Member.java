package cz.iliev.managers.database_manager.entities;

public class Member {

    private long id;
    private String nickname;
    private long createDate;
    private long verificateDate;
    private String email;

    public Member(long id, String nickname, long createDate){

        this.id = id;
        this.nickname = nickname;
        this.createDate = createDate;
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

    public long getVerificateDate() {
        return verificateDate;
    }

    public void setVerificateDate(long verificateDate) {
        this.verificateDate = verificateDate;
    }
}
