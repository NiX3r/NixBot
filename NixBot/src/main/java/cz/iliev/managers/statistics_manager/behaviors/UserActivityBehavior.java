package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserActivityBehavior {
    public static void behave(long userId, String activityName){
        String categoryName = "UserActivity";

        // Update members activity ever
        DatabaseStatisticsService.updateStatistics(categoryName, userId, activityName, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members activity ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members activity ever");
        });

        // Update members activity day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", userId, activityName, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members activity month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members activity month");
        });

        // Update members activity month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", userId, activityName, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members activity month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members activity month");
        });
    }
}
