package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.CallTimeBehavior;
import cz.iliev.managers.statistics_manager.behaviors.UserCallTimeBehavior;
import cz.iliev.managers.statistics_manager.behaviors.VoiceChannelBehavior;
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

        if(!CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().containsKey(userId))
            return;

        long increment = System.currentTimeMillis() - CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().get(userId);
        CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().remove(userId);
        long channelId = serverVoiceChannelMemberLeaveEvent.getChannel().getId();

        UserCallTimeBehavior.behave(userId, increment);
        VoiceChannelBehavior.behave(channelId);
        CallTimeBehavior.behave(increment);

        LogUtils.info("Member leave voice statistics updated");

    }
}
