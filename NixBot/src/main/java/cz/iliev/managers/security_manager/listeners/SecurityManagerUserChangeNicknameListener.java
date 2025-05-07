package cz.iliev.managers.security_manager.listeners;

import cz.iliev.managers.security_manager.scams.ImpersonationScam;
import org.javacord.api.event.user.UserChangeNicknameEvent;
import org.javacord.api.listener.user.UserChangeNicknameListener;

public class SecurityManagerUserChangeNicknameListener implements UserChangeNicknameListener {
    @Override
    public void onUserChangeNickname(UserChangeNicknameEvent userChangeNicknameEvent) {

        ImpersonationScam impersonationScam = new ImpersonationScam();
        impersonationScam.checkScam(userChangeNicknameEvent.getNewNickname(), userChangeNicknameEvent.getUser());

    }
}
