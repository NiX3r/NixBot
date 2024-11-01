package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserCallTimeBehavior {
    public static void behave(long userId, long increment){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing user call time statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.userCallTime.put(userId, stats.userCallTime.getOrDefault(userId, 0L) + 1);
        stats.userCallTimeDay.put(userId, stats.userCallTimeDay.getOrDefault(userId, 0L) + 1);
        stats.userCallTimeMonth.put(userId, stats.userCallTimeMonth.getOrDefault(userId, 0L) + 1);
        stats.userCallTimeYear.put(userId, stats.userCallTimeYear.getOrDefault(userId, 0L) + 1);

        LogUtils.info("Increased done");
    }
}