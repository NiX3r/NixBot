package cz.iliev.managers.reminder_manager.instances;

public class ReminderInstace {

    private long authorId;
    private String phone;
    private boolean sendSms;
    private String cron;
    private String name;
    private String description;

    public ReminderInstace(long authorId, String phone, boolean sendSms, String cron, String name, String description) {
        this.authorId = authorId;
        this.phone = phone;
        this.sendSms = sendSms;
        this.cron = cron;
        this.name = name;
        this.description = description;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSendSms() {
        return sendSms;
    }

    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
