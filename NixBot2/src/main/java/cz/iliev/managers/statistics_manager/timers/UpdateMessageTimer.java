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
    private Timer timer;
    private int index;

    public UpdateMessageTimer(){
        index = 0;
        timer = new Timer();
        timer.schedule(this, 0, TimeUnit.SECONDS.toMillis(20));
    }

    @Override
    public void run() {
        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(STATS_CHANNEL_ID).ifPresent(textChannel -> {
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
        });
    }

    public Timer getTimer(){
        return this.timer;
    }

    private List<EmbedBuilder> getEmbeds(){

        List<EmbedBuilder> output = new ArrayList<EmbedBuilder>();

        switch (index){
            case 0:
                index++;
                var cmdMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getCommandStatsInstance().getMostCommandsUsedMonth());
                var cmdEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getCommandStatsInstance().getMostCommandsUsedEver());
                output.add(createTopStatisticsEmbed("Most command use", "", null, cmdMonth, cmdEver));
                break;
            case 1:
                index++;
                var managerStats = CommonUtils.statisticsManager.getStatistics().getManagerStatsInstance();
                output.add(new EmbedBuilder()
                        .setColor(Color.decode("#2100FF"))
                        .setTitle("Manager statistics")
                        .addField("Tickets today", String.valueOf(managerStats.getTicketsDay()), true)
                        .addField("Tickets this month", String.valueOf(managerStats.getTicketsMonth()), true)
                        .addField("Tickets ever", String.valueOf(managerStats.getTicketsEver()), true)
                        .addField("Temporary channels this month", String.valueOf(managerStats.getTemporaryChannelsMonth()), false)
                        .addField("Temporary channels ever", String.valueOf(managerStats.getTemporaryChannelsEver()), true)
                        .addField("Music played this month", String.valueOf(managerStats.getMusicPlayedMonth()), false)
                        .addField("Music played ever", String.valueOf(managerStats.getMusicPlayedEver()), true)
                        .addField("Music time today", String.valueOf(managerStats.getMusicTimePlayedDay()), false)
                        .addField("Music time this month", String.valueOf(managerStats.getMusicTimePlayedMonth()), true)
                );
                break;
            case 2:
                index++;
                var memberCallMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeMonth(), false);
                var memberCallEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeEver(), false);
                output.add(createTopStatisticsEmbed("Top connected members", "", null, memberCallMonth, memberCallEver));
                break;
            case 3:
                index++;
                var memberTextMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeMonth(), true);
                var memberTextEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCallTimeEver(), true);
                output.add(createTopStatisticsEmbed("Top members text", "", null, memberTextMonth, memberTextEver));
                break;
            case 4:
                index++;
                var memberCmdMonth = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCommandsUsedMonth(), false);
                var memberCmdEver = CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().getBestUserCommandsUsedEver(), false);
                output.add(createTopStatisticsEmbed("Top members commands", "", null, memberCmdMonth, memberCmdEver));
                break;
            case 5:
                index++;
                var serverStats = CommonUtils.statisticsManager.getStatistics().getServerStatsInstance();
                output.add(new EmbedBuilder()
                        .setTitle("Server statistics")
                        .setColor(Color.decode("#2100FF"))
                        .addField("Member join today", String.valueOf(serverStats.getMemberJoinDay()), true)
                        .addField("Member join monthly", String.valueOf(serverStats.getMemberJoinMonth()))
                        .addField("Member join ever", String.valueOf(serverStats.getMemberJoinEver()), false)
                        .addField("Member leave today", String.valueOf(serverStats.getMemberLeaveDay()), true)
                        .addField("Member leave monthly", String.valueOf(serverStats.getMemberLeaveMonth()))
                        .addField("Member leave ever", String.valueOf(serverStats.getMemberLeaveEver()), false)
                );
                break;
            case 6:
                index++;
                var textStats = CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance();
                var textMonth = CommonUtils.statisticsManager.getTopFive(textStats.getUsedTextChannelIdMonth(), true);
                var textEver = CommonUtils.statisticsManager.getTopFive(textStats.getUsedTextChannelIdEver(), true);
                output.add(createTopStatisticsEmbed("Top text channels", "", null, textMonth, textEver));
                break;
            case 7:
                index++;
                var voiceStats = CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance();
                var voiceMonth = CommonUtils.statisticsManager.getTopFive(voiceStats.getUsedVoiceChannelIdMonth(), true);
                var voiceEver = CommonUtils.statisticsManager.getTopFive(voiceStats.getUsedVoiceChannelIdEver(), true);
                output.add(createTopStatisticsEmbed("Top text channels", "", null, voiceMonth, voiceEver));
                break;
            case 8:
                index++;
                var textStats2 = CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance();
                var voiceStats2 = CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance();
                output.add(new EmbedBuilder()
                        .setTitle("Text and voice statistics")
                        .setColor(Color.decode("#2100FF"))
                        .addField("Text today", String.valueOf(textStats2.getTextCounterDay()), true)
                        .addField("Text monthly", String.valueOf(textStats2.getTextCounterMonth()))
                        .addField("Text ever", String.valueOf(textStats2.getTextCounterEver()), false)
                        .addField("Voice today", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeDay()), true)
                        .addField("Voice monthly", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeMonth()))
                        .addField("Voice ever", CommonUtils.statisticsManager.formatTime(voiceStats2.getCallTimeEver()), false)
                );
                break;
        }
        return output;
    }

    private EmbedBuilder createTopStatisticsEmbed(String name, String description, ArrayList<String[]> day, ArrayList<String[]> month, ArrayList<String[]> ever){
        EmbedBuilder output = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description)
                .setColor(Color.decode("#2100FF"));
        for(int i = 0; i < 5; i++) {
            if(day != null)
                output.addField("#" + (i+1) + " DAY " + day.get(i)[0], day.get(i)[1], month == null && ever == null);
            if(month != null)
                output.addField("#" + (i+1) + " MONTH " + month.get(i)[0], month.get(i)[1], ever == null);
            if(ever != null)
                output.addField("#" + (i+1) + " EVER " + ever.get(i)[0], ever.get(i)[1], false);
        }
        return output;
    }
}