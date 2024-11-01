package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TextChannelBehavior {
    public static void behave(long channelId){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing text channel use statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.textChannel.put(channelId, stats.textChannel.getOrDefault(channelId, 0L) + 1);
        stats.textChannelMonth.put(channelId, stats.textChannelMonth.getOrDefault(channelId, 0L) + 1);

        LogUtils.info("Increased done");
    }
}
