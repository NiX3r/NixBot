package cz.nix3r.listeners;

import cz.nix3r.enums.TicketStatus;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.instances.TicketMessage;
import cz.nix3r.utils.CommonUtils;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class nMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        messageCreateEvent.getChannel().asServerTextChannel().ifPresent(textChannel -> {

            textChannel.getCategory().ifPresent(category -> {

                if(category.getIdAsString().equals(CommonUtils.SUBMIT_CATEGORY_ID)){

                    if(!CommonUtils.ticketManager.getActiveTickets().containsKey(textChannel.getId()))
                        return;

                    Ticket ticket = CommonUtils.ticketManager.getActiveTickets().get(textChannel.getId());
                    TicketMessage message = new TicketMessage(messageCreateEvent.getMessageId(),
                            new TicketMember(messageCreateEvent.getMessageAuthor().getId(), messageCreateEvent.getMessageAuthor().getName()),
                            messageCreateEvent.getMessageContent(),
                            new ArrayList<byte[]>());

                    for(MessageAttachment attachment : messageCreateEvent.getMessage().getAttachments()){
                        try {
                            message.getFiles().add(attachment.asByteArray().get());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    if(messageCreateEvent.getServer().get().hasPermission(
                            messageCreateEvent.getServer().get().getMemberById(messageCreateEvent.getMessageAuthor().getId()).get(),
                            PermissionType.ADMINISTRATOR
                    ))
                        ticket.setStatus(TicketStatus.WAITING_FOR_USER);
                    else
                        ticket.setStatus(TicketStatus.WAITING_FOR_SUPPORT);

                    if(!ticket.hasMember(messageCreateEvent.getMessageAuthor().getId()))
                        ticket.getMembers().add(new TicketMember(
                                messageCreateEvent.getMessageAuthor().getId(),
                                messageCreateEvent.getMessageAuthor().getName()
                        ));

                    ticket.getMessages().add(message);

                }

            });

        });
    }
}