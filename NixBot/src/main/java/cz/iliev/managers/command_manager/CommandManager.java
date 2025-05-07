package cz.iliev.managers.command_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.command_manager.commands.*;
import cz.iliev.managers.command_manager.listeners.CommandManagerSlashCommandCreateListener;
import cz.iliev.managers.command_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;
import java.util.List;

public class CommandManager implements IManager {

    private boolean ready;
    private List<String> diceMessages;
    private HashMap<String, ISlashCommand> noManagerCommands;

    public static final String CMD_CHANNEL_ID = "1118284494198288445";

    public CommandManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        diceMessages = FileUtils.loadDiceMessages();
        noManagerCommands = new HashMap<String, ISlashCommand>();
        noManagerCommands.put("dice", new DiceCommand());
        noManagerCommands.put("anonymous", new AnonymousCommand());
        noManagerCommands.put("phonetic", new PhoneticCommand());
        noManagerCommands.put("project", new ProjectCommand());
        noManagerCommands.put("summon", new SummonCommand());
        CommonUtils.bot.addSlashCommandCreateListener(new CommandManagerSlashCommandCreateListener());
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
        return "Command manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for catching commands and sending them to other managers";
    }

    @Override
    public String color() {
        return "#383838";
    }

    public List<String> getDiceMessages(){
        return this.diceMessages;
    }

    public ISlashCommand getCommandByName(String name){
        return noManagerCommands.getOrDefault(name, null);
    }
}
