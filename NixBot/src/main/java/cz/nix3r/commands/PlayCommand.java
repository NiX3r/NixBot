package cz.nix3r.commands;

import cz.nix3r.utils.CommonUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PlayCommand {
    public static void run(SlashCommandInteraction interaction) {

        interaction.getArguments().get(0).getStringValue().ifPresent(url -> {

            interaction.getServer().ifPresent(server -> {
                CommonUtils.musicManager.playMusic(interaction, url);
            });

        });

    }
}
