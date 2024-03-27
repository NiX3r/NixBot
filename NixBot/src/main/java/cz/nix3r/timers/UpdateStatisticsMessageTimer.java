package cz.nix3r.timers;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class UpdateStatisticsMessageTimer extends TimerTask {

    private Timer timer;
    private int index;

    public UpdateStatisticsMessageTimer(){
        index = 0;
        timer = new Timer();
        timer.schedule(this, 0, TimeUnit.SECONDS.toMillis(20));
    }

    @Override
    public void run() {
        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommonUtils.STATS_CHANNEL_ID).ifPresent(textChannel -> {
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

    private List<EmbedBuilder> getEmbeds(){

        List<EmbedBuilder> output = new ArrayList<EmbedBuilder>();

        switch (index){
            case 0:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější textový kanál tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedTextChannelIdMonth(), true)));
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější textový tento celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedTextChannelIdEver(), true)));
                break;
            case 1:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější hlasový kanál tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedVoiceChannelIdMonth(), false)));
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější hlasový kanál celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedVoiceChannelIdEver(), false)));
                break;
            case 2:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejvíce připojený uživatel tento měsíc", "Uživatelé, kteří se nejvíce připojovali k serveru za poslední měsíc", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCallTimeMonth(), false)));
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejvíce připojený uživatel celkově", "Uživatelé, kteří se nejvíce připojovali k serveru celkově", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCallTimeEver(), false)));
                break;
            case 3:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce zprávy tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserTextCounterMonth(), true)));
                output.add(DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce zprávy celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserTextCounterEver(), true)));
                break;
            case 4:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce použitými příkazy tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCommandsUsedMonth(), true)));
                output.add(DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce použitými příkazy celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCommandsUsedEver(), true)));
                break;
            case 5:
                index++;
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejvíce použitý příkazy tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMostCommandsUsedMonth())));
                output.add(DiscordUtils.createTopStatisticsEmbed("Nejvíce použitý příkazy celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getMostCommandsUsedEver())));
                break;
            case 6:
                index = 0;
                output = DiscordUtils.createStatisticEmbed(CommonUtils.statisticsManager.getRestStatistics());
                break;
        }
        return output;
    }
}
