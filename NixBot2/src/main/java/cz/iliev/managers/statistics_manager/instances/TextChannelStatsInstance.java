package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

import java.util.HashMap;

public class TextChannelStatsInstance {
    // HashMap for most used text channels id of current month
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedTextChannelIdMonth;
    // HashMap for most used text channels id ever
    // Key = channel id | Value = value
    private HashMap<Long, Long> usedTextChannelIdEver;
    // long for text sent of current day
    private long textCounterDay;
    // long for text sent of current month
    private long textCounterMonth;
    // long for text sent ever
    private long textCounterEver;

    public TextChannelStatsInstance(HashMap<Long, Long> usedTextChannelIdMonth, HashMap<Long, Long> usedTextChannelIdEver, long textCounterDay, long textCounterMonth, long textCounterEver) {
        this.usedTextChannelIdMonth = usedTextChannelIdMonth;
        this.usedTextChannelIdEver = usedTextChannelIdEver;
        this.textCounterDay = textCounterDay;
        this.textCounterMonth = textCounterMonth;
        this.textCounterEver = textCounterEver;
    }

    public HashMap<Long, Long> getUsedTextChannelIdMonth() {
        return usedTextChannelIdMonth;
    }

    public HashMap<Long, Long> getUsedTextChannelIdEver() {
        return usedTextChannelIdEver;
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

    public void incrementUsedTextChannelId(Long id) {
        CommonUtils.statisticsManager.checkCurrentDatetime();
        usedTextChannelIdMonth.merge(id, 1L, Long::sum);
        usedTextChannelIdEver.merge(id, 1L, Long::sum);
    }

    public void incrementTextCounter(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        textCounterDay++;
        textCounterMonth++;
        textCounterEver++;
    }

    public void resetMonth(){
        textCounterMonth = 0;
        usedTextChannelIdMonth.clear();
    }

    public void resetDay(){
        textCounterDay = 0;
    }
}
