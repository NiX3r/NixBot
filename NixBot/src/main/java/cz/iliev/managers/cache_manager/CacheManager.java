package cz.iliev.managers.cache_manager;

import cz.iliev.interfaces.IManager;
import org.javacord.api.interaction.SlashCommandInteraction;

public class CacheManager implements IManager {
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
        return false;
    }

    @Override
    public String managerName() {
        return "";
    }

    @Override
    public String managerDescription() {
        return "";
    }

    @Override
    public String color() {
        return "";
    }
}
