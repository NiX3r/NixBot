package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.managers.database_manager.services.DatabaseStatisticsService;
import cz.iliev.managers.statistics_manager.enums.TimeUnitEnum;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class VoiceChannelBehavior {
    public static void behave(long channelId){
        String categoryName = "VoiceChannel";

        // Update voice channel ever
        DatabaseStatisticsService.updateStatistics(categoryName, -1, String.valueOf(channelId), null, 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment voice channel ever. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment voice channel ever");
        });

        // Update voice channel month
        DatabaseStatisticsService.updateStatistics(categoryName + "Month", -1, String.valueOf(channelId), CommonUtils.statisticsManager.formatDate(TimeUnitEnum.MONTH), 1, response -> {
            if(response instanceof Exception){
                LogUtils.fatalError("Error while increment voice channel month. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }
            LogUtils.info("Successfully increment voice channel month");
        });
    }
}
