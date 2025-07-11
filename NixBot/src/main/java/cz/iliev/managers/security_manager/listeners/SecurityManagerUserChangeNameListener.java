package cz.iliev.managers.security_manager.listeners;

import cz.iliev.managers.security_manager.scams.ImpersonationScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.user.UserChangeNameEvent;
import org.javacord.api.listener.user.UserChangeNameListener;

import java.awt.*;

public class SecurityManagerUserChangeNameListener implements UserChangeNameListener {
    @Override
    public void onUserChangeName(UserChangeNameEvent userChangeNameEvent) {


        // TODO : not working on Javacord - need to update on JDA
        ImpersonationScam impersonationScam = new ImpersonationScam();
        if(impersonationScam.checkScam(userChangeNameEvent.getNewName(), userChangeNameEvent.getUser())){
            impersonationScam.removeElo(userChangeNameEvent.getUser().getId());
            CommonUtils.getNixCrew().updateNickname(userChangeNameEvent.getUser(), "ITriedImpersonate");
            userChangeNameEvent.getUser().sendMessage("Please change back your nickname to '" + userChangeNameEvent.getOldName() + "'. You now known as `ITriedImpersonate`. This nick belongs to some admin on Nix crew server. If you won't change it back you may be ban from the server.");
            CommonUtils.securityManager.sendAnnouncement(impersonationScam, userChangeNameEvent.getUser());
        }

    }
}
