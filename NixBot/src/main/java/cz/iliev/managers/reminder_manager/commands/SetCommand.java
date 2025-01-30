package cz.iliev.managers.reminder_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.entity.message.component.TextInputStyle;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Arrays;

public class SetCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        String name = interaction.getArgumentStringValueByIndex(0).get();
        var userReminders = CommonUtils.reminderManager.getUserReminders(interaction.getUser().getId());

        if(userReminders == null || userReminders.isEmpty()){
            add(interaction, name);
            return;
        }

        var reminder = CommonUtils.reminderManager.getUserReminderByName(interaction.getUser().getId(), name);
        if(reminder == null){
            add(interaction, name);
        }
        else {
            remove(interaction, name);
        }

    }

    private void add(SlashCommandInteraction interaction, String name){

        interaction.respondWithModal("nix-reminder-" + name, "Create a reminder", Arrays.asList(
                ActionRow.of(TextInput.create(TextInputStyle.SHORT, "reminder-in-cron", "Reminder Cron (<DAY> <MONTH>)", true)),
                ActionRow.of(TextInput.create(TextInputStyle.PARAGRAPH, "reminder-in-desc", "Reminder Description", true))
        ));

    }

    private void remove(SlashCommandInteraction interaction, String name){

        CommonUtils.reminderManager.removeUserReminderByName(interaction.getUser().getId(), name);
        interaction.createImmediateResponder().setContent("Reminder removed").setFlags(MessageFlag.EPHEMERAL).respond();

    }
}
