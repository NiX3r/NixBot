package cz.iliev.managers.reminder_manager.listeners;

import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.managers.reminder_manager.utils.CronUtils;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.event.interaction.ModalSubmitEvent;
import org.javacord.api.listener.interaction.ModalSubmitListener;

public class ReminderModalSubmitListener implements ModalSubmitListener {
    @Override
    public void onModalSubmit(ModalSubmitEvent modalSubmitEvent) {

        var interaction = modalSubmitEvent.getModalInteraction();

        if(!interaction.getCustomId().startsWith("nix-reminder-"))
            return;

        var cron = interaction.getComponents().get(0).asActionRow().get().getComponents().get(0).asTextInput().get();
        var description = interaction.getComponents().get(1).asActionRow().get().getComponents().get(0).asTextInput().get();
        String name = interaction.getCustomId().replace("nix-reminder-", "");
        String cronValue = cron.getValue();
        String descriptionValue = description.getValue();

        if(!CronUtils.checkFullCronFormat(cronValue)){
            interaction.createImmediateResponder().setContent("Špatný formát cron. Formát musí obsahovat den a měsíc odděleny mezerou. Můžete zapsat tři typy dní/měsíců a to:\n- Číslo dne/měsíce;\n- Všechno děleno číslem (*/2 - každý druhý den/měsíc);\n- Každý den/měsíc znakem `*`.\nPříklad správného cron formátu `*/2 *` což je každý druhý den, každého měsíce.").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        ReminderInstace reminder = new ReminderInstace(
                interaction.getUser().getId(),
                null,
                false,
                cronValue,
                name,
                descriptionValue
        );

        CommonUtils.reminderManager.addUserReminder(reminder);
        interaction.createImmediateResponder().setContent("Reminder `" + name + "` vytvořen").setFlags(MessageFlag.EPHEMERAL).respond();

    }
}
