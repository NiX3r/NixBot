package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MemberJoinBehavior {
    public static void behavior(){

        String categoryName = "MemberJoin";

        // Update members join ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members join ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members join ever");
        });

        // Update members join day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members join month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members join month");
        });

        // Update members join month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members join month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members join month");
        });

        // Update members join year
        DatabaseStatisticsService.updateStatistics(categoryName + "Year", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.YEAR), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment members join year. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment members join year");
        });

    }
}
