package cz.iliev.managers.statistics_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.statistics_manager.behaviors.CallTimeBehavior;
import cz.iliev.managers.statistics_manager.behaviors.UserCallTimeBehavior;
import cz.iliev.managers.statistics_manager.instances.StatisticsInstance;
import cz.iliev.managers.statistics_manager.listeners.*;
import cz.iliev.managers.statistics_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager implements IManager {

    private boolean ready;
    private StatisticsInstance statistics;
    private HashMap<Long, Long> userVoiceChannelJoinTime;
    private HashMap<Long, String> usersLastActivity;

    public StatisticsManager(){
        setup();
    }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        statistics = FileUtils.loadStatistics();
        usersLastActivity = FileUtils.loadUsersLastActivity();
        CommonUtils.bot.addMessageCreateListener(new StatisticsManagerMessageCreateListener());
        CommonUtils.bot.addServerMemberLeaveListener(new StatisticsManagerServerMemberLeaveListener());
        CommonUtils.bot.addServerVoiceChannelMemberJoinListener(new StatisticsManagerServerVoiceChannelMemberJoinListener());
        CommonUtils.bot.addServerVoiceChannelMemberLeaveListener(new StatisticsManagerServerVoiceChannelMemberLeaveListener());
        CommonUtils.bot.addReactionAddListener(new StatisticsReactionAddListener());
        CommonUtils.bot.addServerChangeBoostCountListener(new StatisticsServerChangeBoostCountListener());
        CommonUtils.bot.addUserChangeActivityListener(new StatisticsUserChangeActivityListener());
        userVoiceChannelJoinTime = new HashMap<>();

        

        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        for(long userId : userVoiceChannelJoinTime.keySet()){
            long increment = System.currentTimeMillis() - userVoiceChannelJoinTime.get(userId);
            UserCallTimeBehavior.behave(userId, increment);
            CallTimeBehavior.behave(increment);
        }
        userVoiceChannelJoinTime.clear();
        FileUtils.saveUsersLastActivity(usersLastActivity);
        FileUtils.saveStatistics(statistics);
        ready = false;
        LogUtils.info(managerName() + " killed");
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

    public HashMap<Long, String> getUsersLastActivity() {return this.usersLastActivity; }

    public String getUserLastActivityByUserId(long id){
        return this.usersLastActivity.getOrDefault(id, null);
    }

    public HashMap<Long, Long> getUserVoiceChannelJoinTime(){
        return userVoiceChannelJoinTime;
    }

    public void checkCurrentDatetime(){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Calendar statsTime = Calendar.getInstance();
        statsTime.setTimeInMillis(statistics.currentTime);
        if(now.get(Calendar.YEAR) != statsTime.get(Calendar.YEAR)){
            FileUtils.archiveStatistics(statistics);
            resetYear();
        }
        if(now.get(Calendar.MONTH) != statsTime.get(Calendar.MONTH)){
            FileUtils.archiveStatistics(statistics);
            resetMonth();
        }
        else if(now.get(Calendar.DAY_OF_MONTH) != statsTime.get(Calendar.DAY_OF_MONTH)){
            FileUtils.archiveStatistics(statistics);
            resetDay();
        }
    }

    private void resetYear(){
        statistics.boostYear = 0;
        statistics.commandUseYear.clear();
        statistics.memberJoinYear = 0;
        statistics.memberLeaveYear = 0;
        statistics.userActivityYear.clear();
        statistics.userCallTimeYear.clear();
        statistics.userCommandUseYear.clear();
        statistics.userReactionYear.clear();
        statistics.userTextYear.clear();
        statistics.voiceChannelYear.clear();
        resetMonth();
    }

    private void resetMonth(){
        statistics.boostMonth = 0;
        statistics.callTimeMonth = 0;
        statistics.commandUseMonth.clear();
        statistics.memberJoinMonth = 0;
        statistics.memberLeaveMonth = 0;
        statistics.musicPlayMonth = 0;
        statistics.musicPlayTimeMonth = 0;
        statistics.reactionMonth.clear();
        statistics.temporaryChannelMonth = 0;
        statistics.textMonth = 0;
        statistics.textChannelMonth.clear();
        statistics.ticketMonth = 0;
        statistics.userActivityMonth.clear();
        statistics.userCallTimeMonth.clear();
        statistics.userCommandUseMonth.clear();
        statistics.userReactionMonth.clear();
        statistics.userTextMonth.clear();
        statistics.voiceChannelMonth.clear();
        resetDay();
    }

    private void resetDay(){
        statistics.callTimeDay = 0;
        statistics.musicPlayTimeDay = 0;
        statistics.reactionDay.clear();
        statistics.textDay = 0;
        statistics.ticketDay = 0;
        statistics.userActivityTimeDay.clear();
        statistics.userCallTimeDay.clear();
        statistics.currentTime = System.currentTimeMillis();
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
            var server = CommonUtils.getNixCrew();
            server.getTextChannelById(item[0]).ifPresent(serverTextChannel -> { s[0] = serverTextChannel.getName(); });
            server.getVoiceChannelById(item[0]).ifPresent(serverVoiceChannel -> { s[0] = serverVoiceChannel.getName(); });
            server.getMemberById(item[0]).ifPresent(member -> { s[0] = member.getName(); });
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
