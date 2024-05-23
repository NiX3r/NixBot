package cz.iliev.managers.command_manager.listeners;

import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.command_manager.utils.CommandManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class CommandManagerSlashCommandCreateListener implements SlashCommandCreateListener {
    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {

        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();

        if(!interaction.getServer().isPresent()){
            interaction.createImmediateResponder().setContent("Commands are allowed only on server.").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        LogUtils.info("Command '" + interaction.getCommandName() + "' by '" + interaction.getUser().getName() + "' catched");

        // TODO - add statistics

        switch (interaction.getCommandName()){

            case "status":
                StatusCommand.run(interaction);
                break;

            case "play": case "queue": case "skip": case "pause": case "unpause": case "volume":
                if(checkIsCmdChannel(interaction))
                    CommonUtils.musicManager.onCommand(interaction);
                break;

            case "dice":
                if(checkIsCmdChannel(interaction))
                    CommandManagerUtils.rollDice(interaction);
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

            case "announcement":
                AnnouncementCommand.run(interaction);
                break;

        }

    }

    private boolean checkIsCmdChannel(SlashCommandInteraction interaction){

        if(interaction.getChannel().isPresent() &&
                interaction.getChannel().get().getIdAsString().equals(CommandManager.CMD_CHANNEL_ID))
            return true;

        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommandManager.CMD_CHANNEL_ID).ifPresent(channel -> {
                interaction.createImmediateResponder().setContent("Please type your command in " + channel.getMentionTag()).setFlags(MessageFlag.EPHEMERAL).respond().join();
            });
        });

        return false;

    }

}
