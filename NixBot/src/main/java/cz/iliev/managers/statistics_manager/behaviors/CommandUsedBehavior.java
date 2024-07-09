package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class CommandUsedBehavior {
    public static void behave(String commandName){

        String categoryName = "CommandUsed";

        // Update commands used ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, commandName, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment commands used ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment commands used ever");
        });

        // Update commands used month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, commandName, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment commands used month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment commands used month");
        });

        // Update commands used year
        DatabaseStatisticsService.updateStatistics(categoryName + "Year", -1, commandName, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.YEAR), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment commands used year. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment commands used year");
        });

    }
}
