package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

public class StatisticsManagerServerMemberLeaveListener implements ServerMemberLeaveListener {
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent serverMemberLeaveEvent) {
        CommonUtils.statisticsManager.getStatistics().getServerStatsInstance().incrementMemberLeave();
        LogUtils.info("Member leave statistics updated");
    }
}