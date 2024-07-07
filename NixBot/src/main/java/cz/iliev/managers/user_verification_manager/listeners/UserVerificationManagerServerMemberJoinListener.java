package cz.iliev.managers.user_verification_manager.listeners;

import cz.iliev.managers.database_manager.entities.Member;
import cz.iliev.managers.database_manager.services.DatabaseMemberService;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class UserVerificationManagerServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        DatabaseMemberService.getMember(serverMemberJoinEvent.getUser().getId(), getMemberResponse -> {

            if(getMemberResponse instanceof Exception){
                LogUtils.fatalError("Error while getting member. Error: '" + ((Exception)getMemberResponse).getMessage() + "'");
                return;
            }

            if(getMemberResponse == null){
                Member member = new Member(serverMemberJoinEvent.getUser().getId(), serverMemberJoinEvent.getUser().getName(), System.currentTimeMillis());
                DatabaseMemberService.addMember(member, addMemberResponse -> {

                    if(addMemberResponse instanceof Exception){
                        LogUtils.fatalError("Error while adding member. Error: '" + ((Exception)addMemberResponse).getMessage() + "'");
                        return;
                    }
                    CommonUtils.userVerificationManager.sendCode(serverMemberJoinEvent.getUser());

                });
            }
            else {
                CommonUtils.userVerificationManager.sendCode(serverMemberJoinEvent.getUser());
            }

        });

    }
}
