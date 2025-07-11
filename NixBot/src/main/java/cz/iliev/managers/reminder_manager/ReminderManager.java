package cz.iliev.managers.reminder_manager;

import cz.iliev.interfaces.IManager;
import cz.iliev.managers.reminder_manager.commands.ListAllCommand;
import cz.iliev.managers.reminder_manager.commands.ListCommand;
import cz.iliev.managers.reminder_manager.commands.SetCommand;
import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.managers.reminder_manager.listeners.ReminderModalSubmitListener;
import cz.iliev.managers.reminder_manager.threads.ReminderThread;
import cz.iliev.managers.reminder_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.List;

public class ReminderManager implements IManager {

    private boolean ready;

    private List<ReminderInstace> reminders;

    public ReminderManager(){ setup(); }

    @Override
    public void setup() {
        LogUtils.info("Load and start " + managerName());
        reminders = FileUtils.loadSettings();
        CommonUtils.bot.addModalSubmitListener(new ReminderModalSubmitListener());
        ready = true;
        LogUtils.info(managerName() + " loaded and started. Ready to use");
    }

    @Override
    public void kill() {
        LogUtils.info("Kill " + managerName());
        FileUtils.saveSettings(reminders);
        ready = false;
        LogUtils.info(managerName() + " killed");
    }

    @Override
    public boolean restart() {
        kill();
        setup();
        return ready;
    }

    @Override
    public void onCommand(SlashCommandInteraction interaction) {
        switch (interaction.getOptions().get(0).getName()){
            case "set":
                new SetCommand().run(interaction);
                break;
            case "list":
                new ListCommand().run(interaction);
                break;
            case "listall":
                if(!CommonUtils.isUserAdmin(interaction.getUser())){
                    interaction.createImmediateResponder().setContent("This command is for administrators only").setFlags(MessageFlag.EPHEMERAL).respond();
                    break;
                }
                new ListAllCommand().run(interaction);
                break;
        }
    }

    @Override
    public void onConsoleCommand(Object data) {

    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public String managerName() {
        return "ReminderManager";
    }

    @Override
    public String managerDescription() {
        return "Manager for reminding special recurring events";
    }

    @Override
    public String color() {
        return "#e8e537";
    }

    public List<ReminderInstace> getReminders(){
        return reminders;
    }

    public List<ReminderInstace> getUserReminders(long userId){
        List<ReminderInstace> output = new ArrayList<ReminderInstace>();
        for (ReminderInstace reminder : reminders) {
            if (reminder.getAuthorId() == userId)
                output.add(reminder);
        }
        return output;
    }

    public ReminderInstace getUserReminderByName(long userId, String name){
        for (ReminderInstace reminder : reminders) {
            if (reminder.getAuthorId() == userId && reminder.getName().equals(name))
                return reminder;
        }
        return null;
    }

    public void removeUserReminderByName(long userId, String name){
        reminders.removeIf(reminder -> reminder.getAuthorId() == userId && reminder.getName().equals(name));
    }

    public void addUserReminder(ReminderInstace reminder){
        reminders.add(reminder);
    }

    public void checkReminders(){
        Thread t = new Thread(new ReminderThread());
        t.start();
    }
}
