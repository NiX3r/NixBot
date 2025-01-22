package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class TextBehavior {
    public static void behave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing text statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.text++;
        stats.textDay++;
        stats.textMonth++;

        LogUtils.info("Increased done");
    }
}
