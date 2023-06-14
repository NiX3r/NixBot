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
