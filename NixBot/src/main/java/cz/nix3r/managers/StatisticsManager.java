package cz.nix3r.managers;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.StatisticsInstance;
import cz.nix3r.instances.UserChannelActivityInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;

import java.rmi.server.LogStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager {

    private StatisticsInstance statistics;
    private HashMap<Long, UserChannelActivityInstance> channelActivities;

    public StatisticsManager(){
        statistics = FileUtils.loadCurrentMonthStatistics();
        channelActivities = new HashMap<Long, UserChannelActivityInstance>();
        // Load users if there are any
        long now = System.currentTimeMillis();
        CommonUtils.bot.getServers().forEach(server -> {
            server.getVoiceChannels().forEach(voiceChannel -> {
                voiceChannel.getConnectedUserIds().forEach(userId -> {
                    channelActivities.put(userId, new UserChannelActivityInstance(userId, voiceChannel.getId(), now));
                });
            });
        });
    }

    public StatisticsInstance getStatistics(){ return statistics; }

    public HashMap<Long, UserChannelActivityInstance> getChannelActivities(){ return channelActivities; }

    public void incrementUsedTextChannelId(Long id){
        statistics.incrementUsedTextChannelIdMonth(id);
        statistics.incrementUsedTextChannelIdEver(id);
        LogSystem.log(LogType.INFO, "Used text channel id '" + id + "' increased");
    }

    public void incrementUsedVoiceChannelId(Long id, long toAdd){
        statistics.incrementUsedVoiceChannelIdMonth(id, toAdd);
        statistics.incrementUsedVoiceChannelIdEver(id, toAdd);
        LogSystem.log(LogType.INFO, "Used voice channel id '" + id + "' increased");
    }

    public void incrementBestUserCallTime(Long id, long toAdd){
        statistics.incrementBestUserCallTimeMonth(id, toAdd);
        statistics.incrementBestUserCallTimeEver(id, toAdd);
        LogSystem.log(LogType.INFO, "Best user id '" + id + "' call time increased by " + toAdd);
    }

    public void incrementBestUserTextCounterMonth(Long id){
        statistics.incrementBestUserTextCounterMonth(id);
        statistics.incrementBestUserTextCounterEver(id);
        LogSystem.log(LogType.INFO, "Best user id '" + id + "' text increased");
    }

    public void incrementCallTime(long toAdd){
        statistics.incrementCallTimeDay(toAdd);
        statistics.incrementCallTimeMonth(toAdd);
        statistics.incrementCallTimeEver(toAdd);
        LogSystem.log(LogType.INFO, "Total call time increased by " + toAdd);
    }

    public void incrementCommandsUsed(long userId, String commandName){
        statistics.incrementBestUserCommandsUsedMonth(userId);
        statistics.incrementBestUserCommandsUsedEver(userId);
        statistics.incrementMostCommandsUsedMonth(commandName);
        statistics.incrementMostCommandsUsedEver(commandName);
    }

    public void incrementMemberJoin(){
        statistics.incrementMemberJoinDay();
        statistics.incrementMemberJoinMonth();
        statistics.incrementMemberJoinEver();
    }

    public void incrementMemberLeave(){
        statistics.incrementMemberLeaveDay();
        statistics.incrementMemberLeaveMonth();
        statistics.incrementMemberLeaveEver();
    }

    public void incrementTextCounter(){
        statistics.incrementTextCounterDay();
        statistics.incrementTextCounterMonth();
        statistics.incrementTextCounterEver();
        LogSystem.log(LogType.INFO, "Total text increased");
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

    public ArrayList<String[]> getRestStatistics(){
        ArrayList<String[]> output = new ArrayList<String[]>();
        output.add(new String[]{ "Počet provolaných hodin tento den", formatTime(statistics.getCallTimeDay())});
        output.add(new String[]{ "Počet provolaných hodin tento měšís", formatTime(statistics.getCallTimeMonth())});
        output.add(new String[]{ "Počet provolaných hodin celkově", formatTime(statistics.getCallTimeDay())});
        output.add(new String[]{ "Počet napsaných zpráv tento den", statistics.getTextCounterDay() + ""});
        output.add(new String[]{ "Počet napsaných zpráv tento měsíc", statistics.getTextCounterMonth() + ""});
        output.add(new String[]{ "Počet napsaných zpráv celkově", statistics.getTextCounterEver() + ""});
        output.add(new String[]{ "Počet připojených uživatelů tento den", statistics.getMemberJoinDay() + ""});
        output.add(new String[]{ "Počet připojených uživatelů tento měsíc", statistics.getMemberJoinMonth() + ""});
        output.add(new String[]{ "Počet připojených uživatelů celkově", statistics.getMemberJoinEver() + ""});
        output.add(new String[]{ "Počet odpojených uživatelů tento den", statistics.getMemberLeaveDay() + ""});
        output.add(new String[]{ "Počet odpojených uživatelů tento měsíc", statistics.getMemberLeaveMonth() + ""});
        output.add(new String[]{ "Počet odpojených uživatelů celkově", statistics.getMemberLeaveEver() + ""});
        return output;
    }

    private String formatTime(long milliseconds) {
        String output = "";
        long temp = milliseconds;
        long hours = temp / 3600000;
        temp -= (hours * 3600000);
        long minutes = temp / 60000;
        temp -= (minutes * 60000);
        long seconds = temp / 1000;
        output = hours + "h " + minutes + "m " + seconds + "s";
        return output;
    }

}
