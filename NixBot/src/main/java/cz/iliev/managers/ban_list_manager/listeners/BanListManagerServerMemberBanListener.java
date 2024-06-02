package cz.iliev.managers.ban_list_manager.listeners;

import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.BanInstance;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;

import java.util.ArrayList;
import java.util.List;

public class BanListManagerServerMemberBanListener implements ServerMemberBanListener {
    @Override
    public void onServerMemberBan(ServerMemberBanEvent serverMemberBanEvent) {

        List<String> roleNames = new ArrayList<String>();
        for(var role : serverMemberBanEvent.getUser().getRoles(serverMemberBanEvent.getServer())){
            roleNames.add(role.getName());
        }

        // TODO

    }
}
