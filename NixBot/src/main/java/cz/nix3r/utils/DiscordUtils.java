package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;

public class DiscordUtils {

    public static EmbedBuilder createJoinEmbed(String nick, Icon userAvatar, Server server){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("has just landed here! Please warm welcome for him/her!")
                .setColor(Color.decode("#00d60e"))
                .addField("Member count", server.getMembers().size() + " total")
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createBanEmbed(String nick, Icon userAvatar, Server server){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("was banned! What a shame!")
                .setColor(Color.decode("#d60000"))
                .addField("Member count", server.getMembers().size() + " total")
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createLeaveEmbed(String nick, Icon userAvatar, Server server){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("left our server! See ya again .. hopefully ..")
                .setColor(Color.decode("#d66b00"))
                .addField("Member count", server.getMembers().size() + " total")
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createUnbanEmbed(String nick, Icon userAvatar, Server server){
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription("was unbanned! Hell ya. Let's roll it again!")
                .setColor(Color.decode("#004ad6"))
                .addField("Member count", server.getMembers().size() + " total")
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

    public static void throwError(String message, String owner){
        throwError(false, message, owner);
    }

    public static void throwError(boolean fatal, String message, String owner){

        LogSystem.log(fatal ? LogType.FATAL_ERROR : LogType.ERROR, message);
        ((Server)CommonUtils.bot.getServers().toArray()[0]).getTextChannelById(CommonUtils.NIXBOT_CHANNEL_ID).get().sendMessage(
                new EmbedBuilder().setTitle(fatal ? "Fatal Error" : "Error")
                        .setColor(fatal ? Color.decode("#fc0202") : Color.decode("#fc7702"))
                        .setDescription(message)
                        .setFooter(owner)
        ).join();

    }

}
