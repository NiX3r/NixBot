package cz.nix3r.commands;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PlayCommand {
    public static void run(SlashCommandInteraction interaction) {

        interaction.getArguments().get(0).getStringValue().ifPresent(url -> {

            interaction.getServer().ifPresent(server -> {

                if(!interaction.getUser().getConnectedVoiceChannel(server).isPresent())
                    interaction.createImmediateResponder().setContent("To execute this command you have to be in voice channel in which bot could join.").setFlags(MessageFlag.EPHEMERAL).respond();
                else
                    CommonUtils.musicManager.playMusic(interaction, url);

                LogSystem.log(LogType.INFO, "End of command play by '" + interaction.getUser().getName() + "'. URL: '" + url + "'");

            });

        });

    }
}
