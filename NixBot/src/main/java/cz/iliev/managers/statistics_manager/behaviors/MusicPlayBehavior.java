package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MusicPlayBehavior {
    public static void behave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing songs play statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.musicPlay++;
        stats.musicPlayMonth++;

        LogUtils.info("Increased done");
    }
}
