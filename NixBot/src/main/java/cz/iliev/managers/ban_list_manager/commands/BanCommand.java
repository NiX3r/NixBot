package cz.iliev.managers.ban_list_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.PunishmentInstance;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;

public class BanCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        interaction.getServer().ifPresent(server -> {

            if(!server.getIdAsString().equals(CommonUtils.NIX_CREW_ID)){
                CommonUtils.politeDisconnect(server);
                return;
            }

            User toBan = interaction.getArgumentUserValueByIndex(0).get();
            String reason = interaction.getArgumentStringValueByIndex(1).get();
            long duration = interaction.getArgumentLongValueByIndex(2).isPresent() ?
                    interaction.getArgumentLongValueByIndex(2).get() :
                    0;

            var toBanRoles = new ArrayList<String>();
            toBan.getRoles(server).forEach(role -> toBanRoles.add(role.getName()));

            var adminRoles = new ArrayList<String>();
            toBan.getRoles(server).forEach(role -> adminRoles.add(role.getName()));

            var ban = new PunishmentInstance(
                    BanType.BAN,
                    new MemberInstance(
                            toBan.getId(),
                            toBan.getName(),
                            toBanRoles
                    ),
                    new MemberInstance(
                            interaction.getUser().getId(),
                            interaction.getUser().getName(),
                            adminRoles
                    ),
                    System.currentTimeMillis(),
                    duration,
                    reason
            );

        });

    }
}
