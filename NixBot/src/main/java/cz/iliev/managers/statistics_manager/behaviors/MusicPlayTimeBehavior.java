package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MusicPlayTimeBehavior {
    public static void behave(long increment){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing music time play statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.musicPlayTime += increment;
        stats.musicPlayTimeDay += increment;
        stats.musicPlayTimeMonth += increment;

        LogUtils.info("Increased done");
    }
}
