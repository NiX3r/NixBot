package cz.iliev.managers.role_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.role_manager.utils.RoleManagerUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class MessageCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        RoleManagerUtils.sendComponentMessage(interaction);
        interaction.createImmediateResponder().setContent("Message sent").setFlags(MessageFlag.EPHEMERAL).respond();
    }
}
