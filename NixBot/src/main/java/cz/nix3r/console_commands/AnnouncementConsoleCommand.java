package cz.nix3r.console_commands;

import cz.nix3r.commands.StatusCommand;
import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.apache.commons.logging.Log;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.EmptyStackException;

public class AnnouncementConsoleCommand {

    public static void run(String[] command){

        LogSystem.info("Console command '" + String.join(" ", command) + "' caught");

        if(command.length == 1){
            LogSystem.info("Announcement types: restart");
        }
        if (command.length < 2){
            LogSystem.warning("Wrong command usage. Please type: 'ann' or 'announcement'");
            return;
        }

        switch (command[1]){
            case "r": case "restart":
                restart(command);
                break;
            case "s": case "status":
                status(command);
                break;
        }

        LogSystem.info("End of the command '" + String.join(" ", command) + "'");

    }

    private static void status(String[] command){

        if(command.length != 2){
            LogSystem.warning("Bad command usage. Usage: 'announcements status'");
            return;
        }

        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommonUtils.NIXBOT_CHANNEL_ID).ifPresent(textChannel -> {
                StatusCommand.getStatusMessageBuilder().send(textChannel).join();
                LogSystem.info("Status sent");
            });
        });

    }

    private static void restart(String[] command){

        if(command.length != 3){
            LogSystem.warning("Bad command usage. Usage: 'announcements restart <time to restart>'");
            return;
        }

        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommonUtils.NEWS_CHANNEL_ID).ifPresent(textChannel -> {

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("Restart bota")
                        .setDescription("Dojde k automatickému restartu bota.")
                        .addField("Restart za", command[2] + " minut")
                        .setColor(Color.GREEN)
                        .setThumbnail("https://pluspng.com/img-png/restart-png-restart-icon-1600.png")
                        .setFooter("Console | Version: " + CommonUtils.version);

                textChannel.sendMessage(builder);

            });
        });
    }

}
