package cz.iliev.managers.music_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PlayCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        interaction.getArguments().get(0).getStringValue().ifPresent(url -> {
            interaction.getServer().ifPresent(server -> {

                if(!interaction.getUser().getConnectedVoiceChannel(server).isPresent())
                    interaction.createImmediateResponder().setContent("To execute this command you have to be in voice channel in which bot could join.").setFlags(MessageFlag.EPHEMERAL).respond();
                else
                    CommonUtils.musicManager.playMusic(interaction, url);

                LogUtils.info("End of command play by '" + interaction.getUser().getName() + "'. URL: '" + url + "'");

            });
        });
    }
}
