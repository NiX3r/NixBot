package cz.iliev.managers.ticket_manager.listeners;

import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.ticket_manager.enums.TicketStatus;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketMemberInstance;
import cz.iliev.managers.ticket_manager.instances.TicketMessageInstance;
import cz.iliev.managers.ticket_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class TicketManagerMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        if(messageCreateEvent.getMessage().getFlags().contains(MessageFlag.EPHEMERAL))
            return;

        messageCreateEvent.getChannel().asServerTextChannel().ifPresent(textChannel -> {

            textChannel.getCategory().ifPresent(category -> {

                if(category.getIdAsString().equals(TicketManager.TICKET_CATEGORY_ID)){

                    if(!CommonUtils.ticketManager.getActiveTickets().containsKey(textChannel.getId()))
                        return;

                    TicketInstance ticket = CommonUtils.ticketManager.getActiveTickets().get(textChannel.getId());
                    TicketMessageInstance message = new TicketMessageInstance(messageCreateEvent.getMessageId(),
                            new TicketMemberInstance(messageCreateEvent.getMessageAuthor().getId(), messageCreateEvent.getMessageAuthor().getName()),
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
                                CommonUtils.throwException(ex);
                            }

                            message.addAttachment(key, fullpath);
                            LogUtils.info("Attachment '" + fullpath + "' saved");

                        } catch (Exception ex){
                            CommonUtils.throwException(ex);
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
                        ticket.getMembers().add(new TicketMemberInstance(
                                messageCreateEvent.getMessageAuthor().getId(),
                                messageCreateEvent.getMessageAuthor().getName()
                        ));

                    ticket.getMessages().add(message);
                    LogUtils.info("Ticket message by '" + messageCreateEvent.getMessageAuthor().getName() + "' saved");

                }

            });

        });

    }
}
