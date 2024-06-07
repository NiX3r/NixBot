package cz.iliev.managers.announcement_manager.listeners;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

public class AnnouncementServerMemberBanListener implements ServerMemberBanListener {
    @Override
    public void onServerMemberBan(ServerMemberBanEvent serverMemberBanEvent) {
        CommonUtils.announcementManager.sendLeave(serverMemberBanEvent.getUser().getName(), serverMemberBanEvent.getUser().getAvatar(), true);
    }
}
