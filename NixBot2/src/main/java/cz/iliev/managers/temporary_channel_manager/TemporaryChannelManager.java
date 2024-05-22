package cz.iliev.managers.temporary_channel_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;

public class TemporaryChannelManager implements IManager {

    // Key = creator ID | Value = channel ID
    private HashMap<Long, Long> tempVoiceChannelMap;
    private boolean ready;

    @Override
    public void setup() {
        LogUtils.info("Load and start TemporaryManager");

        LogUtils.info("TemporaryManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {

    }

    @Override
    public boolean restart() {
        return false;
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
}
