package cz.iliev.managers.main_manager;

import com.sun.tools.javac.Main;
import cz.iliev.interfaces.IManager;
import cz.iliev.managers.main_manager.commands.StatusCommand;
import cz.iliev.managers.role_manager.commands.MessageCommand;
import cz.iliev.managers.role_manager.commands.SetCommand;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class MainManager implements IManager {

    private boolean ready;

    @Override
    public void setup() {
        LogUtils.info("Load and start MainManager");
        LogUtils.info("MainManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill MainManager");
        LogUtils.info("MainManager killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        switch (interaction.getOptions().get(0).getName()){
            case "status":
                new StatusCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }
}
