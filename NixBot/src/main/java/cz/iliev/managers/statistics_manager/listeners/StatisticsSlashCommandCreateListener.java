package cz.iliev.managers.statistics_manager.listeners;

import cz.iliev.managers.statistics_manager.behaviors.CommandUseBehavior;
import cz.iliev.managers.statistics_manager.behaviors.UserCommandBehavior;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class StatisticsSlashCommandCreateListener implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
        var interaction = slashCommandCreateEvent.getSlashCommandInteraction();
        if(!interaction.getServer().isPresent()){
            return;
        }
        CommandUseBehavior.behave(interaction.getCommandName());
        UserCommandBehavior.behave(interaction.getUser().getId(), interaction.getCommandName());
        LogUtils.info("Command statistics updated");
    }
}