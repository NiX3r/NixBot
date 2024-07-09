package cz.iliev.managers.ban_list_manager.listeners;

import cz.iliev.managers.database_manager.services.DatabaseBanService;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.time.Duration;

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
                    String actionName = "NONE";
                    switch (punishment.getType()){
                        case BAN:
                            // Do the magic
                            actionName = "banned";
                            if(punishment.getDuration() == 0){
                                server.banUser(member.getId(), Duration.ofSeconds(250), punishment.getDescription()).join();
                            }
                            else {
                                server.banUser(member, Duration.ofSeconds(punishment.getDuration()), punishment.getDescription());
                            }
                            break;
                        case UNBAN:
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

                    // Add punishment do database
                    CommonUtils.banListManager.getBans().put(punishment.getMember().getMemberId(), punishment);
                    DatabaseBanService.addPunishment(
                            punishment.getMember().getMemberId(),
                            punishment.getAdmin().getMemberId(),
                            punishment.getType(),
                            punishment.getTime(),
                            punishment.getDuration(),
                            punishment.getDescription(),
                            response -> {
                                if(response instanceof Exception){
                                    LogUtils.fatalError("Error while adding punishment to database. Error: '" + ((Exception)response).getMessage() + "'");
                                    return;
                                }
                                LogUtils.info("Successfully added punishment to database");
                            }
                    );

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
