package cz.iliev.managers.ban_list_manager.commands;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.interfaces.ISlashCommand;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.PunishmentInstance;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.managers.ban_list_manager.utils.FileUtils;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.Arrays;

public class BanCommand implements ISlashCommand {
    @Override
    public void run(SlashCommandInteraction interaction) {

        User toBan = interaction.getArgumentUserValueByIndex(0).get();
        String reason = interaction.getArgumentStringValueByIndex(1).get();
        long duration = interaction.getArgumentLongValueByIndex(2).isPresent() ? interaction.getArgumentLongValueByIndex(2).get() : 0;
        var response = CommonUtils.banListManager.addCachePunishment(toBan, interaction.getUser(), reason, duration, BanType.BAN);

        if(response){
            interaction.createImmediateResponder()
                    .addComponents(
                            ActionRow.of(
                                    Button.success("nix-ban-undo-" + toBan.getId(), "Undo ban", EmojiParser.parseToUnicode(":x:")),
                                    Button.danger("nix-ban-confirm-" + toBan.getId(), "Confirm", EmojiParser.parseToUnicode(":white_check_mark:"))
                            )
                    )
                    .setContent("Are you sure you want to ban `" + toBan.getName() + "`")
                    .setFlags(MessageFlag.EPHEMERAL).respond();
        }
        else {
            interaction.createImmediateResponder().setContent("There is already modal for this user. Please firstly the old one").setFlags(MessageFlag.EPHEMERAL).respond();
        }

    }
}
