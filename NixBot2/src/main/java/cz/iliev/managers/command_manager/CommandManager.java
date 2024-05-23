package cz.iliev.managers.command_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.command_manager.listeners.CommandManagerSlashCommandCreateListener;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class CommandManager implements IManager {

    private boolean ready;

    public static final String CMD_CHANNEL_ID = "1118284494198288445";

    public CommandManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start CommandManager");
        CommonUtils.bot.addSlashCommandCreateListener(new CommandManagerSlashCommandCreateListener());
        ready = true;
        LogUtils.info("CommandManager loaded and started. Ready to use");
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
