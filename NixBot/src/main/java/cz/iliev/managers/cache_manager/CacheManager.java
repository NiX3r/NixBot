package cz.iliev.managers.cache_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.stay_fit_manager.instances.MemberFitInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CacheManager implements IManager {

    public HashMap<Long, Long> memberJoinVoice;

    private boolean ready;

    public CacheManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start CacheManager");
        memberJoinVoice = new HashMap<Long, Long>();
        CommonUtils.bot.getServers().forEach(server -> {
            if(!server.getIdAsString().equals(CommonUtils.NIX_CREW_ID)){
                CommonUtils.politeDisconnect(server);
                return;
            }
            server.getMembers().forEach(member -> {

                if(server.getConnectedVoiceChannel(member).isPresent()){
                    memberJoinVoice.put(member.getId(), server.getConnectedVoiceChannel(member).get().getId());
                }

            });
        });
        ready = true;
        LogUtils.info("CacheManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill CacheManager");
        ready = false;
        LogUtils.info("CacheManager killed");
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
        return "Cache manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to holds temporary data";
    }

    @Override
    public String color() {
        return "#1ae8cc";
    }
}
