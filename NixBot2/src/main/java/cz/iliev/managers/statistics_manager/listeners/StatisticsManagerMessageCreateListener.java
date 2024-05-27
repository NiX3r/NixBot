package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class StatisticsManagerMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        if(messageCreateEvent.getMessage().getFlags().contains(MessageFlag.EPHEMERAL))
            return;

        CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance().incrementTextCounter();
        CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance().incrementUsedTextChannelId(messageCreateEvent.getChannel().getId());
        CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().incrementTextCounter(messageCreateEvent.getMessageAuthor().getId());

        LogUtils.info("Message statistics updated");

    }
}
