package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.InviteInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
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

        // Send welcome message
        serverMemberJoinEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createJoinEmbed(serverMemberJoinEvent.getUser().getName(), inviterId, serverMemberJoinEvent.getUser().getAvatar(), serverMemberJoinEvent.getServer())).join();
        });

        // Update users counter
        CommonUtils.bot.updateActivity(ActivityType.PLAYING, "with " + serverMemberJoinEvent.getServer().getMembers().size() + " users");
        LogSystem.log(LogType.INFO, "Member " + serverMemberJoinEvent.getUser().getName() + " joined on the server. Bot activity updated");

    }

    private long checkWhoInvite(Server server){

        for(RichInvite invite : server.getInvites().join()){
            System.out.println(invite.getCode());
            InviteInstance inv = CommonUtils.inviteManager.getInviteByCode(invite.getCode());
            if(inv != null){
                System.out.println(inv.getUses() + " == " + invite.getUses());
                if(inv.getUses() != invite.getUses()){
                    inv.incrementUses();
                    return inv.getCreator_id();
                }
            }
        }
        return 0;

    }

    private void addDefaultRole(User user, Server server){

        for(String roleIUd : CommonUtils.DEFAULT_ROLES_ID){
            try{
                user.addRole(server.getRoleById(roleIUd).get()).join();
            }
            catch (Exception ex){
                DiscordUtils.throwError("Default role " + roleIUd + " does not exists", "nServerMemberJoinListener.addDefaultRole");
            }
        }

    }

}
