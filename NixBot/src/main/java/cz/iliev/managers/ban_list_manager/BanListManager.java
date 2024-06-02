package cz.iliev.managers.ban_list_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.ban_list_manager.instances.BanInstance;
import cz.iliev.managers.ban_list_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.List;

public class BanListManager implements IManager {

    private boolean ready;
    private List<BanInstance> bans;

    public BanListManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start AnnouncementManager");
        bans = FileUtils.loadBans();
        ready = true;
        LogUtils.info("AnnouncementManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        FileUtils.saveBans(bans);
        ready = false;
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
        return "Ban list manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to view ban list\nFeatures: \n- Bans\n- Unbans\n- Kicks\n- Mutes";
    }

    @Override
    public String color() {
        return "#8c0404";
    }

    public void addBan(BanInstance ban){
        bans.add(ban);
    }
}
