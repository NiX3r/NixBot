package cz.iliev.managers.command_manager.utils;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class CommandManagerUtils {

    public static void createCommands(){

        SlashCommand.with("status", "command to show Discord bot status").setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created status command");

        SlashCommand.with("play", "command to play Youtube song", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "url", "URL of YouTube video", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created play command");

        SlashCommand.with("skip", "command to skip current playing song").createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created skip command");

        SlashCommand.with("queue", "command to view current music queue", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "clear", "clear current queue"),
                SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "view", "view current queue")
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created queue commands");

        SlashCommand.with("pause", "command to pause current song").createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created pause command");

        SlashCommand.with("unpause", "command to unpause current song").createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created unpause command");

        SlashCommand.with("volume", "command to set bot volume", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "volume", "volume percentage", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created volume command");

        SlashCommand.with("dice", "command to get random number thrown from dice", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "size", "size of dice", false)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created dice command");

        SlashCommand.with("anonymous", "command to send anonymous message to anonymous channel", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message to be sent", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created anonymous command");

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
        LogUtils.info("Created ticket commands");

        SlashCommand.with("phonetic", "command to encode text to NATO phonetic", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message to be encode", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created phonetic command");

        SlashCommand.with("role", "command to role adding/removing by components click", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "message", "message for roles adding/removing"),
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "set", "set up add/remove role by click component", Arrays.asList(
                        SlashCommandOption.create(SlashCommandOptionType.ROLE, "role", "role to be add", true),
                        SlashCommandOption.create(SlashCommandOptionType.BOOLEAN, "add", "is role adding or removing", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "emoji", "emoji for component", true),
                        SlashCommandOption.create(SlashCommandOptionType.STRING, "text", "component label", true)
                ))
        )).setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created role command");

        SlashCommand.with("announcement", "command to send announcement", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "topic", "topic of announcement", true),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "message", "message of announcement", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created phonetic command");

        SlashCommand.with("manager", "command to view and administrate managers", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "manager name", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created manager command");

        SlashCommand.with("ban", "command to ban member", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "member to be ban", true),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "reason of ban", true),
                SlashCommandOption.create(SlashCommandOptionType.LONG, "duration", "ban duration", false)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created ban command");

        SlashCommand.with("unban", "command to unban member", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "member to be unban", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created unban command");

        SlashCommand.with("kick", "command to kick member", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "member to be kick", true),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "reason of kick", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created kick command");

        SlashCommand.with("mute", "command to mute member", Arrays.asList(
                SlashCommandOption.create(SlashCommandOptionType.USER, "member", "member to be mute", true),
                SlashCommandOption.create(SlashCommandOptionType.STRING, "reason", "reason of mute", true)
        )).createGlobal(CommonUtils.bot).join();
        LogUtils.info("Created mute command");

    }

    public static void deleteCommands(){

        try {
            LogUtils.info("Deleting commands ...");
            for(SlashCommand cmd : CommonUtils.bot.getGlobalSlashCommands().get()){
                cmd.delete().join();
                LogUtils.info("Deleted " + cmd.getName() + " command");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
    
}
