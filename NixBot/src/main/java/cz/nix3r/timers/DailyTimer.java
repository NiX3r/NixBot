package cz.nix3r.timers;

import cz.nix3r.managers.StreamingPlatformReminderManager;
import cz.nix3r.utils.CommonUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

public class DailyTimer extends TimerTask {
    @Override
    public void run() {


        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        // Send game idea message to server channel
        if(now.get(Calendar.DAY_OF_MONTH) == 9){
            sendStreamingPlatformReminderEmbed();
        }

    }

    private void sendStreamingPlatformReminderEmbed(){

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Služby pro měsíc " + (Calendar.getInstance().get(Calendar.MONTH) + 1))
                .setDescription("Netflix a Disney se platí na účet Danielovi. Číslo účtu: `2001130367/2010`")
                .setColor(Color.decode("#7900FF"));

        HashMap<String, String> platforms = CommonUtils.platformManager.getPlatformPayersHashMap();
        for(String key : platforms.keySet()) builder.addField(key, platforms.get(key));

        for(String receiverId : CommonUtils.platformManager.RECEIVER_DISCORD_ID_ARRAY){
            ((Server)CommonUtils.bot.getServers().toArray()[0]).getMemberById(receiverId).ifPresent(user -> {
                user.sendMessage(builder).join();
            });
        }

        CommonUtils.platformManager.incrementIndexes();
        CommonUtils.platformManager.saveCachePlatformsInFile();

    }

}
