package cz.iliev.managers.announcement_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.announcement_manager.utils.AnnouncementManagerUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AnnouncementCommand implements ISlashCommand {

    @Override
    public void run(SlashCommandInteraction interaction) {
        interaction.getServer().ifPresent(server -> {
            if(!CommonUtils.isUserAdmin(interaction.getUser())){
                interaction.createImmediateResponder().setContent("You're not permitted to use this command").setFlags(MessageFlag.EPHEMERAL).respond().join();
                return;
            }

            interaction.getArgumentStringValueByIndex(0).ifPresent(topic -> {
                interaction.getArgumentStringValueByIndex(1).ifPresent(message -> {
                    server.getTextChannelById(AnnouncementManager.NEWS_CHANNEL_ID).ifPresent(newsChannel -> {
                        EmbedBuilder embed = AnnouncementManagerUtils.createAnnouncementEmbed(topic, message, interaction.getUser().getName());
                        newsChannel.sendMessage(embed).join();
                        interaction.createImmediateResponder().setContent("Announcement sent").setFlags(MessageFlag.EPHEMERAL).respond();
                    });
                });
            });
        });
        LogUtils.info("End of command announcement by '" + interaction.getUser().getName() + "'");
    }
}
