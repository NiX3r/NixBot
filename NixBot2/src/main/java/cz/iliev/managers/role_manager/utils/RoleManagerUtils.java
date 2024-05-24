package cz.iliev.managers.role_manager.utils;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.managers.role_manager.RoleManager;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.MessageUpdater;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.SlashCommandInteraction;

public class RoleManagerUtils {
    public static void sendComponentMessage(SlashCommandInteraction interaction){
        CommonUtils.announcementManager.sendRoleSetter(CommonUtils.roleManager.getRoleSetter());
        interaction.createImmediateResponder().setContent("Message sent").setFlags(MessageFlag.EPHEMERAL).respond();
    }
}
