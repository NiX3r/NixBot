package cz.iliev.managers.music_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class VolumeCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        if(!CommonUtils.musicManager.isPlaying()){
            interaction.createImmediateResponder().setContent("For execute this command bot have to play a song").setFlags(MessageFlag.EPHEMERAL).respond();
        }
        else {
            long vol = interaction.getArgumentByIndex(0).get().getLongValue().get();
            if(vol < 0) vol = 0;
            if(vol > 100) vol = 100;
            CommonUtils.musicManager.setVolume((int)vol);
            interaction.createImmediateResponder().setContent("Volume successfully updated to " + vol).respond();
            LogUtils.info("Volume set to " + vol);
        }
        LogUtils.info("End of command volume by '" + interaction.getUser().getName() + "'");
    }
}
