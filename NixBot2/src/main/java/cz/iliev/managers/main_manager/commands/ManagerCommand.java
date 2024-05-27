package cz.iliev.managers.main_manager.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.IManager;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Arrays;

public class ManagerCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        String managerName = interaction.getOptionByIndex(0).get().getStringValue().get();
        IManager manager = CommonUtils.mainManager.getManagers().get(managerName);

        if(manager == null){
            String managerNames = "";
            for(var name : CommonUtils.mainManager.getManagers().keySet()){
                managerNames += "`" + name + "`, ";
            }
            managerNames = managerNames.substring(0, managerNames.length() - 2);
            interaction.createImmediateResponder().setContent("## You typed wrong manager name.\nAvailable managers names:\n" + managerNames).setFlags(MessageFlag.EPHEMERAL).respond();
            return;
        }

        var message = createEmbed(manager, managerName, CommonUtils.isUserAdmin(interaction.getUser()));
        interaction.createImmediateResponder().setContent("Generating ..").setFlags(MessageFlag.EPHEMERAL).respond();

        if(interaction.getChannel().isPresent()){
            message.send(interaction.getChannel().get()).join();
        }
        else
            message.send(interaction.getUser()).join();

    }

    private MessageBuilder createEmbed(IManager manager, String managerShortcut, boolean isAdmin){

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Manager : " + manager.managerName())
                .addField("Ready", String.valueOf(manager.isReady()))
                .setDescription(manager.managerDescription())
                .setColor(Color.decode(manager.color()))
                .setFooter("Version: " + CommonUtils.VERSION);

        MessageBuilder builder = new MessageBuilder()
                .setEmbed(embed);

        if (isAdmin)
            builder.addComponents(
                    ActionRow.of(Button.success("nix-manager-start-" + managerShortcut, "Start", EmojiParser.parseToUnicode(":arrow_forward:")),
                            Button.danger("nix-manager-stop-" + managerShortcut, "Stop", EmojiParser.parseToUnicode(":double_vertical_bar:")),
                            Button.primary("nix-manager-restart-" + managerShortcut, "Restart", EmojiParser.parseToUnicode(":repeat:")))
            );

        return builder;

    }
}
