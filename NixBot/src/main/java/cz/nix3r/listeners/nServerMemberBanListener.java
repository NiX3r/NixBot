package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

import java.awt.*;

public class nServerMemberBanListener implements ServerMemberBanListener {
    @Override
    public void onServerMemberBan(ServerMemberBanEvent serverMemberBanEvent) {

        serverMemberBanEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createBanEmbed(serverMemberBanEvent.getUser().getName(), serverMemberBanEvent.getUser().getAvatar(), serverMemberBanEvent.getServer())).join();
        });
        DiscordUtils.updateBotActivity("with " + serverMemberBanEvent.getServer().getMembers().size() + " users");
        LogSystem.log(LogType.INFO, "Member " + serverMemberBanEvent.getUser().getName() + " banned from the server. Bot activity updated");

    }
}
