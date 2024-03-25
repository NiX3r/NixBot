package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.enums.TicketStatus;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.instances.TicketMessage;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class nMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        if(messageCreateEvent.getMessage().getFlags().contains(MessageFlag.EPHEMERAL))
            return;

        LogSystem.log(LogType.INFO, "Message sent by '" + messageCreateEvent.getMessageAuthor().getName() + "'");

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
                            File file1 = new File(path);
                            file1.mkdirs();
                            String fullpath = path + "/" + Objects.requireNonNull(file1.listFiles()).length + "-" + fileName;

                            try (FileOutputStream fos = new FileOutputStream(fullpath)) {
                                fos.write(file);
                                fos.flush();
                            }
                            catch (Exception ex){
                                DiscordUtils.throwError(ex);
                            }

                            message.addAttachment(key, fullpath);
                            LogSystem.log(LogType.INFO, "Attachment '" + fullpath + "' saved");

                        } catch (Exception ex){
                            DiscordUtils.throwError(ex);
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
                    LogSystem.log(LogType.INFO, "Server statistics updated");
                }

            });

        });

        LogSystem.log(LogType.INFO, "End of message sent event by '" + messageCreateEvent.getMessageAuthor().getName() + "'");

    }
}