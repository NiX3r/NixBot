package cz.iliev.managers.command_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.command_manager.commands.AnonymousCommand;
import cz.iliev.managers.command_manager.commands.DiceCommand;
import cz.iliev.managers.command_manager.commands.PhoneticCommand;
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
        LogUtils.info("Load and start CommandManager");
        diceMessages = FileUtils.loadDiceMessages();
        noManagerCommands = new HashMap<String, ISlashCommand>();
        noManagerCommands.put("dice", new DiceCommand());
        noManagerCommands.put("anonymous", new AnonymousCommand());
        noManagerCommands.put("phonetic", new PhoneticCommand());
        CommonUtils.bot.addSlashCommandCreateListener(new CommandManagerSlashCommandCreateListener());
        ready = true;
        LogUtils.info("CommandManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill CommandManager");
        ready = false;
        LogUtils.info("CommandManager killed");
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

    public List<String> getDiceMessages(){
        return this.diceMessages;
    }

    public ISlashCommand getCommandByName(String name){
        return noManagerCommands.getOrDefault(name, null);
    }
}
