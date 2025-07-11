package cz.iliev.managers.security_manager.scams;

import cz.iliev.managers.security_manager.interfaces.IScam;
import org.javacord.api.entity.user.User;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PhishingScam implements IScam {

    private final String SCAM_DATABASE_URL = "https://raw.githubusercontent.com/Phishing-Database/Phishing.Database/refs/heads/master/phishing-IPs-ACTIVE.txt";

    @Override
    public boolean checkScam(Object data, User user) {

        if(!(data instanceof String))
            return false;

        String content = (String)data;
        if(!content.contains("www."))
            return false;

        String web = content.substring(content.indexOf("www."));
        String ip = null;
        if(web.contains(" "))
            web = web.substring(0, web.indexOf(" "));
        try {
            InetAddress giriAddress = InetAddress.getByName(web);
            ip = giriAddress.getHostAddress();
        } catch (UnknownHostException e) { return false; }

        

        return false;
    }

    @Override
    public void removeElo(long userId) {

    }

    @Override
    public int punishElo() {
        return -50;
    }

    @Override
    public String scamName() {
        return "";
    }

    @Override
    public String scamDescription() {
        return "";
    }
}
