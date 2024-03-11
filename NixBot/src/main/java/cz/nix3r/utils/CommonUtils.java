package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.InviteInstance;
import cz.nix3r.listeners.*;
import cz.nix3r.managers.InviteManager;
import cz.nix3r.managers.MusicManager;
import cz.nix3r.managers.TemporaryChannelManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.RegularServerChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class CommonUtils {

    public static DiscordApi bot;
    public static String version;
    public static long time_since_start;

    public static TemporaryChannelManager tempChannelManager;
    public static InviteManager inviteManager;
    public static MusicManager musicManager;
    public static Timer dailyTimer;

    public static final String WELCOME_CHANNEL_ID = "611985124057284621";
    public static final String NIXBOT_CHANNEL_ID = "1058017127988211822";
    public static final String CMD_CHANNEL_ID = "1118284494198288445";
    public static final String CREATE_CHANNEL_CHANNEL_ID = "1118311195867369513";
    public static final String CREATE_CHANNEL_CATEGORY_ID = "1118291032065441882";
    public static final String UNKNOWN_CHANNEL_ID = "1119262818101903410";
    public static final List<String> DEFAULT_ROLES_ID = new ArrayList<String>() {{add("1058009225491656724");}};

    public static final String[] WELCOME_MESSAGES = {
            "Welcome! Prepare to be amazed!",
            "Hello and welcome! Abandon all hope, ye who enter here!",
            "Welcome to our community! Don't worry, we don't bite... hard!",
            "We're glad to have you here! Our jokes are terrible, but we try!",
            "Welcome aboard! Fasten your seatbelts and get ready for a wild ride!",
            "Welcome, new friend! We've been expecting you, and the secret handshake is optional.",
            "Greetings and welcome! Our sarcasm levels are off the charts, so buckle up!",
            "A warm welcome to you! Just a heads up, puns are our currency here.",
            "Welcome to the team! We work hard, but our coffee breaks are legendary.",
            "Welcome, and enjoy your stay! Remember, laughter is the best medicine!"
    };

    public static final String[] LEAVE_MESSAGES = {
            "Sad to see you go! Who will bring the snacks now?",
            "Leaving so soon? Don't forget to take the office plant with you!",
            "Goodbye, quitter! We never really liked you anyway... just kidding!",
            "Off to new adventures? Take us with you in your suitcase, please!",
            "Farewell, dear friend! Don't forget to send postcards from your secret hideout!",
            "You're leaving? Time to change the password to the secret clubhouse!",
            "Goodbye, comrade! May your memes always be dank and your WiFi always strong!",
            "Leaving already? Did you find the treasure map we hid in the office fridge?",
            "So long, and thanks for all the fish! Don't forget to bring the towel!",
            "Adios, amigo! Remember, life is better when you're laughing!"
    };

    public static final String[] ROLLED_DICE = {
            "You rolled the dice and it landed on `%i%`! Looks like luck is on your side today.",
            "Oh, snap! The dice rolled a `%i%`. Prepare for some epic gaming moments!",
            "Congratulations! You rolled a whopping `%i%`. You're on a winning streak!",
            "You got a `%i%`! Quick, make a wish! Maybe the dice will grant it.",
            "Whoa! The dice revealed `%i%`. That's the magic number! Enjoy your victory.",
            "Guess what? The dice rolled `%i%`. It seems like fortune favors the bold!",
            "You rolled a `%i%` and unlocked the door to success. Keep going!",
            "Incredible! The dice shows `%i%`. Prepare for some wild adventures!",
            "Aha! The dice landed on `%i%`. It's your lucky charm today!",
            "You rolled the dice and got `%i%`. Time to celebrate, my friend!"
    };

    public static void setupBot(){

        LogSystem.log(LogType.INFO, "Setup default instances");
        time_since_start = System.currentTimeMillis();
        version = "2.4";

        LogSystem.log(LogType.INFO, "Initialize and connect bot");
        bot = new DiscordApiBuilder().setToken("MTA1ODAyMzc0MTA3NjAxNzIyMg.GtNiZE.YbTL7Nn3LQEIW1spqg2BvedptvjDydsFZ5E2Y4").setAllIntents().login().join();

        LogSystem.log(LogType.INFO, "Setup managers");
        tempChannelManager = new TemporaryChannelManager();
        inviteManager = new InviteManager();
        musicManager = new MusicManager();

        LogSystem.log(LogType.INFO, "Load platforms data into cache");

        //LogSystem.log(LogType.INFO, "Refresh commands");
        //CommandUtils.deleteCommands();
        //CommandUtils.createCommands();

        LogSystem.log(LogType.INFO, "Register listeners");
        bot.addServerMemberBanListener(new nServerMemberBanListener());
        bot.addServerMemberJoinListener(new nServerMemberJoinListener());
        bot.addServerMemberLeaveListener(new nServerMemberLeaveListener());
        bot.addServerMemberUnbanListener(new nServerMemberUnbanListener());
        bot.addSlashCommandCreateListener(new nSlashCommandCreateListener());
        bot.addServerVoiceChannelMemberJoinListener(new nServerVoiceChannelMemberJoinListener());
        bot.addServerVoiceChannelMemberLeaveListener(new nServerVoiceChannelMemberLeaveListener());

        LogSystem.log(LogType.INFO, "Delete unwanted channels in temporary category");
        Server nixCrew = ((Server)bot.getServers().toArray()[0]);
        nixCrew.getChannelCategoryById(CREATE_CHANNEL_CATEGORY_ID).ifPresent(category -> {
            for(RegularServerChannel channel : category.getChannels()){
                if(!channel.getIdAsString().equals(CREATE_CHANNEL_CHANNEL_ID)){
                    if(channel.asServerVoiceChannel().isPresent() && channel.asServerVoiceChannel().get().getConnectedUserIds().size() > 0){
                        continue;
                    }
                    channel.delete().join();
                    LogSystem.log(LogType.INFO, "Deleted unwanted channel '" + channel.getName() + "' in temporary category");
                }
            }
        });

        LogSystem.log(LogType.INFO, "Load all invites in invite manager");
        for(RichInvite invite : nixCrew.getInvites().join()){
            long userId = 0;

            if(invite.getInviter().isPresent()){
                userId = invite.getInviter().get().getId();
            }

            InviteInstance inv = new InviteInstance(invite.getCode(), userId, invite.getUses());
            inviteManager.addInvite(inv);
            LogSystem.log(LogType.INFO, "Added invite (code='" + inv.getCode() + "', inviter='" + inv.getCode() + "') into invite manager");
        }

        LogSystem.log(LogType.INFO, "Update activity");
        bot.updateActivity(ActivityType.PLAYING, "with " + ((Server)bot.getServers().toArray()[0]).getMembers().size() + " users");

        LogSystem.log(LogType.INFO, "Bot successfully initialized and loaded. It took " + (System.currentTimeMillis() - time_since_start) + "ms");
    }

}
