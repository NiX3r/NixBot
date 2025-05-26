package cz.iliev.managers.security_manager.scams;

import cz.iliev.managers.security_manager.interfaces.IScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.user.User;

public class MarkdownLinkScam implements IScam {
    @Override
    public boolean checkScam(Object data, User user) {

        String message = (String) data;

        if(!(message.contains("[")) || !(message.contains("](")) || !(message.contains(")")))
            return false;

        String[] splitter = message.split("]\\(");

        for(int i = 0; i <= splitter.length; i += 2){
            String replacement = splitter[i].substring(splitter[i].indexOf("["));
            String realLink = splitter[i+1].substring(0, splitter[i+1].indexOf(")"));
            String realDomain = realLink.replace("https://", "")
                                    .replace("http://", "");
            if(realDomain.contains("/"))
                realDomain = realDomain.substring(0, realLink.indexOf("/"));

            if(!replacement.contains("."))
                continue;

            if(replacement.contains(realDomain))
                continue;

            if(replacement.contains("http://") ||
                replacement.contains("https://") ||
                replacement.contains("http:\\\\") ||
                replacement.contains("https:\\\\")){
                return true;
            }

            if(replacement.split("\\.").length >= 2)
                return true;

        }

        return false;
    }

    @Override
    public void removeElo(long userId) {
        CommonUtils.securityManager.addElo(userId, punishElo());
    }

    @Override
    public void punish(User user) {

    }

    @Override
    public int punishElo() {
        return -5;
    }

    @Override
    public String scamName() {
        return "Markdown link scam";
    }

    @Override
    public String scamDescription() {
        return "Scam where user tries to send link hidden as different link via Markdown";
    }
}
