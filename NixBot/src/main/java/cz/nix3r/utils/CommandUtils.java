package cz.nix3r.utils;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.SlashCommand;

import java.util.concurrent.ExecutionException;

public class CommandUtils {

    public static void createCommands(){

        SlashCommand.with("status", "command for show Discord bot status").setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR).createGlobal(CommonUtils.bot).join();

    }

    public static void deleteCommands(){

        try {
            for(SlashCommand cmd : CommonUtils.bot.getGlobalSlashCommands().get()){

                cmd.delete().join();

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
