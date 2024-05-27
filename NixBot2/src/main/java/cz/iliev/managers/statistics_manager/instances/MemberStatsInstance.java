package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

import java.util.HashMap;

public class MemberStatsInstance {

    // HashMap for best users call time of month
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserCallTimeMonth;
    // HashMap for best users call time ever
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserCallTimeEver;
    // HashMap for best users text sent of month
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserTextCounterMonth;
    // HashMap for best users text sent ever
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserTextCounterEver;
    // HashMap for best users commands used month
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserCommandsUsedMonth;
    // HashMap for best users commands used ever
    // Key = user id | Value = value
    private HashMap<Long, Long> bestUserCommandsUsedEver;

    public MemberStatsInstance(HashMap<Long, Long> bestUserCallTimeMonth, HashMap<Long, Long> bestUserCallTimeEver, HashMap<Long, Long> bestUserTextCounterMonth, HashMap<Long, Long> bestUserTextCounterEver, HashMap<Long, Long> bestUserCommandsUsedMonth, HashMap<Long, Long> bestUserCommandsUsedEver) {
        this.bestUserCallTimeMonth = bestUserCallTimeMonth;
        this.bestUserCallTimeEver = bestUserCallTimeEver;
        this.bestUserTextCounterMonth = bestUserTextCounterMonth;
        this.bestUserTextCounterEver = bestUserTextCounterEver;
        this.bestUserCommandsUsedMonth = bestUserCommandsUsedMonth;
        this.bestUserCommandsUsedEver = bestUserCommandsUsedEver;
    }

    public HashMap<Long, Long> getBestUserCallTimeMonth() {
        return bestUserCallTimeMonth;
    }

    public HashMap<Long, Long> getBestUserCallTimeEver() {
        return bestUserCallTimeEver;
    }

    public HashMap<Long, Long> getBestUserTextCounterMonth() {
        return bestUserTextCounterMonth;
    }

    public HashMap<Long, Long> getBestUserTextCounterEver() {
        return bestUserTextCounterEver;
    }

    public HashMap<Long, Long> getBestUserCommandsUsedMonth() {
        return bestUserCommandsUsedMonth;
    }

    public HashMap<Long, Long> getBestUserCommandsUsedEver() {
        return bestUserCommandsUsedEver;
    }

    public void incrementCallTime(long userId, long increment){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        bestUserCallTimeMonth.merge(userId, increment, Long::sum);
        bestUserCallTimeEver.merge(userId, increment, Long::sum);
    }

    public void incrementTextCounter(long userId){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        bestUserTextCounterMonth.merge(userId, 1L, Long::sum);
        bestUserTextCounterEver.merge(userId, 1L, Long::sum);
    }

    public void incrementUsedCommands(long userId){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        bestUserCommandsUsedMonth.merge(userId, 1L, Long::sum);
        bestUserCommandsUsedEver.merge(userId, 1L, Long::sum);
    }

    public void resetMonth(){
        bestUserCommandsUsedMonth.clear();
        bestUserTextCounterMonth.clear();
        bestUserCallTimeMonth.clear();
    }
}
