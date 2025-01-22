package cz.iliev.managers.reminder_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class ListCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        var reminders = CommonUtils.reminderManager.getUserReminders(interaction.getUser().getId());

        if (reminders == null || reminders.isEmpty()){
            interaction.createImmediateResponder().setContent("You have no reminders").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        String list = "";

        for (ReminderInstace reminder : reminders) {
            list += "\n• `" + reminder.getName() + "`, cron: `" + reminder.getCron() + "`, desc: *" + (reminder.getDescription().length() > 30 ? reminder.getDescription().substring(0, 29) + "..." : reminder.getDescription()) + "*";
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Your reminders")
                .setColor(Color.decode(CommonUtils.reminderManager.color()))
                .addField("Total", String.valueOf(reminders.size()))
                .setDescription(list.substring(1));

        interaction.createImmediateResponder().addEmbed(builder).setFlags(MessageFlag.EPHEMERAL).respond();

    }
}
