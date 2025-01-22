package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class CallTimeBehavior {
    public static void behave(long increment){

        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing call time statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.callTime += increment;
        stats.callTimeDay += increment;
        stats.callTimeMonth += increment;

        LogUtils.info("Increased done");

    }
}
