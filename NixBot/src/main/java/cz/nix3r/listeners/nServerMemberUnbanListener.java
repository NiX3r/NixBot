package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.event.server.member.ServerMemberUnbanEvent;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;

public class nServerMemberUnbanListener implements ServerMemberUnbanListener {
    @Override
    public void onServerMemberUnban(ServerMemberUnbanEvent serverMemberUnbanEvent) {
        serverMemberUnbanEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createUnbanEmbed(serverMemberUnbanEvent.getUser().getName(), serverMemberUnbanEvent.getUser().getAvatar(), serverMemberUnbanEvent.getServer())).join();
        });
        DiscordUtils.updateBotActivity("with " + serverMemberUnbanEvent.getServer().getMembers().size() + " users");
        LogSystem.log(LogType.INFO, "Member " + serverMemberUnbanEvent.getUser().getName() + " unbanned from the server. Bot activity updated");
    }
}
