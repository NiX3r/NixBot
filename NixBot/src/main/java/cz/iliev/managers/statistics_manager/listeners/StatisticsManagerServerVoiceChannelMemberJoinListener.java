package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.temporary_channel_manager.TemporaryChannelManager;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class StatisticsManagerServerVoiceChannelMemberJoinListener implements ServerVoiceChannelMemberJoinListener {
    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent serverVoiceChannelMemberJoinEvent) {

        if(serverVoiceChannelMemberJoinEvent.getUser().isBot())
            return;

        if(serverVoiceChannelMemberJoinEvent.getChannel().asChannelCategory().isPresent() &&
                serverVoiceChannelMemberJoinEvent.getChannel().asChannelCategory().get().getIdAsString().equals(TemporaryChannelManager.TEMP_CATEGORY_ID)){
            return;
        }

        if(CommonUtils.statisticsManager.getStatistics().getMemberJoinVoiceTime().containsKey(serverVoiceChannelMemberJoinEvent.getUser().getId())){
            return;
        }

        CommonUtils.statisticsManager.getStatistics().getMemberJoinVoiceTime().put(
                serverVoiceChannelMemberJoinEvent.getUser().getId(),
                System.currentTimeMillis()
        );
        LogUtils.info("Member join voice statistics updated");

    }
}
