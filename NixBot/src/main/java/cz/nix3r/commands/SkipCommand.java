package cz.nix3r.commands;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SkipCommand {

    public static void run(SlashCommandInteraction interaction) {

        CommonUtils.musicManager.playNext();
        interaction.createImmediateResponder().setContent("Skipped song successfully").respond();
        LogSystem.log(LogType.INFO, "End of command skip by '" + interaction.getUser().getName() + "'");

    }
}
