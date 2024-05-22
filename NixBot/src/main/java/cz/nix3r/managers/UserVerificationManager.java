package cz.nix3r.managers;

import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.DiscordUtils;
import cz.nix3r.utils.FileUtils;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class UserVerificationManager {

    // Key = user ID | Value = code
    private HashMap<Long, String> usersCodes;

    public UserVerificationManager(){
        usersCodes = FileUtils.loadUsersVerificationCodes();
        if(usersCodes == null){
            usersCodes = new HashMap<Long, String>();
        }
    }

    public void sendCode(User user){

        String code = addUser(user.getId());
        if(code == null)
            return;

        int width = 600, height = 300;
        Color backgroundColor = Color.decode("#2c2c2c");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 80));
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
        user.sendMessage(embed);
    }

    private String addUser(long userId){
        if(usersCodes.containsKey(userId))
            return null;
        Random rand = new Random();
        int r = rand.nextInt(100000,1000000);
        String code = Integer.toHexString(r);
        usersCodes.put(userId, code);
        return code;
    }

    public HashMap<Long, String> getUsersCodes() {
        return usersCodes;
    }
}
