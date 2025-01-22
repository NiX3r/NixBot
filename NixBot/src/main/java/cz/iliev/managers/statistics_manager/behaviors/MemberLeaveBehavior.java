package cz.iliev.managers.statistics_manager.behaviors;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

public class MemberLeaveBehavior {
    public static void behave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();

        LogUtils.info("Increasing member leave statistics");

        var stats = CommonUtils.statisticsManager.getStatistics();
        stats.memberLeave++;
        stats.memberLeaveMonth++;
        stats.memberLeaveYear++;

        LogUtils.info("Increased done");
    }
}
