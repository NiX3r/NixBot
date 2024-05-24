package cz.iliev.managers.statistics_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.statistics_manager.instances.StatisticsInstance;
import cz.iliev.managers.statistics_manager.utils.FileUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Calendar;
import java.util.HashMap;

public class StatisticsManager implements IManager {

    private boolean ready;
    private StatisticsInstance statistics;

    @Override
    public void setup() {
        LogUtils.info("Load and start StatisticsManager");
        statistics = FileUtils.loadStatistics();
        ready = true;
        LogUtils.info("StatisticsManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill StatisticsManager");
        ready = false;
        LogUtils.info("StatisticsManager killed");
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

    public void checkCurrentDatetime(){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Calendar statsTime = Calendar.getInstance();
        statsTime.setTimeInMillis(statistics.getCurrentTime());
        if(now.get(Calendar.MONTH) != statsTime.get(Calendar.MONTH)){
            FileUtils.archiveStatistics(statistics);
            resetMonth();
        }
        else if(now.get(Calendar.DAY_OF_MONTH) != statsTime.get(Calendar.DAY_OF_MONTH)){
            resetDay();
        }
    }

    private void resetMonth(){
        // TODO
        resetDay();
    }

    private void resetDay(){
        // TODO
        statistics.setCurrentTime(System.currentTimeMillis());
    }
}
