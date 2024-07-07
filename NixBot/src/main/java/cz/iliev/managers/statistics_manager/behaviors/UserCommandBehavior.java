package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserCommandBehavior {
    public static void behave(long userId, String commandName){
        String categoryName = "UserCommand";

        // Update user command ever
        DatabaseStatisticsService.updateStatistics(categoryName, userId, commandName, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user command ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user command ever");
        });

        // Update user command month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", userId, commandName, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user command month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user command month");
        });
    }
}
