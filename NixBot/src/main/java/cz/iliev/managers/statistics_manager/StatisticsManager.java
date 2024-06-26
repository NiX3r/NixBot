package cz.iliev.managers.statistics_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.statistics_manager.instances.StatisticsInstance;
import cz.iliev.managers.statistics_manager.listeners.StatisticsManagerMessageCreateListener;
import cz.iliev.managers.statistics_manager.listeners.StatisticsManagerServerMemberLeaveListener;
import cz.iliev.managers.statistics_manager.listeners.StatisticsManagerServerVoiceChannelMemberJoinListener;
import cz.iliev.managers.statistics_manager.listeners.StatisticsManagerServerVoiceChannelMemberLeaveListener;
import cz.iliev.managers.statistics_manager.timers.UpdateMessageTimer;
import cz.iliev.managers.statistics_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager implements IManager {

    private boolean ready;
    private StatisticsInstance statistics;
    private UpdateMessageTimer timer;

    public StatisticsManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start StatisticsManager");
        statistics = FileUtils.loadStatistics();
        CommonUtils.bot.addMessageCreateListener(new StatisticsManagerMessageCreateListener());
        CommonUtils.bot.addServerMemberLeaveListener(new StatisticsManagerServerMemberLeaveListener());
        CommonUtils.bot.addServerVoiceChannelMemberJoinListener(new StatisticsManagerServerVoiceChannelMemberJoinListener());
        CommonUtils.bot.addServerVoiceChannelMemberLeaveListener(new StatisticsManagerServerVoiceChannelMemberLeaveListener());
        timer = new UpdateMessageTimer();
        ready = true;
        LogUtils.info("StatisticsManager loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill StatisticsManager");
        FileUtils.saveStatistics(statistics);
        timer.cancel();
        timer = null;
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
        return;
    }

    @Override
    public void onConsoleCommand(Object data) {
        return;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "Statistics manager";
    }

    @Override
    public String managerDescription() {
        return "Manager to calculate statistics\nFeatures: \n- Command statistics\n- Manager statistics\n- Member statistics\n- Server statistics\n- Text channel statistics\n- Voice channel statistics";
    }

    @Override
    public String color() {
        return "#29b9e5";
    }

    public StatisticsInstance getStatistics(){
        return this.statistics;
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
            FileUtils.archiveStatistics(statistics);
            resetDay();
        }
    }

    private void resetMonth(){
        statistics.getCommandStatsInstance().resetMonth();
        statistics.getManagerStatsInstance().resetMonth();
        statistics.getMemberStatsInstance().resetMonth();
        statistics.getServerStatsInstance().resetMonth();
        statistics.getTextChannelStatsInstance().resetMonth();
        statistics.getVoiceChannelStatsInstance().resetMonth();
        resetDay();
    }

    private void resetDay(){
        statistics.getManagerStatsInstance().resetDay();
        statistics.getServerStatsInstance().resetDay();
        statistics.getTextChannelStatsInstance().resetDay();
        statistics.getVoiceChannelStatsInstance().resetDay();
        statistics.setCurrentTime(System.currentTimeMillis());
    }

    // Get top five used text channel || voice channels || members
    // Returns array list of long array
    // long array contains [0] = channel id, [1] = value
    public ArrayList<String[]> getTopFive(HashMap<Long, Long> toSort, boolean plainValue){
        List<Map.Entry<Long, Long>> sortedEntries = toSort.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
        ArrayList<long[]> topEntries = new ArrayList<>();
        ArrayList<String[]> output = new ArrayList<String[]>();
        int index = 0;
        for (Map.Entry<Long, Long> entry : sortedEntries) {
            if(index < 5){
                topEntries.add(new long[]{entry.getKey(), entry.getValue()});
                index++;
            }
            else
                break;
        }
        for(long[] item : topEntries){
            String[] s = new String[2];
            CommonUtils.bot.getServers().forEach(server -> {
                server.getTextChannelById(item[0]).ifPresent(serverTextChannel -> { s[0] = serverTextChannel.getName(); });
                server.getVoiceChannelById(item[0]).ifPresent(serverVoiceChannel -> { s[0] = serverVoiceChannel.getName(); });
                server.getMemberById(item[0]).ifPresent(member -> { s[0] = member.getName(); });
            });
            if(plainValue)
                s[1] = item[1] + "";
            else
                s[1] = formatTime(item[1]);
            output.add(s);
        }
        return output;
    }

    // Get top five used commands
    // Returns array list of long array
    // long array contains [0] = channel id, [1] = value
    public ArrayList<String[]> getTopFive(HashMap<String, Long> toSort){
        List<Map.Entry<String, Long>> sortedEntries = toSort.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
        ArrayList<String[]> topEntries = new ArrayList<>();
        ArrayList<String[]> output = new ArrayList<String[]>();
        int index = 0;
        for (Map.Entry<String, Long> entry : sortedEntries) {
            if(index < 5){
                topEntries.add(new String[]{entry.getKey(), entry.getValue().toString()});
                index++;
            }
            else
                break;
        }
        for(String[] item : topEntries){
            String[] s = new String[2];
            s[0] = "/" + item[0];
            s[1] = item[1] + "";
            output.add(s);
        }
        return output;
    }

    public String formatTime(long milliseconds) {
        String output = "";
        long temp = milliseconds;
        long days = temp / 86400000;
        temp -= (days * 86400000);
        long hours = temp / 3600000;
        temp -= (hours * 3600000);
        long minutes = temp / 60000;
        temp -= (minutes * 60000);
        long seconds = temp / 1000;
        output = (days > 0 ? (days + "d ") : "") + (hours > 0 ? (hours + "h ") : "") + minutes + "m " + seconds + "s";
        return output;
    }
}
