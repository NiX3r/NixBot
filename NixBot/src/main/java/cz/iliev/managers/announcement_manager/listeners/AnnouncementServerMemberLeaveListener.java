package cz.iliev.managers.announcement_manager.listeners;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

public class AnnouncementServerMemberLeaveListener implements ServerMemberLeaveListener {
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent serverMemberLeaveEvent) {
        CommonUtils.announcementManager.sendLeave(serverMemberLeaveEvent.getUser().getName(), serverMemberLeaveEvent.getUser().getAvatar(), false);
    }
}
