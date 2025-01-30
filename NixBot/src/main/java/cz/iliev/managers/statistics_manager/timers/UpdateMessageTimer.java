package cz.iliev.managers.statistics_manager.timers;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class UpdateMessageTimer extends TimerTask {

    private final String STATS_CHANNEL_ID = "1217475154582442086";
    private final int MAX_INDEX = 8;

    private Timer timer;
    private int index;

    public UpdateMessageTimer(){
        index = 0;
        timer = new Timer();
        timer.schedule(this, 0, TimeUnit.SECONDS.toMillis(5));
    }

    @Override
    public void run() {
        CommonUtils.getNixCrew().getTextChannelById(STATS_CHANNEL_ID).ifPresent(textChannel -> {
            Message msg = null;
            try {
                msg = textChannel.getMessageById(CommonUtils.settings.getStatsMessageId()).get();
            } catch (Exception e) {}
            if(msg == null){
                Message newMsg = textChannel.sendMessage(getEmbeds()).join();
                CommonUtils.settings.setStatsMessageId(newMsg.getId());
            }
            else {
                msg.createUpdater().removeAllEmbeds().addEmbeds(getEmbeds()).applyChanges();
            }
        });
    }

    public Timer getTimer(){
        return this.timer;
    }

    private List<EmbedBuilder> getEmbeds(){

        List<EmbedBuilder> output = new ArrayList<EmbedBuilder>();

        /*switch (index){
            case 0:
                var cmdMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getCommandStatsInstance().getMostCommandsUsedMonth());
                var cmdEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getCommandStatsInstance().getMostCommandsUsedEver());
                output.add(createTopStatisticsEmbed("Most command use", "", null, cmdMonth, cmdEver));
                break;
            case 1:
                var managerStats = CommonUtils.statisticsManager.getStatistics().getManagerStatsInstance();
                output.add(new EmbedBuilder()
                        .setColor(Color.decode("#2100FF"))
                        .setTitle("Manager statistics")
                        .addField("Tickets today", String.valueOf(managerStats.getTicketsDay()), true)
                        .addField("Tickets monthly", String.valueOf(managerStats.getTicketsMonth()), true)
                        .addField("Tickets ever", String.valueOf(managerStats.getTicketsEver()), true)
                        .addField("\u200B", "\u200B")
                        .addField("Temp channels monthly", String.valueOf(managerStats.getTemporaryChannelsMonth()), true)
                        .addField("Temp channels ever", String.valueOf(managerStats.getTemporaryChannelsEver()), true)
                        .addField("\u200B", "\u200B")
                        .addField("Music played monthly", String.valueOf(managerStats.getMusicPlayedMonth()), true)
                        .addField("Music played ever", String.valueOf(managerStats.getMusicPlayedEver()), true)
                        .addField("\u200B", "\u200B")
                        .addField("Music time today", String.valueOf(managerStats.getMusicTimePlayedDay()), true)
                        .addField("Music time monthly", String.valueOf(managerStats.getMusicTimePlayedMonth()), true)
                );
                break;
            case 2:
                var memberCallMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeMonth(), false);
                var memberCallEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeEver(), false);
                output.add(createTopStatisticsEmbed("Top connected members", "", null, memberCallMonth, memberCallEver));
                break;
            case 3:
                var memberTextMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeMonth(), true);
                var memberTextEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeEver(), true);
                output.add(createTopStatisticsEmbed("Top members text", "", null, memberTextMonth, memberTextEver));
                break;
            case 4:
                var memberCmdMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCommandsUsedMonth(), true);
                var memberCmdEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCommandsUsedEver(), true);
                output.add(createTopStatisticsEmbed("Top members commands", "", null, memberCmdMonth, memberCmdEver));
                break;
            case 5:
                var serverStats = CommonUtils.statisticsManager.getStatistics().getServerStatsInstance();
                output.add(new EmbedBuilder()
                        .setTitle("Server statistics")
                        .setColor(Color.decode("#2100FF"))
                        .addField("User+ today", String.valueOf(serverStats.getMemberJoinDay()), true)
                        .addField("User+ monthly", String.valueOf(serverStats.getMemberJoinMonth()), true)
                        .addField("User+ ever", String.valueOf(serverStats.getMemberJoinEver()), true)
                        .addField("\u200B", "\u200B")
                        .addField("User- today", String.valueOf(serverStats.getMemberLeaveDay()), true)
                        .addField("User- monthly", String.valueOf(serverStats.getMemberLeaveMonth()), true)
                        .addField("User- ever", String.valueOf(serverStats.getMemberLeaveEver()), true)
                        .setDescription("*User+* stands for user joins server\n*User-* stands for user leaves server")
                );
                break;
            case 6:
                var textStats = CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance();
                var textMonth = CommonUtils.statisticsManager.getTopFive(textStats.getUsedTextChannelIdMonth(), true);
                var textEver = CommonUtils.statisticsManager.getTopFive(textStats.getUsedTextChannelIdEver(), true);
                output.add(createTopStatisticsEmbed("Top text channels", "", null, textMonth, textEver));
                break;
            case 7:
                var voiceStats = CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance();
                var voiceMonth = CommonUtils.statisticsManager.getTopFive(voiceStats.getUsedVoiceChannelIdMonth(), true);
                var voiceEver = CommonUtils.statisticsManager.getTopFive(voiceStats.getUsedVoiceChannelIdEver(), true);
                output.add(createTopStatisticsEmbed("Top text channels", "", null, voiceMonth, voiceEver));
                break;
            case 8:
                var textStats2 = CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance();
                var voiceStats2 = CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance();
                output.add(new EmbedBuilder()
                        .setTitle("Text and voice statistics")
                        .setColor(Color.decode("#2100FF"))
                        .addField("Text today", String.valueOf(textStats2.getTextCounterDay()), true)
                        .addField("Text monthly", String.valueOf(textStats2.getTextCounterMonth()), true)
                        .addField("Text ever", String.valueOf(textStats2.getTextCounterEver()), true)
                        .addField("\u200B", "\u200B")
                        .addField("Voice today", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeDay()), true)
                        .addField("Voice monthly", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeMonth()), true)
                        .addField("Voice ever", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeEver()), true)
                );
                break;
        }*/

        output.forEach(embed -> {
            embed.setFooter("Index: " + index + " | Version: " + CommonUtils.VERSION);
        });

        if(index == MAX_INDEX)
            index = 0;
        else
            index++;

        return output;
    }

    private EmbedBuilder createTopStatisticsEmbed(String name, String description, ArrayList<String[]> day, ArrayList<String[]> month, ArrayList<String[]> ever){
        EmbedBuilder output = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description)
                .setColor(Color.decode("#2100FF"));

        if(day != null)
            output.addField("TODAY", "\u200B", true);
        if(month != null)
            output.addField("THIS MONTH", "\u200B", true);
        if(ever != null)
            output.addField("EVER", "\u200B", true);
        output.addField("\u200B", "\u200B", true);

        for(int i = 0; i < 5; i++) {
            if(day != null)
                if(i <= (day.size() - 1))
                    output.addField("#" + (i+1) + " " + day.get(i)[0], day.get(i)[1], true);
                else
                    output.addField("#" + (i+1) + " NaN", "NaN", true);
            if(month != null)
                if(i <= (month.size() - 1))
                    output.addField("#" + (i+1) + " " + month.get(i)[0], month.get(i)[1], true);
                else
                    output.addField("#" + (i+1) + " NaN", "NaN", true);
            if(ever != null)
                if(i <= (ever.size() - 1))
                    output.addField("#" + (i+1) + " " + ever.get(i)[0], ever.get(i)[1], true);
                else
                    output.addField("#" + (i+1) + " NaN", "NaN", true);

            output.addField("\u200B", "\u200B", true);
        }
        return output;
    }
}