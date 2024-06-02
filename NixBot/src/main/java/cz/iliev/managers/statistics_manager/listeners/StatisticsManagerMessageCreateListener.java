package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class StatisticsManagerMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        // Break if bot is author
        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        // Break if it's ephemeral message
        if(messageCreateEvent.getMessage().getFlags().contains(MessageFlag.EPHEMERAL))
            return;

        // Break if it's PM
        if(!messageCreateEvent.getServerTextChannel().isPresent())
            return;

        // Break if it's in ticket category
        if(messageCreateEvent.getServerTextChannel().get().getCategory().isPresent() &&
            messageCreateEvent.getServerTextChannel().get().getCategory().get().getIdAsString().equals(TicketManager.TICKET_CATEGORY_ID))
            return;

        CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance().incrementTextCounter();
        CommonUtils.statisticsManager.getStatistics().getTextChannelStatsInstance().incrementUsedTextChannelId(messageCreateEvent.getChannel().getId());
        CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().incrementTextCounter(messageCreateEvent.getMessageAuthor().getId());

        LogUtils.info("Message statistics updated");

    }
}
