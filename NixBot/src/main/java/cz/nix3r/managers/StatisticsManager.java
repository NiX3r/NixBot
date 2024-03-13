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

    public void incrementUsedVoiceChannelId(Long id){
        statistics.incrementUsedVoiceChannelIdMonth(id);
        statistics.incrementUsedVoiceChannelIdEver(id);
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

    public void incrementTextCounter(){
        statistics.incrementTextCounterDay();
        statistics.incrementTextCounterMonth();
        statistics.incrementTextCounterEver();
        LogSystem.log(LogType.INFO, "Total text increased");
    }

    // Get top five used text channel
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

    public ArrayList<String[]> getRestStatistics(){
        ArrayList<String[]> output = new ArrayList<String[]>();
        output.add(new String[]{ "Počet provolaných hodin tento den", formatTime(statistics.getCallTimeDay())});
        return output;
    }

    private String formatTime(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH'h' mm'm' ss's'");
        return formatter.format(new Date(milliseconds));
    }

}
