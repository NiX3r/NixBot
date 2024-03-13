package cz.nix3r.timers;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

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
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
                if(msg == null){
                    Message newMsg = textChannel.sendMessage(getEmbed()).join();
                    CommonUtils.settings.setStatsMessageId(newMsg.getId());
                }
                else {
                    msg.createUpdater().removeAllEmbeds().addEmbed(getEmbed()).applyChanges();
                }
            });
        });
    }

    private EmbedBuilder getEmbed(){
        switch (index){
            case 0:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější textový kanál tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedTextChannelIdMonth(), true));
            case 1:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější textový tento celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedTextChannelIdEver(), true));
            case 2:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější hlasový kanál tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedVoiceChannelIdMonth(), false));
            case 3:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejpoužívanější hlasový kanál celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getUsedVoiceChannelIdEver(), false));
            case 4:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejvíce připojený uživatel tento měsíc", "Uživatelé, kteří se nejvíce připojovali k serveru za poslední měsíc", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCallTimeMonth(), false));
            case 5:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Nejvíce připojený uživatel celkově", "Uživatelé, kteří se nejvíce připojovali k serveru celkově", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserCallTimeEver(), false));
            case 6:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce zprávy tento měsíc", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserTextCounterMonth(), true));
            case 7:
                index++;
                return DiscordUtils.createTopStatisticsEmbed("Uživatel s nejvíce zprávy celkově", "", CommonUtils.statisticsManager.getTopFive(CommonUtils.statisticsManager.getStatistics().getBestUserTextCounterEver(), true));
            case 8:
                index = 0;
                return DiscordUtils.createStatisticEmbed(CommonUtils.statisticsManager.getRestStatistics());
        }
        return null;
    }
}
