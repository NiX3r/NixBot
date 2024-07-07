package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class BoostBehavior{
    public static void behave() {

        String categoryName = "Boost";

        // Update boost ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment boost ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment boost ever");
        });

        // Update boost month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment boost month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment boost month");
        });

        // Update boost year
        DatabaseStatisticsService.updateStatistics(categoryName + "Year", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.YEAR), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment boost year. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment boost year");
        });

    }
}
