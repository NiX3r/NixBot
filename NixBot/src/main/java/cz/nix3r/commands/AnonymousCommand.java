package cz.nix3r.commands;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AnonymousCommand {
    public static void run(SlashCommandInteraction interaction) {

        interaction.getServer().ifPresent(server -> {

            server.getTextChannelById(CommonUtils.UNKNOWN_CHANNEL_ID).ifPresent(channel -> {

                String message = interaction.getArgumentStringValueByIndex(0).get();

                channel.sendMessage("## Anonymous message\n```" + message + "```").join();
                interaction.createImmediateResponder().setContent("Message sent!").setFlags(MessageFlag.EPHEMERAL).respond().join();
                LogSystem.info("End of command anonymous by '" + interaction.getUser().getName() + "'. Message: `" + message + "`");

            });

        });

    }
}
