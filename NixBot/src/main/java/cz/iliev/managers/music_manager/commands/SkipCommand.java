package cz.iliev.managers.music_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SkipCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        CommonUtils.musicManager.playNext();
        interaction.createImmediateResponder().setContent("Skipped song successfully").respond();
        LogUtils.info("End of command skip by '" + interaction.getUser().getName() + "'");
    }
}
