package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class UserCallTimeBehavior {
    public static void behave(long userId, long increment){
        String categoryName = "UserCallTime";

        // Update user call time ever
        DatabaseStatisticsService.updateStatistics(categoryName, userId, null, null, increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user call time ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user call time ever");
        });

        // Update user call time month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", userId, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user call time month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user call time month");
        });

        // Update user call time year
        DatabaseStatisticsService.updateStatistics(categoryName + "Year", userId, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.YEAR), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment user call time year. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment user call time year");
        });
    }
}
