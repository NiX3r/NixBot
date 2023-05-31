package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

public class nServerMemberLeaveListener implements ServerMemberLeaveListener {
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent serverMemberLeaveEvent) {
        serverMemberLeaveEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createLeaveEmbed(serverMemberLeaveEvent.getUser().getName(), serverMemberLeaveEvent.getUser().getAvatar(), serverMemberLeaveEvent.getServer())).join();
        });
        CommonUtils.bot.updateActivity(ActivityType.PLAYING, "with " + serverMemberLeaveEvent.getServer().getMembers().size() + " users");
        LogSystem.log(LogType.INFO, "Member " + serverMemberLeaveEvent.getUser().getName() + " left from the server. Bot activity updated");
    }
}
