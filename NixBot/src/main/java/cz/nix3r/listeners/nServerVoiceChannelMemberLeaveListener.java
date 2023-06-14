package cz.nix3r.listeners;

import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
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

        if(serverVoiceChannelMemberLeaveEvent.getUser().getIdAsString().equals(CommonUtils.bot.getYourself().getIdAsString())){
            if(CommonUtils.musicManager.isPlaying()){
                serverVoiceChannelMemberLeaveEvent.getChannel().connect().join();
                CommonUtils.musicManager.playNext();
            }
        }

    }

    private void closeTempChannel(User creator, Server server, ServerVoiceChannel channel){

        if(channel.getConnectedUserIds().size() == 0){

            CommonUtils.tempChannelManager.getTempVoiceChannelMap().remove(creator.getId());
            channel.delete().join();

        }

    }

}
