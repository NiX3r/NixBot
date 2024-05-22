package cz.iliev.managers.announcement_manager.instances;

import java.util.List;

public class MessagesInstance {

    private List<String> welcomeMessages;
    private List<String> leaveMessages;
    private List<String> rolledDiceMessages;

    public MessagesInstance(List<String> welcomeMessages, List<String> leaveMessages, List<String> rolledDiceMessages) {
        this.welcomeMessages = welcomeMessages;
        this.leaveMessages = leaveMessages;
        this.rolledDiceMessages = rolledDiceMessages;
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

    public List<String> getRolledDiceMessages() {
        return rolledDiceMessages;
    }

    public void setRolledDiceMessages(List<String> rolledDiceMessages) {
        this.rolledDiceMessages = rolledDiceMessages;
    }
}
