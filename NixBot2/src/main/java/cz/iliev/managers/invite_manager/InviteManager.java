package cz.iliev.managers.invite_manager;

import cz.iliev.interfaces.IManager;
import org.javacord.api.interaction.SlashCommandInteraction;

public class InviteManager implements IManager {

    private boolean ready;

    public InviteManager(){
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
