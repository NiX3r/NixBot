package cz.iliev.managers.console_command_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.console_command_manager.threads.ConsoleCommandManagerThread;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ConsoleCommandManager implements IManager {

    private boolean ready;
    private ConsoleCommandManagerThread thread;

    public ConsoleCommandManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start ConsoleCommandManager");
        thread = new ConsoleCommandManagerThread();
        thread.start();
        ready = true;
        LogUtils.info("ConsoleCommandManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill ConsoleCommandManager");
        ready = false;
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
        return "Console command manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for catching console commands and sending them to other managers";
    }

    @Override
    public String color() {
        return "#caccc9";
    }
}
