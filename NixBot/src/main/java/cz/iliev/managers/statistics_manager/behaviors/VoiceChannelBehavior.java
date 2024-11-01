package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class VoiceChannelBehavior {
    public static void behave(long channelId){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing channel statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.voiceChannel.put(channelId, stats.voiceChannel.getOrDefault(channelId, 0L) + 1);
        stats.voiceChannelMonth.put(channelId, stats.voiceChannelMonth.getOrDefault(channelId, 0L) + 1);
        stats.voiceChannelYear.put(channelId, stats.voiceChannelYear.getOrDefault(channelId, 0L) + 1);

        LogUtils.info("Increased done");
    }
}
