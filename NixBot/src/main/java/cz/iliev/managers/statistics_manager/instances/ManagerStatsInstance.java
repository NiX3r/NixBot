package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

public class ManagerStatsInstance {

    private long ticketsDay;
    private long ticketsMonth;
    private long ticketsEver;
    private long temporaryChannelsMonth;
    private long temporaryChannelsEver;
    private long musicPlayedMonth;
    private long musicPlayedEver;
    private long musicTimePlayedDay;
    private long musicTimePlayedMonth;

    public ManagerStatsInstance(long ticketsDay, long ticketsMonth, long ticketsEver, long temporaryChannelsMonth, long temporaryChannelsEver, long musicPlayedMonth, long musicPlayedEver, long musicTimePlayedDay, long musicTimePlayedMonth) {
        this.ticketsDay = ticketsDay;
        this.ticketsMonth = ticketsMonth;
        this.ticketsEver = ticketsEver;
        this.temporaryChannelsMonth = temporaryChannelsMonth;
        this.temporaryChannelsEver = temporaryChannelsEver;
        this.musicPlayedMonth = musicPlayedMonth;
        this.musicPlayedEver = musicPlayedEver;
        this.musicTimePlayedDay = musicTimePlayedDay;
        this.musicTimePlayedMonth = musicTimePlayedMonth;
    }

    public long getTicketsDay() {
        return ticketsDay;
    }

    public long getTicketsMonth() {
        return ticketsMonth;
    }

    public long getTemporaryChannelsMonth() {
        return temporaryChannelsMonth;
    }

    public long getTemporaryChannelsEver() {
        return temporaryChannelsEver;
    }

    public long getMusicPlayedMonth() {
        return musicPlayedMonth;
    }

    public long getMusicPlayedEver() {
        return musicPlayedEver;
    }

    public long getMusicTimePlayedDay() {
        return musicTimePlayedDay;
    }

    public long getMusicTimePlayedMonth() {
        return musicTimePlayedMonth;
    }

    public void incrementTickets(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        ticketsDay++;
        ticketsMonth++;
        ticketsEver++;
    }

    public void incrementTemporaryChannels(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        temporaryChannelsMonth++;
        temporaryChannelsEver++;
    }

    public void incrementMusicPlayed(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        musicPlayedMonth++;
        musicPlayedEver++;
    }

    public void incrementMusicTimePlayed(long increment){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        musicTimePlayedDay += increment;
        musicTimePlayedMonth += increment;
    }

    public long getTicketsEver() {
        return ticketsEver;
    }

    public void resetMonth(){
        ticketsMonth = 0;
        temporaryChannelsMonth = 0;
        musicTimePlayedMonth = 0;
        musicPlayedMonth = 0;
    }

    public void resetDay(){
        ticketsDay = 0;
        musicTimePlayedDay = 0;
    }
}
