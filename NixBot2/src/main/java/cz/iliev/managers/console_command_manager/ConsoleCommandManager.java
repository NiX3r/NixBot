package cz.iliev.managers.console_command_manager;

import cz.iliev.interfaces.IManager;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ConsoleCommandManager implements IManager {

    private boolean ready;

    @Override
    public void setup() {

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
