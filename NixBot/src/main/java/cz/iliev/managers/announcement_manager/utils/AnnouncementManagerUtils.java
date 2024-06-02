package cz.iliev.managers.announcement_manager.utils;

import cz.iliev.managers.announcement_manager.instances.MessagesInstance;
import cz.iliev.managers.music_manager.instances.SongInstance;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AnnouncementManagerUtils {

    public static EmbedBuilder createExceptionEmbed(Exception exception, boolean isFatal){
        String stackTrace = "";
        for(var item : exception.getStackTrace()){
            stackTrace += "\n" + item.toString();
        }
        stackTrace = "```" + stackTrace + "\n```";
        return new EmbedBuilder().setTitle(isFatal ? "Fatal Error" : "Error")
                .setColor(isFatal ? Color.decode("#0f0f0f") : Color.decode("#c90006"))
                .addField("Message", exception.toString())
                .setDescription(stackTrace)
                .setFooter("Version: " + CommonUtils.VERSION);
    }

    public static EmbedBuilder createRestartAnnouncementEmbed(String minutes){
        return new EmbedBuilder()
                .setTitle("Bot restart!")
                .setDescription("This bot will be restarted.")
                .addField("Restart in ", minutes + " minutes")
                .setColor(Color.GREEN)
                .setThumbnail("https://pluspng.com/img-png/restart-png-restart-icon-1600.png")
                .setFooter("Console | Version: " + CommonUtils.VERSION);
    }

    public static EmbedBuilder createCurrentSongEmbed(SongInstance song){
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
                .addField("Length", CommonUtils.formatTimeToMinutes(song.getTrack().getDuration()))
                .addField("Added by", song.getPlayerName())
                .addField("Time added", simpleDateFormat.format(date))
                .setColor(Color.decode("#2100FF"));
    }

    public static EmbedBuilder createRoleSetterEmbed(int roleSetterCount){
        return new EmbedBuilder()
                .setTitle("Add or remove roles")
                .setColor(Color.decode("#7900FF"))
                .setFooter("Version: "  + CommonUtils.VERSION)
                .addField("Available roles", roleSetterCount + "")
                .setDescription("By clicking buttons below you can add or remove roles");
    }

    public static EmbedBuilder createJoinEmbed(String nick, String inviterMentionTag, Icon userAvatar, Server server){
        Server nixCrew = (Server)CommonUtils.bot.getServers().toArray()[0];
        Random r = new Random();
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription(CommonUtils.announcementManager.getMessages().getWelcomeMessages().get(r.nextInt(CommonUtils.announcementManager.getMessages().getWelcomeMessages().size())))
                .setColor(Color.decode("#00d60e"))
                .addField("Thanks for invite", inviterMentionTag, true)
                .addField("Member count", server.getMembers().size() + " total", true)
                .setFooter("Version: " + CommonUtils.VERSION)
                .setThumbnail(userAvatar);
    }

    public static EmbedBuilder createAnnouncementEmbed(String topic, String message, String author){
        return new EmbedBuilder()
                .setTitle(topic)
                .setDescription(message.replace("\\n", "\n"))
                .setColor(Color.decode("#2100FF"))
                .setThumbnail(new File(".\\announcement-thumbnail.png"))
                .setFooter("Author: " + author + " | Version: " + CommonUtils.VERSION);
    }

}
