package cz.iliev.managers.temporary_channel_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.temporary_channel_manager.listeners.TemporaryChannelServerVoiceChannelMemberJoinListener;
import cz.iliev.managers.temporary_channel_manager.listeners.TemporaryChannelServerVoiceChannelMemberLeaveListener;
import cz.iliev.managers.temporary_channel_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;

public class TemporaryChannelManager implements IManager {

    // Key = creator ID | Value = channel ID
    private HashMap<Long, Long> tempVoiceChannelMap;
    private boolean ready;

    public static final String TEMP_CHANNEL_ID = "1118311195867369513";
    public static final String TEMP_CATEGORY_ID = "1118291032065441882";

    public TemporaryChannelManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start TemporaryManager");
        tempVoiceChannelMap = FileUtils.loadTemporaryChannelCache();
        CommonUtils.bot.addServerVoiceChannelMemberJoinListener(new TemporaryChannelServerVoiceChannelMemberJoinListener());
        CommonUtils.bot.addServerVoiceChannelMemberLeaveListener(new TemporaryChannelServerVoiceChannelMemberLeaveListener());
        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        FileUtils.saveTemporaryChannelCache(tempVoiceChannelMap);
        ready = false;
        LogUtils.info(managerName() + " killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {

    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "Temporary channel manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to create temporary channels\nFeatures: \n- Create channel by joining specific channel\n- After create temp channel user get full permissions to it";
    }

    @Override
    public String color() {
        return "e0dd2a";
    }

    public HashMap<Long, Long> getTempVoiceChannelMap(){
        return this.tempVoiceChannelMap;
    }
}
