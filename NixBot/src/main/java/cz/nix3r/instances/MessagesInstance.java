package cz.nix3r.instances;

import java.util.List;

public class MessagesInstance {

    private List<String> WelcomeMessages;
    private List<String> LeaveMessages;
    private List<String> RolledDiceMessages;

    public MessagesInstance(List<String> welcomeMessages, List<String> leaveMessages, List<String> roledDiceMessages) {
        WelcomeMessages = welcomeMessages;
        LeaveMessages = leaveMessages;
        RolledDiceMessages = roledDiceMessages;
    }

    public List<String> getWelcomeMessages() {
        return WelcomeMessages;
    }

    public void setWelcomeMessages(List<String> welcomeMessages) {
        WelcomeMessages = welcomeMessages;
    }

    public List<String> getLeaveMessages() {
        return LeaveMessages;
    }

    public void setLeaveMessages(List<String> leaveMessages) {
        LeaveMessages = leaveMessages;
    }

    public List<String> getRolledDiceMessages() {
        return RolledDiceMessages;
    }

    public void setRolledDiceMessages(List<String> rolledDiceMessages) {
        RolledDiceMessages = rolledDiceMessages;
    }
}
