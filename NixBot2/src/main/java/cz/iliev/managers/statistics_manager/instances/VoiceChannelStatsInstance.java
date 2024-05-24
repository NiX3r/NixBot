package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

import java.util.HashMap;

public class VoiceChannelStatsInstance {

    // HashMap for most used voice channels id of current month
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedVoiceChannelIdMonth;
    // HashMap for most used voice channels id ever
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedVoiceChannelIdEver;
    // long for call time of current day
    private long callTimeDay;
    // long for call time of current month
    private long callTimeMonth;
    // long for call time ever
    private long callTimeEver;

    public VoiceChannelStatsInstance(HashMap<Long, Long> usedVoiceChannelIdMonth, HashMap<Long, Long> usedVoiceChannelIdEver, long callTimeDay, long callTimeMonth, long callTimeEver) {
        this.usedVoiceChannelIdMonth = usedVoiceChannelIdMonth;
        this.usedVoiceChannelIdEver = usedVoiceChannelIdEver;
        this.callTimeDay = callTimeDay;
        this.callTimeMonth = callTimeMonth;
        this.callTimeEver = callTimeEver;
    }

    public HashMap<Long, Long> getUsedVoiceChannelIdMonth() {
        return usedVoiceChannelIdMonth;
    }

    public HashMap<Long, Long> getUsedVoiceChannelIdEver() {
        return usedVoiceChannelIdEver;
    }

    public long getCallTimeDay() {
        return callTimeDay;
    }

    public long getCallTimeMonth() {
        return callTimeMonth;
    }

    public long getCallTimeEver() {
        return callTimeEver;
    }

    public void incrementVoiceChannel(long channelId, long increment){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        usedVoiceChannelIdMonth.merge(channelId, increment, Long::sum);
        usedVoiceChannelIdEver.merge(channelId, increment, Long::sum);
    }

    public void incrementCallTime(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        callTimeDay++;
        callTimeMonth++;
        callTimeEver++;
    }
}
