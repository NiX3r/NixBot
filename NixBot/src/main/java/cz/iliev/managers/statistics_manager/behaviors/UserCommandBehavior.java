package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.util.HashMap;

public class UserCommandBehavior {
    public static void behave(long userId, String commandName){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing user command statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        if(stats.userCommandUse.containsKey(userId))
            stats.userCommandUse.get(userId).put(commandName, stats.userCommandUse.get(userId).getOrDefault(commandName, 0L) + 1);
        else
            stats.userCommandUse.put(userId, new HashMap<>() {{ put( commandName, 1L );}});

        if(stats.userCommandUseMonth.containsKey(userId))
            stats.userCommandUseMonth.get(userId).put(commandName, stats.userCommandUseMonth.get(userId).getOrDefault(commandName, 0L) + 1);
        else
            stats.userCommandUseMonth.put(userId, new HashMap<>() {{ put( commandName, 1L );}});

        if(stats.userCommandUseYear.containsKey(userId))
            stats.userCommandUseYear.get(userId).put(commandName, stats.userCommandUseYear.get(userId).getOrDefault(commandName, 0L) + 1);
        else
            stats.userCommandUseYear.put(userId, new HashMap<>() {{ put( commandName, 1L );}});

        LogUtils.info("Increased done");
    }
}
