package cz.iliev.managers.database_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.database_manager.services.DatabaseCommonService;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class DatabaseManager implements IManager {

    private boolean ready;



    public DatabaseManager(){ setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start DatabaseManager");
        DatabaseCommonService.setupConnection(err -> {
            if(err != null)
                CommonUtils.throwException(err, true, false);
        });
        ready = true;
        LogUtils.info("DatabaseManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill DatabaseManager");
        ready = false;
        LogUtils.info("DatabaseManager killed");
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
        return false;
    }

    @Override
    public String managerName() {
        return "Database manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to store and access data via database server";
    }

    @Override
    public String color() {
        return "#57E3B7";
    }
}
