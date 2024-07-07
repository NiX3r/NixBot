package cz.iliev.managers.ticket_manager.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.database_manager.services.DatabaseTicketService;
import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.ticket_manager.instances.TicketType;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class MessageCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        if(!interaction.getServer().isPresent()){
            interaction.createImmediateResponder().setContent("This channel is only for server use").respond();
            return;
        }

        if(!CommonUtils.isUserAdmin(interaction.getUser())){
            interaction.createImmediateResponder().setContent("This command is only for administrators").respond();
            return;
        }

        DatabaseTicketService.getTicketTypes(response -> {

            if(response instanceof Exception){
                LogUtils.fatalError("Error while getting ticket types. Error: '" + ((Exception)response).getMessage() + "'");
                return;
            }

            var types = (java.util.List<TicketType>)response;
            TextChannel channel = interaction.getServer().get().getTextChannelById(TicketManager.TICKET_CHANNEL_ID).get();

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(Color.decode("#61B23A"))
                    .setTitle("Ticket Manager")
                    .setDescription("Hello! :wave: \nDon't be shy and ask anything!")
                    .addField("Rule #1", "Don't abuse this manager")
                    .addField("Rule #2", "After create a ticket, please describe as much as possible your problem")
                    .addField("Rule #3", "Whole conversation is archived for future development")
                    .setFooter("Version: " + CommonUtils.VERSION);

            var message = new MessageBuilder().setEmbed(builder);

            for(var type : types){
                message.addComponents(
                        ActionRow.of(Button.secondary("nix-ticket-" + type.getName(), type.getName() + " - " + type.getDescription(), EmojiParser.parseToUnicode(":" + type.getEmoji() + ":")))
                );
            }
            message.send(channel);

            interaction.createImmediateResponder().setContent("Sent").respond();

        });


        LogUtils.info("End of command message by '" + interaction.getUser().getName() + "'");
    }
}
