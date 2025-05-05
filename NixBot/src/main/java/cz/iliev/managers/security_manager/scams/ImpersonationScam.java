package cz.iliev.managers.security_manager.scams;

import cz.iliev.managers.security_manager.interfaces.IScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.user.User;

public class ImpersonationScam implements IScam {
    @Override
    public boolean IsScam(Object data) {

        if(!(data instanceof String))
            return false;

        String nickname = ((String)data).toLowerCase();

        for (User member : CommonUtils.getNixCrew().getMembers()) {
            if(CommonUtils.isUserAdmin(member)){
                if(nickname.contains(member.getDisplayName(CommonUtils.getNixCrew())) ||
                            nickname.contains(member.getName()))
                    return true;
            }
        }

        return false;
    }
}
