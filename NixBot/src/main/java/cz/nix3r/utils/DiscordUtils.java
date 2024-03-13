package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.SongInstance;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DiscordUtils {

    private static Random random = new Random();

    public static void updateBotActivity(String activity){
        CommonUtils.bot.updateActivity(ActivityType.PLAYING, activity);
    }

    public static EmbedBuilder createNextSongEmbed(SongInstance song){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Date date = new Date(song.getAddedTime());
        String url = song.getTrack().getInfo().uri;
        String temp = url.substring(url.indexOf("watch?v=") + 8);
        if(temp.contains("&list"))
            temp = temp.substring(0, temp.indexOf("&list"));
        String picture = ("https://img.youtube.com/vi/%ID%/maxresdefault.jpg").replace("%ID%", temp);
        return new EmbedBuilder()
                .setTitle("Start playing new song")
                .setUrl(url)
                .setImage(picture)
                .addField("Name", song.getTrack().getInfo().title, true)
                .addField("Length", formatTimeToMinutes(song.getTrack().getDuration()))
                .addField("Added by", song.getPlayerName())
                .addField("Time added", simpleDateFormat.format(date))
                .setColor(Color.decode("#2100FF"));
    }

    public static EmbedBuilder createJoinEmbed(String nick, long inviterId, Icon userAvatar, Server server){
        Server nixCrew = (Server)CommonUtils.bot.getServers().toArray()[0];
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription(CommonUtils.WELCOME_MESSAGES[random.nextInt(10)])
                .setColor(Color.decode("#00d60e"))
                .addField("Thanks for invite", inviterId == 0 ? "unknown" : nixCrew.getMemberById(inviterId).get().getMentionTag(), true)
                .addField("Member count", server.getMembers().size() + " total", true)
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
                .setDescription(CommonUtils.LEAVE_MESSAGES[random.nextInt(10)])
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

    public static EmbedBuilder createTopStatisticsEmbed(String name, String description, ArrayList<String[]> data){
        return new EmbedBuilder()
                .setTitle(name)
                .addField("#1 " + data.get(0)[0], data.get(0)[1])
                .addField("#2 " + data.get(1)[0], data.get(1)[1])
                .addField("#3 " + data.get(2)[0], data.get(2)[1])
                .addField("#4 " + data.get(3)[0], data.get(3)[1])
                .addField("#5 " + data.get(4)[0], data.get(4)[1])
                .setDescription(description)
                .setColor(Color.decode("#2100FF"));
    }

    public static EmbedBuilder createStatisticEmbed(ArrayList<String[]> data){
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Ostatní statistiky")
                .setColor(Color.decode("#2100FF"));
        for(String[] item : data){
            builder.addField(item[0], item[1]);
        }
        return builder;
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

    public static String formatTimeToMinutes(long time){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
