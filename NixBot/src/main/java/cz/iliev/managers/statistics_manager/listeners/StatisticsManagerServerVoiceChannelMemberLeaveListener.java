package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class StatisticsManagerServerVoiceChannelMemberLeaveListener implements ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent serverVoiceChannelMemberLeaveEvent) {

        if(serverVoiceChannelMemberLeaveEvent.getUser().isBot())
            return;

        long userId = serverVoiceChannelMemberLeaveEvent.getUser().getId();

        if(CommonUtils.statisticsManager.getStatistics().getMemberJoinVoiceTime().containsKey(userId))
            return;

        long timestamp = CommonUtils.statisticsManager.getStatistics().getMemberJoinVoiceTime().get(userId);
        long channelId = serverVoiceChannelMemberLeaveEvent.getChannel().getId();

        CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().incrementCallTime(userId, timestamp);
        CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance().incrementVoiceChannel(channelId, timestamp);
        CommonUtils.statisticsManager.getStatistics().getVoiceChannelStatsInstance().incrementCallTime(timestamp);

        CommonUtils.statisticsManager.getStatistics().getMemberJoinVoiceTime().remove(userId);

        LogUtils.info("Member leave voice statistics updated");

    }
}
