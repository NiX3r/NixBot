package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class ReactionBehavior {
    public static void behave(String emoji){

        String categoryName = "Reaction";

        // Update reaction ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, emoji, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment reaction ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment reaction ever");
        });

        // Update reaction day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, emoji, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment reaction month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment reaction month");
        });

        // Update reaction month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, emoji, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment reaction month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment reaction month");
        });

    }
}
