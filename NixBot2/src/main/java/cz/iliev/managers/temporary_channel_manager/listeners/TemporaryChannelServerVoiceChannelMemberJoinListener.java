package cz.iliev.managers.temporary_channel_manager.listeners;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class TemporaryChannelServerVoiceChannelMemberJoinListener implements ServerVoiceChannelMemberJoinListener {
    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent serverVoiceChannelMemberJoinEvent) {
        if(serverVoiceChannelMemberJoinEvent.getChannel().getIdAsString().equals(CommonUtils.temporaryChannelManager.TEMP_CHANNEL_ID)){
            createTempVoiceChannel(serverVoiceChannelMemberJoinEvent.getUser(), serverVoiceChannelMemberJoinEvent.getServer());
        }
    }

    private void createTempVoiceChannel(User creator, Server server){

        LogUtils.info("User '" + creator.getName() + "' tries to create temp channel");

        if(CommonUtils.temporaryChannelManager.getTempVoiceChannelMap().containsKey(creator.getId())){
            server.getVoiceChannelById(CommonUtils.temporaryChannelManager.getTempVoiceChannelMap().get(creator.getId())).ifPresent(tempChannel -> {
                creator.move(tempChannel).join();
                LogUtils.info("User " + creator.getName() + "' already have temp channel. User moved");
            });
            return;
        }

        server.getChannelCategoryById(CommonUtils.temporaryChannelManager.TEMP_CATEGORY_ID).ifPresentOrElse(category -> {
            ServerVoiceChannel voiceChannel = server.createVoiceChannelBuilder()
                    .setCategory(server.getChannelCategoryById(CommonUtils.temporaryChannelManager.TEMP_CATEGORY_ID).get())
                    .setName(creator.getName() + "'s temp")
                    .addPermissionOverwrite(creator, Permissions.fromBitmask(268435472))
                    .create().join();

            CommonUtils.temporaryChannelManager.getTempVoiceChannelMap().put(creator.getId(), voiceChannel.getId());
            creator.move(voiceChannel).join();
            // TODO - add to statistics
            LogUtils.info("Temp channel for user '" + creator.getName() + "' created. User moved");
        }, new Runnable() {
            @Override
            public void run() {
                LogUtils.info("Temp channel for user '" + creator.getName() + "' can't created. Temporary channel category not found");
            }
        });

    }

}
