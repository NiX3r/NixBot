package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MusicTimePlayBehavior {
    public static void behave(long increment){
        String categoryName = "MusicTimePlay";

        // Update music time played day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment music time played month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment music time played month");
        });

        // Update music time played month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), increment, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment music time played month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment music time played month");
        });
    }
}
