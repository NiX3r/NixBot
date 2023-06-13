package cz.nix3r.managers;

import java.util.HashMap;

public class TemporaryChannelManager {

    // Key = creator id | Value = channel id
    private HashMap<Long, Long> tempVoiceChannelMap;

    public TemporaryChannelManager(){
        this.tempVoiceChannelMap = new HashMap<Long, Long>();
    }

    public void addTempChannel(long creatorId, long channelId){
        this.tempVoiceChannelMap.put(creatorId, channelId);
    }

    public boolean alreadyHasTempVoiceChannel(long creatorId){
        return tempVoiceChannelMap.containsKey(creatorId);
    }

    public HashMap<Long, Long> getTempVoiceChannelMap() {
        return tempVoiceChannelMap;
    }
}
