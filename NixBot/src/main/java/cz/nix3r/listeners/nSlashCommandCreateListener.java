package cz.nix3r.listeners;

import cz.nix3r.commands.StatusCommand;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class nSlashCommandCreateListener implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();

        switch (interaction.getCommandName()){

            case "status":
                StatusCommand.run(interaction);

        }

    }
}
