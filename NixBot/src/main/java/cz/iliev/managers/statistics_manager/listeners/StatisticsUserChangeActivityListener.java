package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.UserActivityBehavior;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.event.user.UserChangeActivityEvent;
import org.javacord.api.listener.user.UserChangeActivityListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticsUserChangeActivityListener implements UserChangeActivityListener {
    @Override
    public void onUserChangeActivity(UserChangeActivityEvent userChangeActivityEvent) {

        List<String> oldActivitiesNames = new ArrayList<String>();
        userChangeActivityEvent.getOldActivities().forEach(activity -> {
            if(!oldActivitiesNames.contains(activity.getName()))
                oldActivitiesNames.add(activity.getName());
        });

        userChangeActivityEvent.getNewActivities().forEach(activity -> {
            // If new activity is in old activities -> skip it
            if(oldActivitiesNames.contains(activity.getName()))
                return;

            UserActivityBehavior.behave(userChangeActivityEvent.getUserId(), activity.getName());
        });
        LogUtils.info("User activity statistics updated");
    }
}