package cz.iliev.managers.announcement_manager.utils;

import cz.iliev.managers.announcement_manager.instances.MessagesInstance;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.Random;

public class AnnouncementManagerUtils {

    private static MessagesInstance messages;

    public static void setMessages(MessagesInstance messagesInstance){
        messages = messagesInstance;
    }

    public static EmbedBuilder createWelcomeEmbed(String username, Icon userAvatar, String inviterMentionTag, int serverCount){
        Random r = new Random();
        return new EmbedBuilder()
                .setTitle(username)
                .setDescription(messages.getWelcomeMessages().get(r.nextInt(messages.getWelcomeMessages().size())))
                .setColor(Color.decode("#00d60e"))
                .addField("Thanks for invite", inviterMentionTag, true)
                .addField("Member count", serverCount + " total", true)
                .setFooter("Version: " + CommonUtils.version)
                .setThumbnail(userAvatar);
    }

}
