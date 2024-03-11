package cz.nix3r.listeners;

import cz.nix3r.utils.CommonUtils;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class nServerVoiceChannelMemberJoinListener implements ServerVoiceChannelMemberJoinListener {
    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent serverVoiceChannelMemberJoinEvent) {

        if(serverVoiceChannelMemberJoinEvent.getChannel().getIdAsString().equals(CommonUtils.CREATE_CHANNEL_CHANNEL_ID)){
            createTempVoiceChannel(serverVoiceChannelMemberJoinEvent.getUser(), serverVoiceChannelMemberJoinEvent.getServer());
        }

    }

    private void createTempVoiceChannel(User creator, Server server){

        if(CommonUtils.tempChannelManager.alreadyHasTempVoiceChannel(creator.getId())){
            server.getVoiceChannelById(CommonUtils.tempChannelManager.getTempVoiceChannelMap().get(creator.getId())).ifPresent(tempChannel -> {
                creator.move(tempChannel).join();
            });
            return;
        }

        ServerVoiceChannel voiceChannel = server.createVoiceChannelBuilder()
                .setCategory(server.getChannelCategoryById(CommonUtils.CREATE_CHANNEL_CATEGORY_ID).get())
                .setName(creator.getName() + "'s temp")
                .addPermissionOverwrite(creator, Permissions.fromBitmask(268435472))
                .create().join();

        CommonUtils.tempChannelManager.addTempChannel(creator.getId(), voiceChannel.getId());



        creator.move(voiceChannel).join();

    }

}