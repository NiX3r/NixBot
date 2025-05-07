package cz.iliev.managers.security_manager.utils;

import cz.iliev.managers.security_manager.interfaces.IScam;
import cz.iliev.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.time.Instant;

public class SecurityManagerUtils {

    public static EmbedBuilder createPunishLogEmbed(IScam scam, User user, int currentElo){
        return new EmbedBuilder()
                .setTitle("Punishment " + scam.scamName())
                .setDescription(scam.scamDescription())
                .setColor(Color.decode(CommonUtils.securityManager.color()))
                .addField("User nick", user.getName())
                .addField("User ping", user.getMentionTag())
                .addField("ELO remove", String.valueOf(scam.punishElo()))
                .addField("Current ELO", String.valueOf(currentElo))
                .setThumbnail(user.getAvatar())
                .setTimestamp(Instant.now());
    }

}
