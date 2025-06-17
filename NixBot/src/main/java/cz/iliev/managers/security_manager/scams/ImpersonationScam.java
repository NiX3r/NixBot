package cz.iliev.managers.security_manager.scams;

import cz.iliev.managers.security_manager.interfaces.IScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.user.User;

public class ImpersonationScam implements IScam {

    @Override
    public boolean checkScam(Object data, User user) {

        if(!(data instanceof String))
            return false;

        String nickname = ((String)data).toLowerCase();

        for (User member : CommonUtils.getNixCrew().getMembers()) {
            if(CommonUtils.isUserAdmin(member)) {
                if(nickname.contains(member.getDisplayName(CommonUtils.getNixCrew()).toLowerCase()) ||
                            nickname.contains(member.getName().toLowerCase())){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void removeElo(long userId) {
        CommonUtils.securityManager.addElo(userId, punishElo());
    }

    @Override
    public int punishElo() {
        return -25;
    }

    @Override
    public String scamName() {
        return "Impersonation scam";
    }

    @Override
    public String scamDescription() {
        return "When you try impersonate any nickname of admins";
    }
}