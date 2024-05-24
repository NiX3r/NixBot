package cz.iliev.interfaces;

import org.javacord.api.interaction.SlashCommandInteraction;

public interface ISlashCommand {
    public void run(SlashCommandInteraction interaction);
}
