package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;

import java.util.concurrent.ExecutionException;

public class CommandUtils {

    public static void createCommands(){

        SlashCommand.with("status", "command for show Discord bot status").setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();
        LogSystem.log(LogType.INFO, "Created status command");

    }

    public static void deleteCommands(){

        try {
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
