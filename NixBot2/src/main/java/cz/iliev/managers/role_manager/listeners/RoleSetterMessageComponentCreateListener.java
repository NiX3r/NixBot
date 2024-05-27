package cz.iliev.managers.role_manager.listeners;

import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class RoleSetterMessageComponentCreateListener implements MessageComponentCreateListener {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();
        messageComponentInteraction.getServer().ifPresent(server -> {
            messageComponentInteraction.getChannel().ifPresent(textChannel -> {
                if(customId.contains("nix-role-")){
                    String roleId = customId.replace("nix-role-", "");
                    RoleSetterInstance setter = CommonUtils.roleManager.getRoleSetterByRoleId(roleId);
                    if(setter == null){
                        messageComponentInteraction.createImmediateResponder().setContent("Unfortunately this role does not exists. Please contact support").setFlags(MessageFlag.EPHEMERAL).respond();
                        return;
                    }
                    roleSetter(messageComponentCreateEvent.getInteraction(), setter);
                }
            });
        });
    }

    private void roleSetter(Interaction interaction, RoleSetterInstance setter){
        interaction.getServer().ifPresent(server -> {
            server.getRoleById(setter.getRoleId()).ifPresent(role -> {
                boolean done = false;
                for(Role item : interaction.getUser().getRoles(server)){
                    if(item.getId() == role.getId()){
                        interaction.getUser().removeRole(item);
                        interaction.createImmediateResponder().setContent("Role " + item.getMentionTag() + " has been took").setFlags(MessageFlag.EPHEMERAL).respond();
                        LogUtils.info("Role '" + item.getName() + "' has been took from '" + interaction.getUser().getName() + "'");
                        done = true;
                    }
                }
                if(!done){
                    interaction.getUser().addRole(role);
                    interaction.createImmediateResponder().setContent("Role " + role.getMentionTag() + " has been added").setFlags(MessageFlag.EPHEMERAL).respond();
                    LogUtils.info("Role '" + role.getName() + "' has been added to '" + interaction.getUser().getName() + "'");
                }
            });
        });
    }
}
