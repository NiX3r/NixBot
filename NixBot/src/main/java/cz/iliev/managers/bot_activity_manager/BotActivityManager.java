package cz.iliev.managers.bot_activity_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.bot_activity_manager.instances.BotActivityInstance;
import cz.iliev.managers.bot_activity_manager.timers.BotActivityTimer;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public class BotActivityManager implements IManager {

    private boolean ready;
    private List<BotActivityInstance> activities;
    private boolean timerRunning;

    public BotActivityManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start BotActivityManager");
        setBasicActivity();
        timerRunning = false;
        activities = new ArrayList<BotActivityInstance>();
        ready = true;
        LogUtils.info("BotActivityManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill BotActivityManager");
        ready = false;
        LogUtils.info("BotActivityManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        return;
    }

    @Override
    public void onConsoleCommand(Object data) {
        return;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "Bot activity manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for updating bot activity\nFeatures: \n- Basic activity\n- Custom activity";
    }

    @Override
    public String color() {
        return "#a3b707";
    }

    public void setBasicActivity(){
        int membersCount = CommonUtils.getNixCrew().getMemberCount();
        CommonUtils.bot.updateActivity(ActivityType.WATCHING, " for " + membersCount + " users here");
    }

    public void setActivity(ActivityType type, String message, long length){
        BotActivityInstance activity = new BotActivityInstance(type, message, length);

        if(timerRunning){
            activities.add(activity);
        }
        else {
            BotActivityTimer timer = new BotActivityTimer(activity);
        }
    }

    public List<BotActivityInstance> getActivities() {
        return activities;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }
}
