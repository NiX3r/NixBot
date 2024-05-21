package cz.nix3r.listeners;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.InviteInstance;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.FileUtils;
import cz.nix3r.utils.LogSystem;
import org.apache.commons.logging.Log;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class nServerMemberJoinListener implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent serverMemberJoinEvent) {

        if(CommonUtils.verificationManager.getUsersCodes().containsKey(serverMemberJoinEvent.getUser().getId())){
            return;
        }

        String code = CommonUtils.verificationManager.addUser(serverMemberJoinEvent.getUser().getId());

        int width = 600, height = 300;
        Color backgroundColor = Color.decode("#2c2c2c");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 40));
        int textWidth = graphics.getFontMetrics().stringWidth(code);
        int textHeight = graphics.getFontMetrics().getHeight();
        int textX = (width - textWidth) / 2;
        int textY = (height - textHeight) / 2 + textHeight;

        graphics.drawString(code, textX, textY);

        Random random = new Random();
        graphics.setStroke(new java.awt.BasicStroke(3));
        for (int i = 0; i < 100; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            Color lineColor = Color.WHITE;
            graphics.setColor(lineColor);
            graphics.drawLine(x1, y1, x2, y2);
        }

        graphics.dispose();

        var embed = DiscordUtils.createVerificationEmbed();
        embed.setImage(image);
        serverMemberJoinEvent.getUser().sendMessage(embed);
        LogSystem.info("Member " + serverMemberJoinEvent.getUser().getName() + " joined on the server. Bot activity updated. Event end");

    }

}
