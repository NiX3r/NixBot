package cz.iliev.managers.security_manager.listeners;

import cz.iliev.managers.security_manager.scams.ImpersonationScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.user.UserChangeNicknameListener;

import java.awt.*;

public class SecurityManagerUserChangeNicknameListener implements UserChangeNicknameListener {
    @Override
    public void onUserChangeNickname(UserChangeNicknameEvent userChangeNicknameEvent) {

        ImpersonationScam impersonationScam = new ImpersonationScam();
        if(!userChangeNicknameEvent.getNewNickname().isPresent())
            return;
        if(impersonationScam.checkScam(userChangeNicknameEvent.getNewNickname().get(), userChangeNicknameEvent.getUser())){
            String oldName = userChangeNicknameEvent.getOldNickname().isPresent() ? userChangeNicknameEvent.getOldNickname().get() : userChangeNicknameEvent.getUser().getName();
            impersonationScam.removeElo(userChangeNicknameEvent.getUser().getId());
            userChangeNicknameEvent.getUser().resetNickname(userChangeNicknameEvent.getServer());
            userChangeNicknameEvent.getUser().sendMessage("You nickname on Nix crew server was reset. This nick belongs to some admin on Nix crew server. If you will again try to impersonate admins you might get ban");
            CommonUtils.securityManager.sendAnnouncement(impersonationScam, userChangeNicknameEvent.getUser());
        }

    }
}
