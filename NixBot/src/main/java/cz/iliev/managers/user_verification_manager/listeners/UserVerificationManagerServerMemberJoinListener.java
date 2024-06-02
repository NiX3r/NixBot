package cz.iliev.managers.user_verification_manager.listeners;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class UserVerificationManagerServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {
        CommonUtils.userVerificationManager.sendCode(serverMemberJoinEvent.getUser());
    }
}
