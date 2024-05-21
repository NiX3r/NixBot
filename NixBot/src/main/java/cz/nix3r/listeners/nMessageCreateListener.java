package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.enums.TicketStatus;
import cz.nix3r.instances.InviteInstance;
import cz.nix3r.instances.Ticket;
import cz.nix3r.instances.TicketMember;
import cz.nix3r.instances.TicketMessage;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class nMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        if(messageCreateEvent.getMessage().getFlags().contains(MessageFlag.EPHEMERAL))
            return;

        LogSystem.info("Message sent by '" + messageCreateEvent.getMessageAuthor().getName() + "'");

        if(messageCreateEvent.getChannel().getType().equals(ChannelType.PRIVATE_CHANNEL) &&
            CommonUtils.verificationManager.getUsersCodes().containsKey(messageCreateEvent.getMessageAuthor().getId())){

            if(CommonUtils.verificationManager.getUsersCodes().get(messageCreateEvent.getMessageAuthor().getId()).equals(messageCreateEvent.getMessageContent())){
                verificate(messageCreateEvent);
            }
            else {
                messageCreateEvent.getMessage().reply("Bad code. Please try it again");
                return;
            }

        }

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
                            LogSystem.info("Attachment '" + fullpath + "' saved");

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
                    LogSystem.info("Ticket message by '" + messageCreateEvent.getMessageAuthor().getName() + "' saved");

                }
                // Add to statistics (only if it's in category, and it's not tickets category)
                else if(!category.getIdAsString().equals(CommonUtils.PROGRAMMING_CATEGORY)){
                    CommonUtils.statisticsManager.incrementTextCounter();
                    CommonUtils.statisticsManager.incrementUsedTextChannelId(textChannel.getId());
                    CommonUtils.statisticsManager.incrementBestUserTextCounterMonth(messageCreateEvent.getMessageAuthor().getId());
                    LogSystem.info("Server statistics updated");
                }

            });

        });

        LogSystem.info("End of message sent event by '" + messageCreateEvent.getMessageAuthor().getName() + "'");

    }

    private void verificate(MessageCreateEvent messageCreateEvent){
        User user = messageCreateEvent.getMessageAuthor().asUser().get();
        Server server = CommonUtils.bot.getServers().stream().findFirst().get();

        // Give default roles
        addDefaultRole(user, server);

        // Check who invite
        long inviterId = checkWhoInvite(server);
        if(inviterId == 0)
            LogSystem.info("Can't find who invite '" + user.getName() + "'");
        else
            LogSystem.info("Member '" + user.getName() + "' was invited by member with id '" + inviterId + "'");

        // Send welcome message
        server.getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createJoinEmbed(user.getName(), server.getMemberById(inviterId).isPresent() ? server.getMemberById(inviterId).get().getMentionTag() : "NaN", user.getAvatar(), server)).join();
        });
        LogSystem.info("Welcome message sent");

        // Update statistics
        CommonUtils.statisticsManager.incrementMemberJoin();
        LogSystem.info("Member join statistics updated");

        // Update activity users counter
        DiscordUtils.updateBotActivity("with " + server.getMembers().size() + " users");

        // Remove from verificate users codes list
        CommonUtils.verificationManager.getUsersCodes().remove(user.getId());

        messageCreateEvent.getMessage().reply("You successfully verificate. Now you can browse our server! :salut:");

    }

    private long checkWhoInvite(Server server){

        long output = 0;
        RichInvite indexOneInvite = null;
        for(RichInvite invite : server.getInvites().join()){
            InviteInstance inv = CommonUtils.inviteManager.getInviteByCode(invite.getCode());
            if(inv != null){
                if(inv.getUses() != invite.getUses()){
                    inv.incrementUses();
                    output = inv.getCreator_id();
                }
            }
            else{

                User inviter = invite.getInviter().isPresent() ? invite.getInviter().get() : null;

                if(inviter == null)
                    continue;

                inv = CommonUtils.inviteManager.addInvite(new InviteInstance(invite.getCode(), inviter.getId(), 0));

                if(invite.getUses() == 1){
                    if(inv.getUses() != invite.getUses()){
                        inv.incrementUses();
                        output = inv.getCreator_id();
                    }
                }

            }
        }
        return output;

    }

    private void addDefaultRole(User user, Server server){

        for(String roleId : CommonUtils.DEFAULT_ROLES_ID){
            try{
                if(server.getRoleById(roleId).isPresent()){
                    user.addRole(server.getRoleById(roleId).get()).join();
                    LogSystem.info("Role with id '" + roleId + "' added to '" + user.getName() + "'");
                }
            }
            catch (Exception ex){
                DiscordUtils.throwError(ex);
            }
        }

    }

}