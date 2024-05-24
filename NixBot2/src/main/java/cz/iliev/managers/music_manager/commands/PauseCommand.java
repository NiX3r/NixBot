package cz.iliev.managers.music_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PauseCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        if(CommonUtils.musicManager.isPaused()){
            interaction.createImmediateResponder().setContent("NixBot is already paused").setFlags(MessageFlag.EPHEMERAL).respond();
        }
        else {
            CommonUtils.musicManager.setPause(true);
            interaction.createImmediateResponder().setContent("NixBot song is paused").respond();
        }

        LogUtils.info("End of command pause by '" + interaction.getUser().getName() + "'");
    }
}
