package cz.nix3r.commands;

import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AnnouncementCommand {
    public static void run(SlashCommandInteraction interaction) {

        interaction.getServer().ifPresent(server -> {

            if(!CommonUtils.isUserAdmin(server, interaction.getUser())){
                interaction.createImmediateResponder().setContent("You're not permitted to use this command").setFlags(MessageFlag.EPHEMERAL).respond().join();
                return;
            }

            interaction.getArgumentStringValueByIndex(0).ifPresent(topic -> {

                interaction.getArgumentStringValueByIndex(1).ifPresent(message -> {

                    server.getTextChannelById(CommonUtils.NEWS_CHANNEL_ID).ifPresent(newsChannel -> {
                        EmbedBuilder embed = DiscordUtils.createAnnouncementEmbed(topic, message, interaction.getUser().getName());
                        newsChannel.sendMessage(embed).join();
                        interaction.createImmediateResponder().setContent("Announcement sent").setFlags(MessageFlag.EPHEMERAL).respond();
                    });

                });

            });

        });

        LogSystem.info("End of command announcement by '" + interaction.getUser().getName() + "'");

    }
}
