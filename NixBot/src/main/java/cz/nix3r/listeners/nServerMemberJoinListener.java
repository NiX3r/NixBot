package cz.nix3r.listeners;

import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class nServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        // Give default roles
        addDefaultRole(serverMemberJoinEvent.getUser(), serverMemberJoinEvent.getServer());

        // Send welcome message
        serverMemberJoinEvent.getServer().getTextChannelById(CommonUtils.WELCOME_CHANNEL_ID).ifPresent(channel -> {
            channel.sendMessage(DiscordUtils.createJoinEmbed(serverMemberJoinEvent.getUser().getName(), serverMemberJoinEvent.getUser().getAvatar(), serverMemberJoinEvent.getServer())).join();
        });
        CommonUtils.bot.updateActivity(ActivityType.PLAYING, "with " + serverMemberJoinEvent.getServer().getMembers().size() + " users");

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
