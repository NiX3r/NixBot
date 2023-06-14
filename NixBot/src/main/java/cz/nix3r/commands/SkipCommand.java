package cz.nix3r.commands;

import cz.nix3r.utils.CommonUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SkipCommand {

    public static void run(SlashCommandInteraction interaction) {

        CommonUtils.musicManager.playNext();
        interaction.createImmediateResponder().setContent("Skipped song successfully").respond();

    }
}
