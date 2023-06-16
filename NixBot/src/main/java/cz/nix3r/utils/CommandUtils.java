package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CommandUtils {

    public static void createCommands(){

        SlashCommand.with("status", "command to show Discord bot status").setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created status command");

        SlashCommand.with("play", "command to play Youtube song", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "url", "URL of YouTube video", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created play command");

        SlashCommand.with("skip", "command to skip current playing song").createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created skip command");

        SlashCommand.with("queue", "command to view current music queue", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "clear", "clear current queue"),
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "view", "view current queue")
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created queue commands");

        SlashCommand.with("pause", "command to pause current song").createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created pause command");

        SlashCommand.with("unpause", "command to unpause current song").createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created unpause command");

        SlashCommand.with("volume", "command to set bot volume", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "volume", "volume percentage", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created volume command");

        SlashCommand.with("dice", "command to get random number thrown from dice", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "size", "size of dice", false)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created dice command");

        SlashCommand.with("anonymous", "command to send anonymous message to anonymous channel", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message to be sent", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created anonymous command");

    }

    public static void deleteCommands(){

        try {
            LogSystem.log(LogType.INFO, "Deleting commands ...");
            for(SlashCommand cmd : CommonUtils.bot.getGlobalSlashCommands().get()){
                cmd.delete().join();
                LogSystem.log(LogType.INFO, "Deleted " + cmd.getName() + " command");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
