package cz.iliev.managers.console_command_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ConsoleCommandManager implements IManager {

    private boolean ready;

    @Override
    public void setup() {
        LogUtils.info("Load and start ConsoleCommandManager");
        LogUtils.info("ConsoleCommandManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill ConsoleCommandManager");
        LogUtils.info("ConsoleCommandManager killed");
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
}
