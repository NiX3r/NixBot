package cz.iliev.managers.ban_list_manager.listeners;

import cz.iliev.managers.ban_list_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class BanListManagerMessageComponentCreateListener implements MessageComponentCreateListener {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();
        messageComponentInteraction.getServer().ifPresent(server -> {
            messageComponentInteraction.getChannel().ifPresent(textChannel -> {
                if(customId.contains("nix-ban-undo-")){
                    long memberId = Long.parseLong(customId.replace("nix-ban-undo-", ""));
                    undo(memberId, messageComponentInteraction);
                }
                if(customId.contains("nix-ban-confirm-")){
                    long memberId = Long.parseLong(customId.replace("nix-ban-confirm-", ""));
                    confirm(memberId, messageComponentInteraction);
                }
            });
        });
    }

    private void undo(long memberId, MessageComponentInteraction messageComponentInteraction){
        if(CommonUtils.banListManager.getBanCache().containsKey(memberId)){
            CommonUtils.banListManager.getBanCache().remove(memberId);
            messageComponentInteraction.createImmediateResponder().setContent("Successfully undo punishment for '" + memberId + "'").setFlags(MessageFlag.EPHEMERAL).respond();
            messageComponentInteraction.getMessage().delete();
        }
        else {
            messageComponentInteraction.createImmediateResponder().setContent("Unfortunately I can't find match for this member in cache").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }
    }

    private void confirm(long memberId, MessageComponentInteraction messageComponentInteraction){
        if(CommonUtils.banListManager.getBanCache().containsKey(memberId)){
            var punishment = CommonUtils.banListManager.getBanCache().get(memberId);
            CommonUtils.bot.getServers().forEach(server -> {
                if(!server.getIdAsString().equals(CommonUtils.NIX_CREW_ID)){
                    CommonUtils.politeDisconnect(server);
                    return;
                }

                if(!server.getMemberById(punishment.getMember().getMemberId()).isPresent()){
                    messageComponentInteraction.createImmediateResponder().setContent("Unfortunately this member is not on Nix Crew server").setFlags(MessageFlag.EPHEMERAL).respond();
                    return;
                }

                server.getMemberById(punishment.getMember().getMemberId()).ifPresent(member -> {
                    FileUtils.savePunishment(punishment);
                    String actionName = "NONE";
                    switch (punishment.getType()){
                        case BAN:
                            // Update in active ban list
                            CommonUtils.banListManager.getBans().put(punishment.getMember().getMemberId(), punishment);
                            // Do the magic
                            actionName = "banned";
                            if(punishment.getDuration() == 0){
                                server.banUser(member, Duration.ofDays(999999999), punishment.getDescription());
                            }
                            else {
                                server.banUser(member, punishment.getDuration(), TimeUnit.MILLISECONDS,punishment.getDescription());
                            }
                            break;
                        case UNBAN:
                            // Update in active ban list
                            CommonUtils.banListManager.getBans().remove(punishment.getMember().getMemberId());
                            actionName = "unbanned";
                            // Do the magic
                            server.unbanUser(punishment.getMember().getMemberId(), punishment.getDescription());
                            break;
                        case KICK:
                            actionName = "kicked";
                            // Do the magic
                            server.kickUser(member, punishment.getDescription());
                            break;
                        case TIMEOUT:
                            actionName = "timeouted";
                            // Do the magic
                            Duration duration = Duration.ofMillis(punishment.getDuration());
                            server.timeoutUser(member, duration, punishment.getDescription());
                            break;
                    }
                    CommonUtils.botActivityManager.setActivity(ActivityType.WATCHING, " new " + actionName + " member '" + punishment.getMember().getMemberName() + "'", 60000);
                    messageComponentInteraction.createImmediateResponder().setContent("Successfully " + actionName + " member '" + punishment.getMember().getMemberName() + "'").setFlags(MessageFlag.EPHEMERAL).respond();
                    messageComponentInteraction.getMessage().delete();
                });
            });
        }
        else {
            messageComponentInteraction.createImmediateResponder().setContent("Unfortunately I can't find match for this member in cache").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }
    }
}
