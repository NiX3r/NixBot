package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.UserActivityBehavior;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.user.UserChangeActivityEvent;
import org.javacord.api.listener.user.UserChangeActivityListener;

public class StatisticsUserChangeActivityListener implements UserChangeActivityListener {
    @Override
    public void onUserChangeActivity(UserChangeActivityEvent userChangeActivityEvent) {
        userChangeActivityEvent.getNewActivities().forEach(activity -> {
            // If new activity is in old activities -> skip it
            if (userChangeActivityEvent.getOldActivities().contains(activity))
                return;
            UserActivityBehavior.behave(userChangeActivityEvent.getUserId(), activity.getName());
        });
        LogUtils.info("User activity statistics updated");
    }
}