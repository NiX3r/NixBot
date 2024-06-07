package cz.iliev.managers.bot_activity_manager.timers;

import cz.iliev.managers.bot_activity_manager.instances.BotActivityInstance;
import cz.iliev.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class BotActivityTimer extends TimerTask {

    private Timer timer;

    public BotActivityTimer(BotActivityInstance activity){
        CommonUtils.bot.updateActivity(activity.getType(), activity.getMessage());
        CommonUtils.botActivityManager.setTimerRunning(true);
        timer = new Timer();
        timer.schedule(this, activity.getLength());
    }

    @Override
    public void run() {
        if(CommonUtils.botActivityManager.getActivities().isEmpty()){
            CommonUtils.botActivityManager.setBasicActivity();
            CommonUtils.botActivityManager.setTimerRunning(false);
        }
        else{
            var activity = CommonUtils.botActivityManager.getActivities().get(0);
            BotActivityTimer timer2 = new BotActivityTimer(activity);
            CommonUtils.botActivityManager.getActivities().remove(0);
        }
    }
}
