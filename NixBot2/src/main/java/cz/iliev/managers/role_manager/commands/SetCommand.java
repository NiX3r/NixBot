package cz.iliev.managers.role_manager.commands;

import com.google.gson.Gson;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.announcement_manager.utils.AnnouncementManagerUtils;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.managers.role_manager.utils.RoleManagerUtils;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SetCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        long roleId = interaction.getArgumentRoleValueByIndex(0).get().getId();
        boolean adding = interaction.getArgumentBooleanValueByIndex(1).get();
        String emoji = interaction.getArgumentStringValueByIndex(2).get();
        String componentLabel = interaction.getArgumentStringValueByIndex(3).get();

        RoleSetterInstance setter = CommonUtils.roleManager.getRoleSetterByRoleId(roleId);

        if(setter == null){
            setter = new RoleSetterInstance(roleId, adding, emoji, componentLabel);
            CommonUtils.roleManager.getRoleSetter().add(setter);
            RoleManagerUtils.sendComponentMessage(interaction);
        }
        else{
            CommonUtils.roleManager.getRoleSetter().remove(setter);
            interaction.createImmediateResponder().setContent("Role removed from message").setFlags(MessageFlag.EPHEMERAL).respond();
            RoleManagerUtils.sendComponentMessage(interaction);
        }
    }
}
