package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MusicPlayBehavior {
    public static void behave(){
        String categoryName = "MusicPlay";

        // Update music play ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment music play ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment music play ever");
        });

        // Update music play month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment music play month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment music play month");
        });
    }
}
