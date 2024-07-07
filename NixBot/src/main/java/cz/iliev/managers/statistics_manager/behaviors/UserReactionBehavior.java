package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserReactionBehavior {
    public static void behave(long userId, String reaction){
        String categoryName = "UserReaction";

        // Update user reaction ever
        DatabaseStatisticsService.updateStatistics(categoryName, userId, reaction, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user reaction ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user reaction ever");
        });

        // Update user reaction day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", userId, reaction, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user reaction month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user reaction month");
        });

        // Update user reaction month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", userId, reaction, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user reaction month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user reaction month");
        });
    }
}
