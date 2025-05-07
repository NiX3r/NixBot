package cz.iliev.managers.security_manager.interfaces;

import org.javacord.api.entity.user.User;

public interface IScam {
    
    public boolean checkScam(Object data, User user);

    public void removeElo(long userId);

    public void punish(User user);

    public int punishElo();
    public String scamName();
    public String scamDescription();

}
