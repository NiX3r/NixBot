package cz.iliev.managers.temporary_channel_manager.listeners;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class TemporaryChannelServerVoiceChannelMemberLeaveListener implements ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent serverVoiceChannelMemberLeaveEvent) {
        serverVoiceChannelMemberLeaveEvent.getChannel().getCategory().ifPresent(category -> {
            if(category.getIdAsString().equals(CommonUtils.temporaryChannelManager.TEMP_CATEGORY_ID) && !serverVoiceChannelMemberLeaveEvent.getChannel().getIdAsString().equals(CommonUtils.temporaryChannelManager.TEMP_CHANNEL_ID)){
                closeTempChannel(serverVoiceChannelMemberLeaveEvent.getUser(), serverVoiceChannelMemberLeaveEvent.getServer(), serverVoiceChannelMemberLeaveEvent.getChannel());
            }
        });
    }

    private void closeTempChannel(User creator, Server server, ServerVoiceChannel channel){

        LogUtils.info("Attempting to delete temp channel '" + channel.getName() + "'");

        if(channel.getConnectedUserIds().isEmpty()){

            CommonUtils.temporaryChannelManager.getTempVoiceChannelMap().remove(creator.getId());
            channel.delete().join();
            LogUtils.info("Channel '" + channel.getName() + "' deleted");

        }

    }
}
