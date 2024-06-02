package cz.iliev.managers.ticket_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.ticket_manager.utils.TicketManagerUtils;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ResolveCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        TicketManagerUtils.close(interaction, true);
    }
}
