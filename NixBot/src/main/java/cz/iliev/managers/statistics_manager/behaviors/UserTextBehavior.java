package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserTextBehavior {
    public static void behave(long userId){
        String categoryName = "UserText";

        // Update user texts ever
        DatabaseStatisticsService.updateStatistics(categoryName, userId, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user texts ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user texts ever");
        });

        // Update user texts month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", userId, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user texts month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user texts month");
        });
    }
}
