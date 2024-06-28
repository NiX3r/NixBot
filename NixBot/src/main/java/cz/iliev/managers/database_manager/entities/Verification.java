package cz.iliev.managers.database_manager.entities;

public class Verification {

    private int id;
    private String code;
    private long createDate;
    private long verificateDate;
    private boolean verificated;

    public Verification(int id, String code, long createDate, long verificateDate, boolean verificated) {
        this.id = id;
        this.code = code;
        this.createDate = createDate;
        this.verificateDate = verificateDate;
        this.verificated = verificated;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getVerificateDate() {
        return verificateDate;
    }

    public void setVerificateDate(long verificateDate) {
        this.verificateDate = verificateDate;
    }

    public boolean isVerificated() {
        return verificated;
    }

    public void setVerificated(boolean verificated) {
        this.verificated = verificated;
    }
}
