package cz.nix3r.instances;

public class StatisticsInstance {

    private long usedTextChannelIdEver;
    private long usedVoiceChannelIdMonth;
    private long usedVoiceChannelIdEver;
    private long callTimeDay;
    private long callTimeMonth;
    private long callTimeEver;
    private long textCounterDay;
    private long textCounterMonth;
    private long textCounterEver;
    private long bestUserCallTimeMonth;
    private long bestUserCallTimeEver;
    private long bestUserTextCounterMonth;
    private long bestUserTextCounterEver;

    private int currentDay;
    private int currentMonth;
    private int currentYear;

    public StatisticsInstance(long usedTextChannelIdEver, long usedVoiceChannelIdMonth, long usedVoiceChannelIdEver, long callTimeDay, long callTimeMonth, long callTimeEver, long textCounterDay, long textCounterMonth, long textCounterEver, long bestUserCallTimeMonth, long bestUserCallTimeEver, long bestUserTextCounterMonth, long bestUserTextCounterEver, int currentDay, int currentMonth, int currentYear) {
        this.usedTextChannelIdEver = usedTextChannelIdEver;
        this.usedVoiceChannelIdMonth = usedVoiceChannelIdMonth;
        this.usedVoiceChannelIdEver = usedVoiceChannelIdEver;
        this.callTimeDay = callTimeDay;
        this.callTimeMonth = callTimeMonth;
        this.callTimeEver = callTimeEver;
        this.textCounterDay = textCounterDay;
        this.textCounterMonth = textCounterMonth;
        this.textCounterEver = textCounterEver;
        this.bestUserCallTimeMonth = bestUserCallTimeMonth;
        this.bestUserCallTimeEver = bestUserCallTimeEver;
        this.bestUserTextCounterMonth = bestUserTextCounterMonth;
        this.bestUserTextCounterEver = bestUserTextCounterEver;
        this.currentDay = currentDay;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
    }

    public long getUsedTextChannelIdEver() {
        return usedTextChannelIdEver;
    }

    public long getUsedVoiceChannelIdMonth() {
        return usedVoiceChannelIdMonth;
    }

    public long getUsedVoiceChannelIdEver() {
        return usedVoiceChannelIdEver;
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

    public long getBestUserCallTimeMonth() {
        return bestUserCallTimeMonth;
    }

    public long getBestUserCallTimeEver() {
        return bestUserCallTimeEver;
    }

    public long getBestUserTextCounterMonth() {
        return bestUserTextCounterMonth;
    }

    public long getBestUserTextCounterEver() {
        return bestUserTextCounterEver;
    }

    public void incrementUsedTextChannelIdEver() {
        this.usedTextChannelIdEver++;
    }

    public void incrementUsedVoiceChannelIdMonth() {
        // Method is intentionally left empty
    }

    public void incrementUsedVoiceChannelIdEver() {
        this.usedVoiceChannelIdEver++;
    }

    public void incrementCallTimeDay() {
        // Method is intentionally left empty
    }

    public void incrementCallTimeMonth() {
        // Method is intentionally left empty
    }

    public void incrementCallTimeEver() {
        this.callTimeEver++;
    }

    public void incrementTextCounterDay() {
        // Method is intentionally left empty
    }

    public void incrementTextCounterMonth() {
        // Method is intentionally left empty
    }

    public void incrementTextCounterEver() {
        this.textCounterEver++;
    }

    public void incrementBestUserCallTimeMonth() {
        // Method is intentionally left empty
    }

    public void incrementBestUserCallTimeEver() {
        this.bestUserCallTimeEver++;
    }

    public void incrementBestUserTextCounterMonth() {
        // Method is intentionally left empty
    }

    public void incrementBestUserTextCounterEver() {
        this.bestUserTextCounterEver++;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}
