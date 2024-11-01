package cz.iliev.managers.ticket_manager.listeners;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.managers.statistics_manager.behaviors.TicketBehavior;
import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.ticket_manager.enums.TicketStatus;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketMemberInstance;
import cz.iliev.managers.ticket_manager.instances.TicketMessageInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.awt.*;
import java.util.ArrayList;

public class TicketManagerMessageComponentCreateListener implements MessageComponentCreateListener {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();
        messageComponentInteraction.getServer().ifPresent(server -> {
            messageComponentInteraction.getChannel().ifPresent(textChannel -> {
                if(customId.contains("nix-ticket-")){
                    if(customId.equals("nix-ticket-close") || customId.equals("nix-ticket-resolve"))
                        switch (CommonUtils.ticketManager.closeTicket(server, textChannel, messageComponentInteraction.getUser(), customId.equals("nix-ticket-resolve"))){
                            case -1:
                                messageComponentInteraction.createImmediateResponder().setContent("Can't find ticket channel").setFlags(MessageFlag.EPHEMERAL).respond();
                                break;
                            case -2:
                                messageComponentInteraction.createImmediateResponder().setContent("You have no right to close this ticket. Tickets can be closed only by staff or ticket owner").setFlags(MessageFlag.EPHEMERAL).respond();
                                break;
                        }
                    else
                        createTicket(messageComponentCreateEvent.getInteraction(), customId.replace("nix-ticket-", ""));
                }
            });
        });
    }

    private void createTicket(Interaction interaction, String type){

        if(!interaction.getServer().isPresent()){
            interaction.createImmediateResponder().setContent("This button works only on server!").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        User user = interaction.getUser();
        Server server = interaction.getServer().get();

        if(CommonUtils.ticketManager.getTicketByOwner(user.getId()) != null){
            interaction.createImmediateResponder().setContent("Only one ticket can be opened at the time. Please firstly solve or close old ticket").setFlags(MessageFlag.EPHEMERAL).respond();
            LogUtils.warning("User '" + user.getName() + "' tried to open second ticket");
            return;
        }

        TicketMemberInstance author = new TicketMemberInstance(user.getId(), user.getName());
        TicketInstance ticket = new TicketInstance(CommonUtils.ticketManager.getIndex(),
                author,
                System.currentTimeMillis(),
                -1,
                type,
                TicketStatus.WAITING_FOR_USER,
                new ArrayList<TicketMemberInstance>(){{add(author);}},
                new ArrayList<TicketMessageInstance>());

        TextChannel channel = server.createTextChannelBuilder()
                .setCategory(server.getChannelCategoryById(TicketManager.TICKET_CATEGORY_ID).get())
                .setName(ticket.getId() + " " + ticket.getAuthor().getName())
                .setTopic("**Author:** " + ticket.getAuthor().getName() + " **| Topic:** " + ticket.getTopic())
                .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                .addPermissionOverwrite(user, Permissions.fromBitmask(1024))
                .create().join();

        ticket.setChannelId(channel.getId());

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode("#61B23A"))
                .setTitle("Ticket system | Ticket channel")
                .setDescription("This channel was made for ticket purpose. Please try to describe your problem/question as much as possible. Thanks you")
                .addField("Ping pong!", user.getMentionTag())
                .setFooter("Version: " + CommonUtils.VERSION);

        new MessageBuilder()
                .setEmbed(builder)
                .addComponents(
                        ActionRow.of(org.javacord.api.entity.message.component.Button.secondary("nix-ticket-close", "Close ticket", EmojiParser.parseToUnicode(":no_entry_sign:"))),
                        ActionRow.of(Button.secondary("nix-ticket-resolve", "Mark as solved", EmojiParser.parseToUnicode(":white_check_mark:")))
                )
                .send(channel);

        CommonUtils.ticketManager.addTicket(ticket);
        CommonUtils.ticketManager.incrementIndex();

        TicketBehavior.behave();

        interaction.createImmediateResponder().setContent("Ticket channel created. You will be moved to there by clicking here " + channel.asServerTextChannel().get().getMentionTag()).setFlags(MessageFlag.EPHEMERAL).respond();
        LogUtils.info("Ticket '" + (ticket.getId() + "-" + ticket.getAuthor().getName()) + "' created");

    }

}
