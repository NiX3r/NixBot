package cz.iliev.managers.ban_list_manager.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.Arrays;

public class KickCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {
        User toBan = interaction.getArgumentUserValueByIndex(0).get();
        String reason = interaction.getArgumentStringValueByIndex(1).get();
        var response = CommonUtils.banListManager.addCachePunishment(toBan, interaction.getUser(), reason, 0, BanType.KICK);

        if(response){
            interaction.createImmediateResponder()
                    .addComponents(
                        ActionRow.of(
                                Button.success("nix-ban-undo-" + toBan.getId(), "Undo"),
                                Button.danger("nix-ban-confirm-" + toBan.getId(), "Confirm")
                        ))
                    .setContent("## Are you sure you want to kick `" + toBan.getName() + "`")
                    .setFlags(MessageFlag.EPHEMERAL).respond();
        }
        else {
            interaction.createImmediateResponder().setContent("There is already modal for this user. Please firstly finish the old one").setFlags(MessageFlag.EPHEMERAL).respond();
        }
    }
}
