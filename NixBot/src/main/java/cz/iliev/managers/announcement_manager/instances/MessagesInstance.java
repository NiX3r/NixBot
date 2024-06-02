package cz.iliev.managers.announcement_manager.instances;

import java.util.List;

public class MessagesInstance {

    private List<String> welcomeMessages;
    private List<String> leaveMessages;

    public MessagesInstance(List<String> welcomeMessages, List<String> leaveMessages) {
        this.welcomeMessages = welcomeMessages;
        this.leaveMessages = leaveMessages;
    }

    public List<String> getWelcomeMessages() {
        return welcomeMessages;
    }

    public void setWelcomeMessages(List<String> welcomeMessages) {
        this.welcomeMessages = welcomeMessages;
    }

    public List<String> getLeaveMessages() {
        return leaveMessages;
    }

    public void setLeaveMessages(List<String> leaveMessages) {
        this.leaveMessages = leaveMessages;
    }
}
