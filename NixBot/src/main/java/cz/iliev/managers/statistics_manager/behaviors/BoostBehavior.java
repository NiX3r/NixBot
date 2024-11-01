package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class BoostBehavior {
    public static void behave(long increment){

        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing boost statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.boost += increment;
        stats.boostMonth += increment;
        stats.boostYear += increment;

        LogUtils.info("Increased done");

    }
}
