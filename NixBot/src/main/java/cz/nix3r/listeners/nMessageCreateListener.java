package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.enums.TicketStatus;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.instances.TicketMessage;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
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
                            new HashMap<String, String>());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(ticket.getCreateDate());

                    for(MessageAttachment attachment : messageCreateEvent.getMessage().getAttachments()){
                        String key, fileName;
                        try {

                            byte[] file = attachment.asByteArray().get();
                            System.out.println(file.length);
                            fileName = attachment.getFileName();
                            key = fileName.substring(0, fileName.indexOf("."));
                            String path = FileUtils.getTicketArchiveFullPath(ticket);

                            new File(path).mkdirs();

                            try (FileOutputStream fos = new FileOutputStream(path + "/" + fileName)) {
                                fos.write(file);
                                fos.flush();
                            }

                            message.addAttachment(key, path + "/" + fileName);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
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
                    LogSystem.log(LogType.INFO, "Ticket message by '" + messageCreateEvent.getMessageAuthor().getName() + "' saved");

                }
                // Add to statistics (only if it's in category, and it's not tickets category)
                else if(!category.getIdAsString().equals(CommonUtils.PROGRAMMING_CATEGORY)){
                    CommonUtils.statisticsManager.incrementTextCounter();
                    CommonUtils.statisticsManager.incrementUsedTextChannelId(textChannel.getId());
                    CommonUtils.statisticsManager.incrementBestUserTextCounterMonth(messageCreateEvent.getMessageAuthor().getId());
                }

            });

        });
    }
}