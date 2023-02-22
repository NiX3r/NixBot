package cz.nix3r.utils;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class DiscordUtils {

    public static EmbedBuilder createJoinEmbed(String nick, Icon userAvatar){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("has just landed here! Please warm welcome for him/her!")
                .setColor(Color.decode("#00d60e"))
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createBanEmbed(String nick, Icon userAvatar){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("was banned! What a shame!")
                .setColor(Color.decode("#d60000"))
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createLeaveEmbed(String nick, Icon userAvatar){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("left our server! See ya again .. hopefully ..")
                .setColor(Color.decode("#d66b00"))
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createUnbanEmbed(String nick, Icon userAvatar){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("was unbanned! Hell ya. Let's roll it again!")
                .setColor(Color.decode("#004ad6"))
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

}
