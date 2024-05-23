package cz.iliev.managers.command_manager.interfaces;

import org.javacord.api.interaction.SlashCommandInteraction;

public interface ISlashCommand {
    public void run(SlashCommandInteraction interaction);
}
