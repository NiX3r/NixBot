package cz.nix3r.instances;

import cz.nix3r.utils.FileUtils;

import java.util.*;

public class StatisticsInstance {

    // HashMap for most used text channels id of current month
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedTextChannelIdMonth;
    // HashMap for most used text channels id ever
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedTextChannelIdEver;
    // HashMap for most used voice channels id of current month
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedVoiceChannelIdMonth;
    // HashMap for most used voice channels id ever
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedVoiceChannelIdEver;
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
    // HashMap for most used commands month
    // Key = command name | Value = value
    private HashMap<String, Long> mostCommandsUsedMonth;
    // HashMap for most used commands ever
    // Key = command name | Value = value
    private HashMap<String, Long> mostCommandsUsedEver;
    // long for call time of current day
    private long callTimeDay;
    // long for call time of current month
    private long callTimeMonth;
    // long for call time ever
    private long callTimeEver;
    // long for text sent of current day
    private long textCounterDay;
    // long for text sent of current month
    private long textCounterMonth;
    // long for text sent ever
    private long textCounterEver;
    // int for members join day
    private int memberJoinDay;
    // int for members join month
    private int memberJoinMonth;
    // int for members join ever
    private int memberJoinEver;
    // int for members leave day;
    private int memberLeaveDay;
    // int for members leave month;
    private int memberLeaveMonth;
    // int for members leave ever;
    private int memberLeaveEver;

    private int currentDay;
    private int currentMonth;

    public StatisticsInstance(HashMap<Long, Long> usedTextChannelIdMonth, HashMap<Long, Long> usedTextChannelIdEver,
                              HashMap<Long, Long> usedVoiceChannelIdMonth, HashMap<Long, Long> usedVoiceChannelIdEver,
                              HashMap<Long, Long> bestUserCallTimeMonth, HashMap<Long, Long> bestUserCallTimeEver,
                              HashMap<Long, Long> bestUserTextCounterMonth, HashMap<Long, Long> bestUserTextCounterEver,
                              HashMap<Long, Long> bestUserCommandsUsedMonth, HashMap<Long, Long> bestUserCommandsUsedEver, HashMap<String, Long> mostCommandsUsedMonth, HashMap<String, Long> mostCommandsUsedEver, long callTimeDay, long callTimeMonth, long callTimeEver, long textCounterDay,
                              long textCounterMonth, long textCounterEver, int memberJoinDay, int memberJoinMonth, int memberJoinEver, int memberLeaveDay, int memberLeaveMonth, int memberLeaveEver, int currentDay, int currentMonth) {
        this.usedTextChannelIdMonth = usedTextChannelIdMonth;
        this.usedTextChannelIdEver = usedTextChannelIdEver;
        this.usedVoiceChannelIdMonth = usedVoiceChannelIdMonth;
        this.usedVoiceChannelIdEver = usedVoiceChannelIdEver;
        this.bestUserCallTimeMonth = bestUserCallTimeMonth;
        this.bestUserCallTimeEver = bestUserCallTimeEver;
        this.bestUserTextCounterMonth = bestUserTextCounterMonth;
        this.bestUserTextCounterEver = bestUserTextCounterEver;
        this.bestUserCommandsUsedMonth = bestUserCommandsUsedMonth;
        this.bestUserCommandsUsedEver = bestUserCommandsUsedEver;
        this.mostCommandsUsedMonth = mostCommandsUsedMonth;
        this.mostCommandsUsedEver = mostCommandsUsedEver;

        this.callTimeDay = callTimeDay;
        this.callTimeMonth = callTimeMonth;
        this.callTimeEver = callTimeEver;
        this.textCounterDay = textCounterDay;
        this.textCounterMonth = textCounterMonth;
        this.textCounterEver = textCounterEver;
        this.memberJoinDay = memberJoinDay;
        this.memberJoinMonth = memberJoinMonth;
        this.memberJoinEver = memberJoinEver;
        this.memberLeaveDay = memberLeaveDay;
        this.memberLeaveMonth = memberLeaveMonth;
        this.memberLeaveEver = memberLeaveEver;

        this.currentDay = currentDay;
        this.currentMonth = currentMonth;
    }

    public HashMap<Long, Long> getUsedTextChannelIdMonth() {
        return usedTextChannelIdMonth;
    }

    public HashMap<Long, Long> getUsedTextChannelIdEver() {
        return usedTextChannelIdEver;
    }

    public HashMap<Long, Long> getUsedVoiceChannelIdMonth() {
        return usedVoiceChannelIdMonth;
    }

    public HashMap<Long, Long> getUsedVoiceChannelIdEver() {
        return usedVoiceChannelIdEver;
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

    public HashMap<String, Long> getMostCommandsUsedMonth() {
        return mostCommandsUsedMonth;
    }

    public HashMap<String, Long> getMostCommandsUsedEver() {
        return mostCommandsUsedEver;
    }

    public long getCallTimeDay() {
        return callTimeDay;
    }

    public long getCallTimeMonth() {
        return callTimeMonth;
    }

    public long getCallTimeEver() {
        return callTimeEver;
    }

    public long getTextCounterDay() {
        return textCounterDay;
    }

    public long getTextCounterMonth() {
        return textCounterMonth;
    }

    public long getTextCounterEver() {
        return textCounterEver;
    }

    public void incrementUsedTextChannelIdMonth(Long id) {
        checkCurrentDatetime();
        usedTextChannelIdMonth.merge(id, 1L, Long::sum);
    }

    public void incrementUsedTextChannelIdEver(Long id) {
        usedTextChannelIdEver.merge(id, 1L, Long::sum);
    }

    public void incrementUsedVoiceChannelIdMonth(Long id, long toAdd) {
        checkCurrentDatetime();
        usedVoiceChannelIdMonth.merge(id, toAdd, Long::sum);
    }

    public void incrementUsedVoiceChannelIdEver(Long id, long toAdd) {
        usedVoiceChannelIdEver.merge(id, toAdd, Long::sum);
    }

    public void incrementBestUserCallTimeMonth(Long id, long toAdd) {
        checkCurrentDatetime();
        bestUserCallTimeMonth.merge(id, toAdd, Long::sum);
    }

    public void incrementBestUserCallTimeEver(Long id, long toAdd) {
        bestUserCallTimeEver.merge(id, toAdd, Long::sum);
    }

    public void incrementBestUserTextCounterMonth(Long id) {
        checkCurrentDatetime();
        bestUserTextCounterMonth.merge(id, 1L, Long::sum);
    }

    public void incrementBestUserTextCounterEver(Long id) {
        bestUserTextCounterEver.merge(id, 1L, Long::sum);
    }

    public void incrementBestUserCommandsUsedMonth(Long id){
        checkCurrentDatetime();
        bestUserCommandsUsedMonth.merge(id, 1L, Long::sum);
    }

    public void incrementBestUserCommandsUsedEver(Long id){
        bestUserCommandsUsedEver.merge(id, 1L, Long::sum);
    }

    public void incrementMostCommandsUsedMonth(String commandName){
        checkCurrentDatetime();
        mostCommandsUsedMonth.merge(commandName, 1L, Long::sum);
    }

    public void incrementMostCommandsUsedEver(String commandName){
        mostCommandsUsedEver.merge(commandName, 1L, Long::sum);
    }

    public void incrementCallTimeDay(long toAdd) {
        checkCurrentDatetime();
        callTimeDay += toAdd;
    }

    public void incrementCallTimeMonth(long toAdd) {
        checkCurrentDatetime();
        callTimeMonth += toAdd;
    }

    public void incrementCallTimeEver(long toAdd) {
        callTimeEver += toAdd;
    }

    public void incrementTextCounterDay() {
        checkCurrentDatetime();
        textCounterDay++;
    }

    public void incrementTextCounterMonth() {
        checkCurrentDatetime();
        textCounterMonth++;
    }

    public void incrementTextCounterEver() {
        textCounterEver++;
    }
    public void incrementMemberJoinDay() {
        memberJoinDay++;
    }
    public void incrementMemberJoinMonth() {
        memberJoinMonth++;
    }
    public void incrementMemberJoinEver() {
        memberJoinEver++;
    }
    public void incrementMemberLeaveDay() {
        memberLeaveDay++;
    }
    public void incrementMemberLeaveMonth() {
        memberLeaveMonth++;
    }
    public void incrementMemberLeaveEver() {
        memberLeaveEver++;
    }

    private void checkCurrentDatetime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if(currentMonth != calendar.get(Calendar.MONTH) + 1){
            FileUtils.saveStatistics();
            currentMonth = calendar.get(Calendar.YEAR);
            currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            resetMonth();
        }
        else if(currentDay != calendar.get(Calendar.DAY_OF_MONTH)){
            currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            resetDay();
        }
    }

    private void resetMonth(){
        usedTextChannelIdMonth = bestUserCallTimeMonth = usedVoiceChannelIdMonth =
                bestUserTextCounterMonth = new HashMap<Long, Long>();
        callTimeMonth = textCounterMonth = 0L;
        resetDay();
    }

    private void resetDay(){
        callTimeDay = textCounterDay = 0L;
    }

    public int getMemberJoinDay() {
        return memberJoinDay;
    }

    public int getMemberJoinMonth() {
        return memberJoinMonth;
    }

    public int getMemberJoinEver() {
        return memberJoinEver;
    }

    public int getMemberLeaveDay() {
        return memberLeaveDay;
    }

    public int getMemberLeaveMonth() {
        return memberLeaveMonth;
    }

    public int getMemberLeaveEver() {
        return memberLeaveEver;
    }
}