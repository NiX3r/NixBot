package cz.iliev.managers.main_manager.listeners;

import cz.iliev.interfaces.IManager;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

public class MainManagerMessageComponentCreateListener implements MessageComponentCreateListener {
    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();

        if(!customId.contains("nix-manager-")){
            return;
        }

        if(!CommonUtils.isUserAdmin(messageComponentInteraction.getUser())){
            messageComponentInteraction.createImmediateResponder().setContent("Only admins can administrate managers").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        if(!CommonUtils.mainManager.getManagers().containsKey(customId.split("-")[3])){
            messageComponentInteraction.createImmediateResponder().setContent("Clicked manager does not exists").setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        messageComponentInteraction.getServer().ifPresent(server -> {
            messageComponentInteraction.getChannel().ifPresent(textChannel -> {
                IManager manager = CommonUtils.mainManager.getManagers().get(customId.split("-")[3]);
                switch (customId.split("-")[2]){
                    case "start":
                        if(manager.isReady()){
                            messageComponentInteraction.createImmediateResponder().setContent("You can' start already started manager").setFlags(MessageFlag.EPHEMERAL).respond();
                            return;
                        }
                        manager.setup();
                        messageComponentInteraction.createImmediateResponder().setContent("Manager successfully started").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                    case "stop":
                        if(!manager.isReady()){
                            messageComponentInteraction.createImmediateResponder().setContent("You can' stop already stopped manager").setFlags(MessageFlag.EPHEMERAL).respond();
                            return;
                        }
                        manager.kill();
                        messageComponentInteraction.createImmediateResponder().setContent("Manager successfully stopped").setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                    case "restart":
                        var ready = manager.restart();
                        messageComponentInteraction.createImmediateResponder().setContent("Manager successfully restarted. Manager Readiness = " + ready).setFlags(MessageFlag.EPHEMERAL).respond();
                        break;
                }
            });
        });
    }
}
