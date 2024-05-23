package cz.iliev.managers.bot_activity_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.interaction.SlashCommandInteraction;

public class BotActivityManager implements IManager {

    private boolean ready;

    public BotActivityManager(){
        setup();
    }

    @Override
    public void setup() {
        ready = true;
    }

    @Override
    public void kill() {
        ready = false;
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

    public void setBasicActivity(){
        int membersCount = CommonUtils.bot.getServers().stream().findFirst().get().getMemberCount();
        CommonUtils.bot.updateActivity(ActivityType.WATCHING, " for " + membersCount + " users here");
    }

    public void setActivity(ActivityType type, String message){
        CommonUtils.bot.updateActivity(type, message);
    }
}
