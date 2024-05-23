package cz.iliev.managers.main_manager;

import com.sun.tools.javac.Main;
import cz.iliev.interfaces.IManager;
import org.javacord.api.interaction.SlashCommandInteraction;

public class MainManager implements IManager {

    private boolean ready;

    public MainManager(){
        setup();
    }

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
