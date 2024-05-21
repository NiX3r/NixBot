package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.SongInstance;
import org.apache.commons.logging.Log;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static EmbedBuilder createAnnouncementEmbed(String topic, String message, String author){
        return new EmbedBuilder()
                .setTitle(topic)
                .setDescription(message.replace("\\n", "\n"))
                .setColor(Color.decode("#2100FF"))
                .setThumbnail(new File(".\\announcement-thumbnail.png"))
                .setFooter("Author: " + author + " | Version: " + CommonUtils.version);
    }

    public static EmbedBuilder createVerificationEmbed(){
        return new EmbedBuilder()
                .setTitle("Welcome to NiXCrew")
                .setDescription("To continue please verificate using our system.\nType in code below")
                .setColor(Color.decode("#2100FF"))
                .setFooter("Version: " + CommonUtils.version);
    }

    public static EmbedBuilder createJoinEmbed(String nick, String inviterMentionTag, Icon userAvatar, Server server){
        Server nixCrew = (Server)CommonUtils.bot.getServers().toArray()[0];
        Random r = new Random();
        return new EmbedBuilder()
                .setTitle(nick)
                .setDescription(CommonUtils.messages.getWelcomeMessages().get(r.nextInt(CommonUtils.messages.getWelcomeMessages().size())))
                .setColor(Color.decode("#00d60e"))
                .addField("Thanks for invite", inviterMentionTag, true)
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
                .setDescription(CommonUtils.messages.getLeaveMessages().get(CommonUtils.messages.getLeaveMessages().size()))
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

    public static EmbedBuilder createRoleEmbed(){
        return new EmbedBuilder()
                .setTitle("Přidej si nebo odeber roli")
                .setColor(Color.decode("#7900FF"))
                .setFooter("Version: "  + CommonUtils.version)
                .addField("Dostupné role", CommonUtils.roleSetter.size() + "")
                .setDescription("Pomocí kliknutí na tlačítka si můžeš přidávat nebo odebírat role");
    }

    public static EmbedBuilder createTopStatisticsEmbed(String name, String description, ArrayList<String[]> data){
        EmbedBuilder output = new EmbedBuilder()
                .setTitle(name)
                .setDescription(description)
                .setColor(Color.decode("#2100FF"));
        for(int i = 0; i < 5; i++){
            if(i < data.size())
                output.addField("#" + (i+1) + " " + data.get(i)[0], data.get(i)[1]);
        }
        return output;
    }

    public static List<EmbedBuilder> createStatisticEmbed(ArrayList<String[]> data){
        List<EmbedBuilder> output = new ArrayList<EmbedBuilder>();
        EmbedBuilder temp = new EmbedBuilder();
        for(int i = 0; i < data.size(); i++){
            if(i == 0){
                temp.setTitle("Ostatní statistiky")
                        .setColor(Color.decode("#2100FF"))
                        .addField(data.get(i)[0], data.get(i)[1]);
                output.add(temp);
            }
            else if(i % 6 == 0){
                temp = new EmbedBuilder()
                    .setColor(Color.decode("#2100FF"))
                    .addField(data.get(i)[0], data.get(i)[1]);
                output.add(temp);
            }
            else {
                temp.addField(data.get(i)[0], data.get(i)[1]);
            }
        }
        return output;
    }

    public static void throwError(Exception ex){
        throwError(false, ex);
    }

    public static void throwError(boolean fatal, Exception exception){

        if(fatal)
            LogSystem.fatalError(exception.getMessage());
        else
            LogSystem.error(exception.getMessage());
        String stackTrace = "";
        for(var item : exception.getStackTrace()){
            stackTrace += "\n" + item.toString();
        }
        stackTrace = "```" + stackTrace + "\n```";
        System.out.println(stackTrace);
        ((Server)CommonUtils.bot.getServers().toArray()[0]).getTextChannelById(CommonUtils.NIXBOT_CHANNEL_ID).get().sendMessage(
                new EmbedBuilder().setTitle(fatal ? "Fatal Error" : "Error")
                        .setColor(fatal ? Color.decode("#0f0f0f") : Color.decode("#c90006"))
                        .addField("Message", exception.getMessage() == null ? "none" : exception.getMessage())
                        .setDescription(stackTrace)
                        .setFooter("Version: " + CommonUtils.version)
        ).join();

    }

    public static String formatTimeToMinutes(long time){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
