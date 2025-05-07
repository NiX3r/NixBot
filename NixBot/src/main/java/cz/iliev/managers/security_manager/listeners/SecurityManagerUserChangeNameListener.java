package cz.iliev.managers.security_manager.listeners;

import cz.iliev.managers.security_manager.scams.ImpersonationScam;
import org.javacord.api.event.user.UserChangeNameEvent;
import org.javacord.api.listener.user.UserChangeNameListener;

public class SecurityManagerUserChangeNameListener implements UserChangeNameListener {
    @Override
    public void onUserChangeName(UserChangeNameEvent userChangeNameEvent) {

        ImpersonationScam impersonationScam = new ImpersonationScam();
        impersonationScam.checkScam(userChangeNameEvent.getNewName(), userChangeNameEvent.getUser());

    }
}
