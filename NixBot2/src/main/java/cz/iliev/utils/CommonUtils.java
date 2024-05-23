package cz.iliev.utils;

import cz.iliev.instances.SettingsInstance;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.bot_activity_manager.BotActivityManager;
import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.console_command_manager.ConsoleCommandManager;
import cz.iliev.managers.invite_manager.InviteManager;
import cz.iliev.managers.main_manager.MainManager;
import cz.iliev.managers.music_manager.MusicManager;
import cz.iliev.managers.statistics_manager.StatisticsManager;
import cz.iliev.managers.temporary_channel_manager.TemporaryChannelManager;
import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.user_verification_manager.UserVerificationManager;
import cz.iliev.threads.ShutdownThread;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.RegularServerChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;

import javax.swing.plaf.multi.MultiScrollBarUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CommonUtils {

    public static DiscordApi bot;
    public static SettingsInstance settings;

    public static final String VERSION = "3.0";
    public static final long START_TIME = System.currentTimeMillis();
    public static final String NIX_CREW_ID = "611985124023730185";

    public static AnnouncementManager announcementManager;
    public static BotActivityManager botActivityManager;
    public static CommandManager commandManager;
    public static ConsoleCommandManager consoleCommandManager;
    public static InviteManager inviteManager;
    public static MainManager mainManager;
    public static MusicManager musicManager;
    public static StatisticsManager statisticsManager;
    public static TemporaryChannelManager temporaryChannelManager;
    public static TicketManager ticketManager;
    public static UserVerificationManager userVerificationManager;

    public static void setupBot(){
        LogUtils.info("Load settings from file");

        settings = FileUtils.loadTemporaryChannelCache();
        if(settings == null){
            LogUtils.fatalError("Can't load settings. Turning the bot off");
            return;
        }

        LogUtils.info("Initialize and connect bot");
        bot = new DiscordApiBuilder().setToken(settings.getBotToken()).setAllIntents().login().join();

        LogUtils.info("Setup managers");
        announcementManager = new AnnouncementManager();
        botActivityManager = new BotActivityManager();
        commandManager = new CommandManager();
        consoleCommandManager = new ConsoleCommandManager();
        inviteManager = new InviteManager();
        mainManager = new MainManager();
        musicManager = new MusicManager();
        statisticsManager = new StatisticsManager();
        temporaryChannelManager = new TemporaryChannelManager();
        ticketManager = new TicketManager();
        userVerificationManager = new UserVerificationManager();

        LogUtils.info("Initializing and starting threads");
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        // Refresh commands if needed
        //CommandUtils.deleteCommands();
        //CommandUtils.createCommands();

        LogUtils.info("Bot successfully initialized and loaded. It took " + (System.currentTimeMillis() - START_TIME) + "ms");
    }

    public static void shutdownBot(){
        announcementManager.kill();
        botActivityManager.kill();
        commandManager.kill();
        consoleCommandManager.kill();
        inviteManager.kill();
        mainManager.kill();
        musicManager.kill();
        statisticsManager.kill();
        temporaryChannelManager.kill();
        ticketManager.kill();
        userVerificationManager.kill();
    }

    public static void throwException(Exception exception){throwException(exception, false);}
    public static void throwException(Exception exception, boolean isFatal){

        if(isFatal) LogUtils.fatalError(exception.toString());
        else LogUtils.error(exception.toString());

        announcementManager.sendException(exception, isFatal);

    }

    public static String formatTimeToMinutes(long time){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
