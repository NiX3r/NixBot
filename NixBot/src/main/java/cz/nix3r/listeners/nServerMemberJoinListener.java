package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.InviteInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;
import org.apache.commons.logging.Log;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class nServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        // Give default roles
        addDefaultRole(serverMemberJoinEvent.getUser(), serverMemberJoinEvent.getServer());

        // Check who invite
        long inviterId = checkWhoInvite(serverMemberJoinEvent.getServer());
        if(inviterId == 0)
            LogSystem.log(LogType.INFO, "Can't find who invite '" + serverMemberJoinEvent.getUser().getName() + "'");
        else
            LogSystem.log(LogType.INFO, "Member '" + serverMemberJoinEvent.getUser().getName() + "' was invited by member with id '" + inviterId + "'");

        // Send welcome message
        serverMemberJoinEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            serverMemberJoinEvent.getServer().getMemberById(inviterId).ifPresent(inviter -> {
                channel.sendMessage(DiscordUtils.createJoinEmbed(serverMemberJoinEvent.getUser().getName(), inviter.getMentionTag(), serverMemberJoinEvent.getUser().getAvatar(), serverMemberJoinEvent.getServer())).join();
            });
        });
        LogSystem.log(LogType.INFO, "Welcome message sent");

        // Update statistics
        CommonUtils.statisticsManager.incrementMemberJoin();
        LogSystem.log(LogType.INFO, "Member join statistics updated");

        // Update activity users counter
        DiscordUtils.updateBotActivity("with " + serverMemberJoinEvent.getServer().getMembers().size() + " users");
        LogSystem.log(LogType.INFO, "Member " + serverMemberJoinEvent.getUser().getName() + " joined on the server. Bot activity updated. Event end");

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
                    LogSystem.log(LogType.INFO, "Role with id '" + roleId + "' added to '" + user.getName() + "'");
                }
            }
            catch (Exception ex){
                DiscordUtils.throwError(ex);
            }
        }

    }

}
