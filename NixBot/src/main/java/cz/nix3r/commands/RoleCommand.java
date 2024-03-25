package cz.nix3r.commands;

import com.google.gson.Gson;
import com.vdurmont.emoji.EmojiParser;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.RoleSetterInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.MessageUpdater;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class RoleCommand {

    public static void run(SlashCommandInteraction interaction){
        switch (interaction.getOptions().get(0).getName()){
            case "message":
                message(interaction);
                break;
            case "set":
                set(interaction);
                break;
        }
    }

    private static void set(SlashCommandInteraction interaction) {

        long roleId = interaction.getArgumentRoleValueByIndex(0).get().getId();
        boolean adding = interaction.getArgumentBooleanValueByIndex(1).get();
        String emoji = interaction.getArgumentStringValueByIndex(2).get();
        String componentLabel = interaction.getArgumentStringValueByIndex(3).get();

        RoleSetterInstance setter = CommonUtils.getRoleSetterByRoleId(roleId);

        if(setter == null){
            setter = new RoleSetterInstance(roleId, adding, emoji, componentLabel);
            CommonUtils.roleSetter.add(setter);
            sendComponentMessage(interaction);
        }
        else{
            CommonUtils.roleSetter.remove(setter);
            interaction.createImmediateResponder().setContent("Role removed from message").setFlags(MessageFlag.EPHEMERAL).respond();
            sendComponentMessage(interaction);
        }
        LogSystem.log(LogType.INFO, "End of the command role set by '" + interaction.getUser().getName() + "'. Data: " + new Gson().toJson(setter));

    }

    private static void message(SlashCommandInteraction interaction) {
        sendComponentMessage(interaction);
        LogSystem.log(LogType.INFO, "End of the command role message by '" + interaction.getUser().getName() + "'");
    }

    private static void sendComponentMessage(SlashCommandInteraction interaction){
        CommonUtils.bot.getServers().forEach(server -> {
            server.getTextChannelById(CommonUtils.ROLES_CHANNEL_ID).ifPresent(textChannel -> {
                Message msg = null;
                try{
                    msg = textChannel.getMessageById(CommonUtils.settings.getRolesMessageId()).get();
                }
                catch (Exception ex){
                    LogSystem.log(LogType.WARNING, "Role message does not exists. Creating a new one");
                }

                if(msg == null){

                    MessageBuilder builder = new MessageBuilder()
                            .setEmbed(DiscordUtils.createRoleEmbed());

                    CommonUtils.roleSetter.forEach(setter -> {
                        builder.addComponents(
                                ActionRow.of(Button.secondary("nix-role-" + setter.getRoleId(), setter.getComponentLabel(), EmojiParser.parseToUnicode(setter.getEmoji())))
                        );
                    });

                    msg = builder.send(textChannel).join();
                    CommonUtils.settings.setRolesMessageId(msg.getId());
                    LogSystem.log(LogType.INFO, "Role message crated and sent. Message ID saved");
                    interaction.createImmediateResponder().setContent("Message sent").setFlags(MessageFlag.EPHEMERAL).respond();

                }
                else{
                    MessageUpdater updater = msg.createUpdater();
                    updater.removeAllComponents().removeAllEmbeds()
                            .addEmbed(DiscordUtils.createRoleEmbed());

                    CommonUtils.roleSetter.forEach(setter -> {
                        updater.addComponents(
                                ActionRow.of(Button.secondary("nix-role-" + setter.getRoleId(), setter.getComponentLabel(), EmojiParser.parseToUnicode(setter.getEmoji())))
                        );
                    });

                    updater.applyChanges().join();
                    LogSystem.log(LogType.INFO, "Role message updated");
                    interaction.createImmediateResponder().setContent("Message sent").setFlags(MessageFlag.EPHEMERAL).respond();


                }
            });
        });
    }

}
