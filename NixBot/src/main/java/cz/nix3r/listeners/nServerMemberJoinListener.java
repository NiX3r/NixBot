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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class nServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        if(CommonUtils.verificationManager.getUsersCodes().containsKey(serverMemberJoinEvent.getUser().getId())){
            return;
        }

        CommonUtils.verificationManager.sendCode(serverMemberJoinEvent.getUser());
        LogSystem.info("Member " + serverMemberJoinEvent.getUser().getName() + " joined on the server. Added to verificate queue");

    }

}
