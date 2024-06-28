package cz.iliev.managers.bot_activity_manager.instances;

import org.javacord.api.entity.activity.ActivityType;

public class BotActivityInstance {

    private ActivityType type;
    private String message;
    private long length;

    public BotActivityInstance(ActivityType type, String message, long length){
        this.type = type;
        this.message = message;
        this.length = length;
    }

    public ActivityType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public long getLength() {
        return length;
    }
}
