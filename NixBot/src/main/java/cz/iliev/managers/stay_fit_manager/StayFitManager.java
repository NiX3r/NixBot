package cz.iliev.managers.stay_fit_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.stay_fit_manager.instances.MemberFitInstance;
import cz.iliev.managers.stay_fit_manager.utils.FileUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public class StayFitManager implements IManager {

    private boolean ready;
    private List<MemberFitInstance> memberFitList;

    public StayFitManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start StayFitManager");
        memberFitList = new ArrayList<MemberFitInstance>();
        ready = true;
        LogUtils.info("StayFitManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill StayFitManager");
        ready = false;
        LogUtils.info("StayFitManager killed");
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
        return "Stay fit manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to holds healthiness data\nFeatures: \n- Daily hydration data\n- TODO: Daily calories";
    }

    @Override
    public String color() {
        return "#29e53c";
    }

    public List<MemberFitInstance> getMemberFitList() {
        return memberFitList;
    }
}
