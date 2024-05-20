package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CommandUtils {

    public static void createCommands(){

        SlashCommand.with("status", "command to show Discord bot status").setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created status command");

        SlashCommand.with("play", "command to play Youtube song", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "url", "URL of YouTube video", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created play command");

        SlashCommand.with("skip", "command to skip current playing song").createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created skip command");

        SlashCommand.with("queue", "command to view current music queue", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "clear", "clear current queue"),
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "view", "view current queue")
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created queue commands");

        SlashCommand.with("pause", "command to pause current song").createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created pause command");

        SlashCommand.with("unpause", "command to unpause current song").createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created unpause command");

        SlashCommand.with("volume", "command to set bot volume", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "volume", "volume percentage", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created volume command");

        SlashCommand.with("dice", "command to get random number thrown from dice", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "size", "size of dice", false)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created dice command");

        SlashCommand.with("anonymous", "command to send anonymous message to anonymous channel", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message to be sent", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created anonymous command");

        SlashCommand.with("ticket", "command for ticket system", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "message", "sub-command for send default message of ticket system"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "close", "sub-command for close the current ticket"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "resolve", "sub-command for close and mark as resolved the current ticket"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "add", "sub-command for add member to ticket", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "user-nick", "user nick who has to been add")
                )),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "remove", "sub-command for remove member from ticket", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "user-nick", "user nick who has to been remove")
                ))
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created ticket commands");

        SlashCommand.with("phonetic", "command to encode text to NATO phonetic", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message to be encode", true)
        )).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created phonetic command");

        SlashCommand.with("role", "command to role adding/removing by components click", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "message", "message for roles adding/removing"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "set", "set up add/remove role by click component", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.ROLE, "role", "role to be add", true),
                        SlashCommandOption.create(SlashCommandOptionType.BOOLEAN, "add", "is role adding or removing", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "emoji", "emoji for component", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "text", "component label", true)
                ))
        )).setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogSystem.info("Created role command");

    }

    public static boolean hasSenderAdminPermission(SlashCommandInteraction interaction){
        if(interaction.getServer().isPresent()){
            return interaction.getServer().get().hasPermission(interaction.getUser(), PermissionType.ADMINISTRATOR);
        }
        else
            return true;
    }

    public static void deleteCommands(){

        try {
            LogSystem.info("Deleting commands ...");
            for(SlashCommand cmd : CommonUtils.bot.getGlobalSlashCommands().get()){
                cmd.delete().join();
                LogSystem.info("Deleted " + cmd.getName() + " command");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
