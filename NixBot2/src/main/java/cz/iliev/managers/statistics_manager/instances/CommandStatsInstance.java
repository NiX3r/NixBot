package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

import java.util.HashMap;

public class CommandStatsInstance {

    // HashMap for most used commands month
    // Key = command name | Value = value
    private HashMap<String, Long> mostCommandsUsedMonth;
    // HashMap for most used commands ever
    // Key = command name | Value = value
    private HashMap<String, Long> mostCommandsUsedEver;

    public CommandStatsInstance(HashMap<String, Long> mostCommandsUsedMonth, HashMap<String, Long> mostCommandsUsedEver) {
        this.mostCommandsUsedMonth = mostCommandsUsedMonth;
        this.mostCommandsUsedEver = mostCommandsUsedEver;
    }

    public HashMap<String, Long> getMostCommandsUsedMonth() {
        return mostCommandsUsedMonth;
    }

    public HashMap<String, Long> getMostCommandsUsedEver() {
        return mostCommandsUsedEver;
    }

    public void incrementUsedCommand(String name){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        mostCommandsUsedMonth.merge(name, 1L, Long::sum);
        mostCommandsUsedEver.merge(name, 1L, Long::sum);
    }
}
