package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.BoostBehavior;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.server.ServerChangeBoostCountEvent;
import org.javacord.api.listener.server.ServerChangeBoostCountListener;

public class StatisticsServerChangeBoostCountListener implements ServerChangeBoostCountListener {
    @Override
    public void onServerChangeBoostCount(ServerChangeBoostCountEvent serverChangeBoostCountEvent) {
        // Just add to statistics boost increment
        if(serverChangeBoostCountEvent.getNewBoostCount() < serverChangeBoostCountEvent.getOldBoostCount())
            return;

        BoostBehavior.behave(serverChangeBoostCountEvent.getNewBoostCount() - serverChangeBoostCountEvent.getOldBoostCount());
        LogUtils.info("Boost statistics updated");
    }
}