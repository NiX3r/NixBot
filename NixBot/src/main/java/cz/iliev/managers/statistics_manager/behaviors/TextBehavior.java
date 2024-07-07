package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TextBehavior {
    public static void behave(){
        String categoryName = "Text";

        // Update text ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment text ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment text ever");
        });

        // Update text day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment text month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment text month");
        });

        // Update text month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment text month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment text month");
        });
    }
}
