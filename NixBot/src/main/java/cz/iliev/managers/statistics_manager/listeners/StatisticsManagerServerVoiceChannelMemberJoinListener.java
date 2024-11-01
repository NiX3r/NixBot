package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.CallTimeBehavior;
import cz.iliev.managers.statistics_manager.behaviors.UserCallTimeBehavior;
import cz.iliev.managers.statistics_manager.behaviors.VoiceChannelBehavior;
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

        long userId = serverVoiceChannelMemberJoinEvent.getUser().getId();
        if(CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().containsKey(userId)){
            long increment = System.currentTimeMillis() - CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().get(userId);
            UserCallTimeBehavior.behave(userId, increment);
            CallTimeBehavior.behave(increment);
            CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().put(userId, System.currentTimeMillis());
        }
        else{
            CommonUtils.statisticsManager.getUserVoiceChannelJoinTime().put(userId, System.currentTimeMillis());
        }

        VoiceChannelBehavior.behave(serverVoiceChannelMemberJoinEvent.getChannel().getId());
        LogUtils.info("Member join voice statistics updated");

    }
}
