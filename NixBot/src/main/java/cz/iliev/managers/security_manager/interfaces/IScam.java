package cz.iliev.managers.security_manager.interfaces;

import org.javacord.api.entity.user.User;

import java.util.HashMap;

public interface IScam {
    
    public boolean checkScam(Object data, User user);

    public void removeElo(long userId);

    public int punishElo();
    public String scamName();
    public String scamDescription();

}
