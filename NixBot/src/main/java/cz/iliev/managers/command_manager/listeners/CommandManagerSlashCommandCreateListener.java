package cz.iliev.managers.command_manager.listeners;

import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
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

        LogUtils.info("Command '" + interaction.getCommandName() + "' by '" + interaction.getUser().getName() + "' caught");

        // Increment command stats
        CommonUtils.statisticsManager.getStatistics().getCommandStatsInstance().incrementUsedCommand(interaction.getCommandName());
        // Increment user command stats
        CommonUtils.statisticsManager.getStatistics().getMemberStatsInstance().incrementUsedCommands(interaction.getUser().getId());

        switch (interaction.getCommandName()){

            case "status":
                if(CommonUtils.isUserAdmin(interaction.getUser()))
                    CommonUtils.mainManager.onCommand(interaction);
                break;

            case "manager":
                CommonUtils.mainManager.onCommand(interaction);
                break;

            case "play": case "queue": case "skip": case "pause": case "unpause": case "volume":
                if(checkIsCmdChannel(interaction))
                    CommonUtils.musicManager.onCommand(interaction);
                break;

            case "dice": case "anonymous": case "phonetic":
                if(checkIsCmdChannel(interaction))
                    CommonUtils.commandManager.getCommandByName(interaction.getCommandName()).run(interaction);
                break;

            case "ticket":
                CommonUtils.ticketManager.onCommand(interaction);
                break;

            case "role":
                CommonUtils.roleManager.onCommand(interaction);
                break;

            case "announcement":
                CommonUtils.announcementManager.onCommand(interaction);
                break;

            case "ban": case "unban": case "kick": case "mute":
                CommonUtils.banListManager.onCommand(interaction);
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
