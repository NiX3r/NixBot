package cz.iliev.managers.reminder_manager.threads;

import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.managers.reminder_manager.utils.CronUtils;
import cz.iliev.managers.reminder_manager.utils.ReminderUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.User;

import java.util.HashMap;

public class ReminderThread implements Runnable {

    @Override
    public void run() {

        var reminders = CommonUtils.reminderManager.getReminders();
        var members = new HashMap<Long, User>();
        var server = CommonUtils.bot.getServerById(CommonUtils.NIX_CREW_ID).get();
        int remindersSentCount = 0;

        for (ReminderInstace reminder : reminders) {

            if(server.getMemberById(reminder.getAuthorId()).isPresent()){
                if(!members.containsKey(reminder.getAuthorId()))
                    members.put(reminder.getAuthorId(), server.getMemberById(reminder.getAuthorId()).get());
            }
            else
                continue;

            if(!CronUtils.isCronToday(reminder.getCron()))
                continue;

            members.get(reminder.getAuthorId()).sendMessage(
                    ReminderUtils.createEmbed(
                            reminder.getName(),
                            reminder.getDescription()
                    )
            );
            remindersSentCount++;
            LogUtils.info("Reminder '" + reminder.getName() + "' sent");

        }

        CommonUtils.botActivityManager.setActivity(ActivityType.PLAYING, " " + remindersSentCount + " reminders", 5000);

    }
}
