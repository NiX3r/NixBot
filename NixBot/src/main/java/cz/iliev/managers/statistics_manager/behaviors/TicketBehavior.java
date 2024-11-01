package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TicketBehavior {
    public static void behave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing ticket statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.ticket++;
        stats.ticketDay++;
        stats.ticketMonth++;

        LogUtils.info("Increased done");
    }
}
