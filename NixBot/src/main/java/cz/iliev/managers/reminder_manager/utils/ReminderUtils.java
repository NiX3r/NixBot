package cz.iliev.managers.reminder_manager.utils;

import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class ReminderUtils {

    public static EmbedBuilder createEmbed(String name, String description){
        return new EmbedBuilder()
                .setTitle("Reminder: " + name)
                .setColor(Color.decode(CommonUtils.reminderManager.color()))
                .setDescription(description)
                .setFooter("Version: " + CommonUtils.VERSION);
    }

}
