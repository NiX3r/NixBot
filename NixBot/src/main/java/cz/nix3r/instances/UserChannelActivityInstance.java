package cz.nix3r.instances;

public class UserChannelActivityInstance {

    private long userId;
    private long channelId;
    private long timestamp;

    public UserChannelActivityInstance(long userId, long channelId, long timestamp) {
        this.userId = userId;
        this.channelId = channelId;
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
