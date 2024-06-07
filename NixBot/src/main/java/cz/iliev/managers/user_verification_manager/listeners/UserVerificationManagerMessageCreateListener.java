package cz.iliev.managers.user_verification_manager.listeners;

import cz.iliev.managers.user_verification_manager.UserVerificationManager;
import cz.iliev.managers.user_verification_manager.instances.InviteInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class UserVerificationManagerMessageCreateListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageAuthor().isBotUser())
            return;

        if(messageCreateEvent.getChannel().getType().equals(ChannelType.PRIVATE_CHANNEL) &&
                CommonUtils.userVerificationManager.getUsersCodes().containsKey(messageCreateEvent.getMessageAuthor().getId())){
            if(CommonUtils.userVerificationManager.getUsersCodes().get(messageCreateEvent.getMessageAuthor().getId()).equals(messageCreateEvent.getMessageContent())){
                verificate(messageCreateEvent);
            }
            else {
                messageCreateEvent.getMessage().reply("Bad code. Please try it again");
                CommonUtils.userVerificationManager.getUsersCodes().remove(messageCreateEvent.getMessageAuthor().asUser().get().getId());
                CommonUtils.userVerificationManager.sendCode(messageCreateEvent.getMessageAuthor().asUser().get());
            }
        }

    }

    private void verificate(MessageCreateEvent messageCreateEvent){
        User user = messageCreateEvent.getMessageAuthor().asUser().get();
        Server server = CommonUtils.bot.getServers().stream().findFirst().get();

        // Give default roles
        addDefaultRole(user, server);

        // Check who invite
        long inviterId = checkWhoInvite(server);
        if(inviterId == 0)
            LogUtils.info("Can't find who invite '" + user.getName() + "'");
        else
            LogUtils.info("Member '" + user.getName() + "' was invited by member with id '" + inviterId + "'");

        // Send welcome message
        server.getMemberById(checkWhoInvite(server)).ifPresentOrElse(inviter -> {
            CommonUtils.announcementManager.sendWelcome(user.getName(), user.getAvatar(), user.getMentionTag());
        }, new Runnable() {
            @Override
            public void run() {
                CommonUtils.announcementManager.sendWelcome(user.getName(), user.getAvatar(), "NaN");
            }
        });

        // Update statistics
        CommonUtils.statisticsManager.getStatistics().getServerStatsInstance().incrementMemberJoin();
        LogUtils.info("Member join statistics updated");

        // Update activity users counter
        CommonUtils.botActivityManager.setActivity(ActivityType.WATCHING, "new member '" + user.getName() + "'", 60000);

        // Remove from verificate users codes list
        CommonUtils.userVerificationManager.getUsersCodes().remove(user.getId());

        messageCreateEvent.getMessage().reply("You successfully verificate. Now you can browse our server! :saluting_facing:");
        LogUtils.info("User successfully verificated and warmly welcomed");

    }

    private long checkWhoInvite(Server server){

        long output = 0;
        RichInvite indexOneInvite = null;
        for(RichInvite invite : server.getInvites().join()){
            InviteInstance inv = CommonUtils.userVerificationManager.getInviteByCode(invite.getCode());
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

                inv = CommonUtils.userVerificationManager.addInvite(new InviteInstance(invite.getCode(), inviter.getId(), 0));

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

        for(String roleId : UserVerificationManager.DEFAULT_ROLES_ID){
            try{
                if(server.getRoleById(roleId).isPresent()){
                    user.addRole(server.getRoleById(roleId).get()).join();
                    LogUtils.info("Role with id '" + roleId + "' added to '" + user.getName() + "'");
                }
            }
            catch (Exception ex){
                CommonUtils.throwException(ex);
            }
        }

    }
}
