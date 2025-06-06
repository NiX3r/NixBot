package cz.iliev.managers.wordle_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class WordleManager implements IManager {

    private boolean ready;

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
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
        return "WordleManager";
    }

    @Override
    public String managerDescription() {
        return "Manager to play Wordle game";
    }

    @Override
    public String color() {
        return "#0a2ccc";
    }
}
