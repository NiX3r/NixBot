package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class CommandUseBehavior {
    public static void behave(String commandName){

        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing command statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.commandUse.put(commandName, stats.commandUse.getOrDefault(commandName, 0L) + 1);
        stats.commandUseMonth.put(commandName, stats.commandUseMonth.getOrDefault(commandName, 0L) + 1);
        stats.commandUseYear.put(commandName, stats.commandUseYear.getOrDefault(commandName, 0L) + 1);

        LogUtils.info("Increased done");

    }
}
