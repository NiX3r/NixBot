package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.UserActivityBehavior;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.event.user.UserChangeActivityEvent;
import org.javacord.api.listener.user.UserChangeActivityListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticsUserChangeActivityListener implements UserChangeActivityListener {
    @Override
    public void onUserChangeActivity(UserChangeActivityEvent userChangeActivityEvent) {

        if(userChangeActivityEvent.getNewActivities().isEmpty())
            return;

        var lastActivity = CommonUtils.statisticsManager.getUserLastActivityByUserId(userChangeActivityEvent.getUserId());
        var activity = userChangeActivityEvent.getNewActivities().stream().findFirst().get();

        if(activity.equals("Custom Status"))
            return;

        if(lastActivity != null && activity.getName().equals(lastActivity)){
            return;
        }

        CommonUtils.statisticsManager.getUsersLastActivity().put(userChangeActivityEvent.getUserId(), activity.getName());
        UserActivityBehavior.behave(userChangeActivityEvent.getUserId(), activity.getName());

        LogUtils.info("User activity statistics updated");
    }
}