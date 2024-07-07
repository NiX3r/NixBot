package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class CallTimeBehavior {
    public static void behave(long increment){

        String categoryName = "CallTime";

        // Update call time ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment call time ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment call time ever");
        });

        // Update call time day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment call time year. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment call time year");
        });

        // Update call time month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment call time month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment call time month");
        });

    }
}
