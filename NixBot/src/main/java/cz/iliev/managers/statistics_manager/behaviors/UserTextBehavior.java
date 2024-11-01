package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserTextBehavior {
    public static void behave(long userId){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing user text statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.userText.put(userId, stats.userText.getOrDefault(userId, 0L) + 1);
        stats.userTextMonth.put(userId, stats.userTextMonth.getOrDefault(userId, 0L) + 1);
        stats.userTextYear.put(userId, stats.userTextYear.getOrDefault(userId, 0L) + 1);

        LogUtils.info("Increased done");
    }
}
