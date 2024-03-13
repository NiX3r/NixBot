package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.UserChannelActivityInstance;
import cz.nix3r.managers.StatisticsManager;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class nServerVoiceChannelMemberLeaveListener implements ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent serverVoiceChannelMemberLeaveEvent) {

        serverVoiceChannelMemberLeaveEvent.getChannel().getCategory().ifPresent(category -> {
            if(category.getIdAsString().equals(CommonUtils.CREATE_CHANNEL_CATEGORY_ID) && !serverVoiceChannelMemberLeaveEvent.getChannel().getIdAsString().equals(CommonUtils.CREATE_CHANNEL_CHANNEL_ID)){
                closeTempChannel(serverVoiceChannelMemberLeaveEvent.getUser(), serverVoiceChannelMemberLeaveEvent.getServer(), serverVoiceChannelMemberLeaveEvent.getChannel());
            }
        });

        if(serverVoiceChannelMemberLeaveEvent.getUser().isYourself()){
            if(CommonUtils.musicManager.isPlaying()){
                serverVoiceChannelMemberLeaveEvent.getChannel().connect().join();
                CommonUtils.musicManager.playNext();
                LogSystem.log(LogType.INFO, "Bot tried to leave channel. Bot reconnect and start playing next song");
            }
        }

        if(serverVoiceChannelMemberLeaveEvent.getChannel().asChannelCategory().isPresent() &&
                serverVoiceChannelMemberLeaveEvent.getChannel().asChannelCategory().get().getIdAsString().equals(CommonUtils.CREATE_CHANNEL_CATEGORY_ID)){
            return;
        }
        if(serverVoiceChannelMemberLeaveEvent.getUser().isBot())
            return;

        if(!CommonUtils.statisticsManager.getChannelActivities().containsKey(serverVoiceChannelMemberLeaveEvent.getUser().getId()))
            return;

        UserChannelActivityInstance activityInstance = CommonUtils.statisticsManager.getChannelActivities().get(serverVoiceChannelMemberLeaveEvent.getUser().getId());
        long timestamp = System.currentTimeMillis() - activityInstance.getTimestamp();

        CommonUtils.statisticsManager.incrementUsedVoiceChannelId(activityInstance.getChannelId(), timestamp);
        CommonUtils.statisticsManager.incrementBestUserCallTime(activityInstance.getUserId(), timestamp);
        CommonUtils.statisticsManager.incrementCallTime(timestamp);

    }

    private void closeTempChannel(User creator, Server server, ServerVoiceChannel channel){

        LogSystem.log(LogType.INFO, "Attempting to delete temp channel '" + channel.getName() + "'");

        if(channel.getConnectedUserIds().isEmpty()){

            CommonUtils.tempChannelManager.getTempVoiceChannelMap().remove(creator.getId());
            channel.delete().join();
            LogSystem.log(LogType.INFO, "Channel '" + channel.getName() + "' deleted");

        }

    }

}
