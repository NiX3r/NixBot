package cz.nix3r.instances;

import cz.nix3r.utils.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

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

    private int currentDay;
    private int currentMonth;

    public StatisticsInstance(HashMap<Long, Long> usedTextChannelIdMonth, HashMap<Long, Long> usedTextChannelIdEver,
                              HashMap<Long, Long> usedVoiceChannelIdMonth, HashMap<Long, Long> usedVoiceChannelIdEver,
                              HashMap<Long, Long> bestUserCallTimeMonth, HashMap<Long, Long> bestUserCallTimeEver,
                              HashMap<Long, Long> bestUserTextCounterMonth, HashMap<Long, Long> bestUserTextCounterEver,
                              long callTimeDay, long callTimeMonth, long callTimeEver, long textCounterDay,
                              long textCounterMonth, long textCounterEver, int currentDay, int currentMonth) {
        this.usedTextChannelIdMonth = usedTextChannelIdMonth;
        this.usedTextChannelIdEver = usedTextChannelIdEver;
        this.usedVoiceChannelIdMonth = usedVoiceChannelIdMonth;
        this.usedVoiceChannelIdEver = usedVoiceChannelIdEver;
        this.bestUserCallTimeMonth = bestUserCallTimeMonth;
        this.bestUserCallTimeEver = bestUserCallTimeEver;
        this.bestUserTextCounterMonth = bestUserTextCounterMonth;
        this.bestUserTextCounterEver = bestUserTextCounterEver;
        this.callTimeDay = callTimeDay;
        this.callTimeMonth = callTimeMonth;
        this.callTimeEver = callTimeEver;
        this.textCounterDay = textCounterDay;
        this.textCounterMonth = textCounterMonth;
        this.textCounterEver = textCounterEver;
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

    public void incrementUsedVoiceChannelIdMonth(Long id) {
        checkCurrentDatetime();
        usedVoiceChannelIdMonth.merge(id, 1L, Long::sum);
    }

    public void incrementUsedVoiceChannelIdEver(Long id) {
        usedVoiceChannelIdEver.merge(id, 1L, Long::sum);
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

}