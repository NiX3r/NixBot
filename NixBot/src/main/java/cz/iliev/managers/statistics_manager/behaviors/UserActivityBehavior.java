package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.util.HashMap;

public class UserActivityBehavior {
    public static void behave(long userId, String activityName){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing user activity statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        if(stats.userActivity.containsKey(userId))
            stats.userActivity.get(userId).put(activityName, stats.userActivity.get(userId).getOrDefault(activityName, 0L) + 1);
        else
            stats.userActivity.put(userId, new HashMap<>() {{ put( activityName, 1L );}});

        if(stats.userActivityMonth.containsKey(userId))
            stats.userActivityMonth.get(userId).put(activityName, stats.userActivityMonth.get(userId).getOrDefault(activityName, 0L) + 1);
        else
            stats.userActivityMonth.put(userId, new HashMap<>() {{ put( activityName, 1L );}});

        if(stats.userActivityYear.containsKey(userId))
            stats.userActivityYear.get(userId).put(activityName, stats.userActivityYear.get(userId).getOrDefault(activityName, 0L) + 1);
        else
            stats.userActivityYear.put(userId, new HashMap<>() {{ put( activityName, 1L );}});

        LogUtils.info("Increased done");
    }
}
