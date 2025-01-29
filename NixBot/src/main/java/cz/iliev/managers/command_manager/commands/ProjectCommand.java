package cz.iliev.managers.command_manager.commands;

import cz.iliev.interfaces.ISlashCommand;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.interaction.SlashCommandInteraction;

public class ProjectCommand implements ISlashCommand {

    private static final String CHANNEL_NAME_TEMPLATE = "%PROJECT_SHORTCUT%․%CHANNEL_TYPE%_≪%EMOJI%≫";

    @Override
    public void run(SlashCommandInteraction interaction) {

        String name = interaction.getArgumentStringValueByIndex(0).get();
        String shortcut = interaction.getArgumentStringValueByIndex(1).get();
        String emoji = interaction.getArgumentStringValueByIndex(2).get();

        interaction.getServer().ifPresent(server -> {

            server.createChannelCategoryBuilder()
                    .setName(name)
                    .addPermissionOverwrite(server.getEveryoneRole(), Permissions.fromBitmask(0, 1024))
                    .setAuditLogReason("Created by NixBot via /project command")
                    .create().thenAccept(category -> {

                        server.createTextChannelBuilder()
                                .setCategory(category)
                                .setName(CHANNEL_NAME_TEMPLATE.replace("%PROJECT_SHORTCUT%", shortcut)
                                                              .replace("%CHANNEL_TYPE%", "log")
                                                              .replace("%EMOJI%", emoji))
                                .setAuditLogReason("Created by NixBot via /project command")
                                .create();

                        server.createTextChannelBuilder()
                                .setCategory(category)
                                .setName(CHANNEL_NAME_TEMPLATE.replace("%PROJECT_SHORTCUT%", shortcut)
                                        .replace("%CHANNEL_TYPE%", "main")
                                        .replace("%EMOJI%", emoji))
                                .setAuditLogReason("Created by NixBot via /project command")
                                .create();

                        server.createTextChannelBuilder()
                                .setCategory(category)
                                .setName(CHANNEL_NAME_TEMPLATE.replace("%PROJECT_SHORTCUT%", shortcut)
                                        .replace("%CHANNEL_TYPE%", "todo")
                                        .replace("%EMOJI%", emoji))
                                .setAuditLogReason("Created by NixBot via /project command")
                                .create();

                    });

        });

    }
}
