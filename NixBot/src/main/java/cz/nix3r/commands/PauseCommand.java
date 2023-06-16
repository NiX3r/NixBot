package cz.nix3r.commands;

import cz.nix3r.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PauseCommand {
    public static void run(SlashCommandInteraction interaction) {

        if(CommonUtils.musicManager.isPaused()){
            interaction.createImmediateResponder().setContent("NixBot is already paused").setFlags(MessageFlag.EPHEMERAL).respond();
        }
        else {
            CommonUtils.musicManager.setPause(true);
            interaction.createImmediateResponder().setContent("NixBot song is paused").respond();
        }

    }
}
