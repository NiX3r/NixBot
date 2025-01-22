package cz.iliev.managers.reminder_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.HashMap;

public class ListAllCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        var reminders = CommonUtils.reminderManager.getReminders();

        if (reminders == null || reminders.isEmpty()){
            interaction.createImmediateResponder().setContent("There are no reminders").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        String list = "";
        var members = new HashMap<Long, String>();

        for (ReminderInstace reminder : reminders) {
            if(!members.containsKey(reminder.getAuthorId())){
                if(interaction.getServer().get().getMemberById(reminder.getAuthorId()).isPresent())
                    members.put(reminder.getAuthorId(), interaction.getServer().get().getMemberById(reminder.getAuthorId()).get().getMentionTag());
                else
                    members.put(reminder.getAuthorId(), "`NaN`");
            }
            list += "\n• `" + reminder.getName() + "`, " + members.get(reminder.getAuthorId()) + ", cron: `" + reminder.getCron() + "`, desc: *" + (reminder.getDescription().length() > 30 ? reminder.getDescription().substring(0, 29) + "..." : reminder.getDescription()) + "*";
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Nix Crew reminders")
                .setColor(Color.decode(CommonUtils.reminderManager.color()))
                .addField("Total reminders", String.valueOf(reminders.size()), true)
                .addField("Total members", String.valueOf(members.size()), true)
                .setDescription(list.substring(1));

        interaction.createImmediateResponder().addEmbed(builder).setFlags(MessageFlag.EPHEMERAL).respond();

    }
}
