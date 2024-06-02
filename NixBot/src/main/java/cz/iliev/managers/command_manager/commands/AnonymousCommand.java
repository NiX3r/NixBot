package cz.iliev.managers.command_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AnonymousCommand implements ISlashCommand {

    private final String ANONYMOUS_CHANNEL_ID = "1119262818101903410";

    @Override
    public void run(SlashCommandInteraction interaction) {
        interaction.getServer().ifPresent(server -> {

            server.getTextChannelById(ANONYMOUS_CHANNEL_ID).ifPresent(channel -> {

                String message = interaction.getArgumentStringValueByIndex(0).get();

                channel.sendMessage("## Anonymous message\n```" + message + "```").join();
                interaction.createImmediateResponder().setContent("Message sent!").setFlags(MessageFlag.EPHEMERAL).respond().join();
                LogUtils.info("End of command anonymous by '" + interaction.getUser().getName() + "'. Message: `" + message + "`");

            });

        });
    }
}
