package cz.iliev.managers.main_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.main_manager.commands.ManagerCommand;
import cz.iliev.managers.main_manager.commands.StatusCommand;
import cz.iliev.managers.main_manager.listeners.MainManagerMessageComponentCreateListener;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.HashMap;

public class MainManager implements IManager {

    private boolean ready;
    private HashMap<String, IManager> managers;

    public MainManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start MainManager");
        managers = new HashMap<String, IManager>();
        managers.put("announcement", CommonUtils.announcementManager);
        managers.put("ban-list", CommonUtils.banListManager);
        managers.put("activity", CommonUtils.botActivityManager);
        managers.put("command", CommonUtils.commandManager);
        managers.put("console", CommonUtils.consoleCommandManager);
        managers.put("music", CommonUtils.musicManager);
        managers.put("role", CommonUtils.roleManager);
        managers.put("stats", CommonUtils.statisticsManager);
        managers.put("stay-fit", CommonUtils.stayFitManager);
        managers.put("temp", CommonUtils.temporaryChannelManager);
        managers.put("ticket", CommonUtils.ticketManager);
        managers.put("verification", CommonUtils.userVerificationManager);
        CommonUtils.bot.addMessageComponentCreateListener(new MainManagerMessageComponentCreateListener());
        ready = true;
        LogUtils.info("MainManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill MainManager");
        ready = false;
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
        switch (interaction.getCommandName()){
            case "status":
                new StatusCommand().run(interaction);
                break;
            case "manager":
                new ManagerCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {
        String command = (String)data;
        switch (command.split(" ")[1]){
            case "status": case "s":
                CommonUtils.bot.getServers().forEach(server -> {
                    if(!server.getIdAsString().equals(CommonUtils.NIX_CREW_ID)){
                        CommonUtils.politeDisconnect(server);
                        return;
                    }
                    server.getTextChannelById(AnnouncementManager.NIXBOT_CHANNEL_ID).ifPresent(channel -> {
                        StatusCommand.getStatusMessageBuilder().send(channel).join();
                    });
                });
                break;
            case "restart": case "r":
                String[] splitter = command.split(" ");
                if(splitter.length != 3){
                    LogUtils.warning("Bad command usage. Usage: 'announcements restart <time to restart>'");
                    return;
                }
                CommonUtils.announcementManager.sendRestart(splitter[2]);
                break;
            default:
                LogUtils.warning("Bad command usage. Command: announcement [status | restart] <data>");
                break;
        }
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "Main manager";
    }

    @Override
    public String managerDescription() {
        return "Manager for administrate other managers\nFeatures:\n- start/stop/restart other managers";
    }

    @Override
    public String color() {
        return "#7900FF";
    }

    public HashMap<String, IManager> getManagers(){
        return managers;
    }
}
