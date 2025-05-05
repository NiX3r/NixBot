package cz.iliev.managers.security_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.weather_manager.utils.FileUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SecurityManager implements IManager {

    private boolean ready;

    public SecurityManager() { setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());

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
        return "SecurityManager";
    }

    @Override
    public String managerDescription() {
        return "Manager for determines users credibility. In case of low ELO ban user";
    }

    @Override
    public String color() {
        return "#eda81e";
    }
}
