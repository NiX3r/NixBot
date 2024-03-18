package cz.nix3r.listeners;

import cz.nix3r.commands.*;
import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class nSlashCommandCreateListener implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();

        LogSystem.log(LogType.INFO, "Command '" + interaction.getCommandName() + "' by '" + interaction.getUser().getName() + "' catched");

        CommonUtils.statisticsManager.incrementCommandsUsed(interaction.getUser().getId(), interaction.getCommandName());

        switch (interaction.getCommandName()){

            case "status":
                StatusCommand.run(interaction);
                break;

            case "play":
                if(checkIsCmdChannel(interaction))
                    PlayCommand.run(interaction);
                break;

            case "queue":
                if (checkIsCmdChannel(interaction))
                    QueueCommand.run(interaction);
                break;

            case "skip":
                if(checkIsCmdChannel(interaction))
                    SkipCommand.run(interaction);
                break;

            case "pause":
                if (checkIsCmdChannel(interaction))
                    PauseCommand.run(interaction);
                break;

            case "unpause":
                if (checkIsCmdChannel(interaction))
                    UnpauseCommand.run(interaction);
                break;

            case "volume":
                if (checkIsCmdChannel(interaction))
                    VolumeCommand.run(interaction);
                break;

            case "dice":
                if(checkIsCmdChannel(interaction))
                    DiceCommand.run(interaction);
                break;

            case "anonymous":
                if(checkIsCmdChannel(interaction))
                    AnonymousCommand.run(interaction);
                break;

            case "ticket":
                TicketCommand.run(interaction);
                break;

            case "phonetic":
                if(checkIsCmdChannel(interaction))
                    PhoneticCommand.run(interaction);
                break;

            case "role":
                RoleCommand.run(interaction);
                break;

        }

    }

    private boolean checkIsCmdChannel(SlashCommandInteraction interaction){

        if(interaction.getChannel().isPresent()){
            if(interaction.getChannel().get().getIdAsString().equals(CommonUtils.CMD_CHANNEL_ID))
                return true;
        }

        ((Server)CommonUtils.bot.getServers().toArray()[0]).getTextChannelById(CommonUtils.CMD_CHANNEL_ID).ifPresent(serverTextChannel -> {
            interaction.createImmediateResponder().setContent("Please type your command in " + serverTextChannel.getMentionTag()).setFlags(MessageFlag.EPHEMERAL).respond().join();
        });

        return false;

    }

}
