package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TicketBehavior {
    public static void behave(){
        String categoryName = "Ticket";

        // Update ticket ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, null, null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment ticket ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment ticket ever");
        });

        // Update ticket day
        DatabaseStatisticsService.updateStatistics(categoryName + "Day", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.DAY), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment ticket month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment ticket month");
        });

        // Update ticket month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, null, CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment ticket month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment ticket month");
        });
    }
}
