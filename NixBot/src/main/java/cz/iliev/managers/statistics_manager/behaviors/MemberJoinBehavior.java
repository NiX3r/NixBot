package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MemberJoinBehavior {
    public static void behave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing member join statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.memberJoin++;
        stats.memberJoinMonth++;
        stats.memberJoinYear++;

        LogUtils.info("Increased done");
    }
}
