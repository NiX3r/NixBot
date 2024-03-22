package cz.nix3r.managers;

import cz.nix3r.console_commands.AnnouncementConsoleCommand;
import cz.nix3r.enums.LogType;
import cz.nix3r.utils.LogSystem;

public class ConsoleCommandManager {

    public static void run(String command){

        String[] commandSplitter = command.split(" ");

        if(commandSplitter.length < 2)
            return;

        LogSystem.log(LogType.INFO, "Console command caught. Command: '" + command + "'");

        switch (commandSplitter[0]){

            case "ann": case "announcements":
                AnnouncementConsoleCommand.run(commandSplitter);
                break;

        }

    }

}
