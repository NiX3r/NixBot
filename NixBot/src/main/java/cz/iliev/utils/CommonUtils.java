package cz.iliev.utils;

import com.vdurmont.emoji.EmojiParser;
import cz.iliev.instances.SettingsInstance;
import cz.iliev.managers.announcement_manager.AnnouncementManager;
import cz.iliev.managers.ban_list_manager.BanListManager;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.managers.ban_list_manager.instances.PunishmentInstance;
import cz.iliev.managers.bot_activity_manager.BotActivityManager;
import cz.iliev.managers.command_manager.CommandManager;
import cz.iliev.managers.command_manager.utils.CommandManagerUtils;
import cz.iliev.managers.console_command_manager.ConsoleCommandManager;
import cz.iliev.managers.main_manager.MainManager;
import cz.iliev.managers.music_manager.MusicManager;
import cz.iliev.managers.role_manager.RoleManager;
import cz.iliev.managers.statistics_manager.StatisticsManager;
import cz.iliev.managers.stay_fit_manager.StayFitManager;
import cz.iliev.managers.temporary_channel_manager.TemporaryChannelManager;
import cz.iliev.managers.ticket_manager.TicketManager;
import cz.iliev.managers.user_verification_manager.UserVerificationManager;
import cz.iliev.managers.weather_manager.WeatherManager;
import cz.iliev.threads.ShutdownThread;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonUtils {

    public static DiscordApi bot;
    public static SettingsInstance settings;

    public static final String VERSION = "3.1.0";
    public static final long START_TIME = System.currentTimeMillis();
    public static final String NIX_CREW_ID = "611985124023730185";

    public static AnnouncementManager announcementManager;
    public static BanListManager banListManager;
    public static BotActivityManager botActivityManager;
    public static CommandManager commandManager;
    public static ConsoleCommandManager consoleCommandManager;
    public static MainManager mainManager;
    public static MusicManager musicManager;
    public static RoleManager roleManager;
    public static StatisticsManager statisticsManager;
    public static StayFitManager stayFitManager;
    public static TemporaryChannelManager temporaryChannelManager;
    public static TicketManager ticketManager;
    public static UserVerificationManager userVerificationManager;
    public static WeatherManager weatherManager;

    public static void setupBot(){
        LogUtils.info("Load settings from file");

        FileUtils.createDefaultFolders();

        settings = FileUtils.loadSettings();
        if(settings == null){
            LogUtils.fatalError("Can't load settings. Turning the bot off");
            return;
        }

        LogUtils.info("Initialize and connect bot");
        bot = new DiscordApiBuilder().setToken(settings.getBotToken()).setAllIntents().login().join();

        LogUtils.info("Setup managers");
        announcementManager = new AnnouncementManager();
        banListManager = new BanListManager();
        botActivityManager = new BotActivityManager();
        commandManager = new CommandManager();
        consoleCommandManager = new ConsoleCommandManager();
        musicManager = new MusicManager();
        roleManager = new RoleManager();
        statisticsManager = new StatisticsManager();
        stayFitManager = new StayFitManager();
        temporaryChannelManager = new TemporaryChannelManager();
        ticketManager = new TicketManager();
        userVerificationManager = new UserVerificationManager();
        weatherManager = new WeatherManager();
        mainManager = new MainManager();

        LogUtils.info("Initializing and starting threads");
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        // Refresh commands if needed
        //CommandManagerUtils.deleteCommands();
        //CommandManagerUtils.createCommands();

        LogUtils.info("Bot successfully initialized and loaded. It took " + (System.currentTimeMillis() - START_TIME) + "ms");
    }

    public static void shutdownBot(){
        announcementManager.kill();
        banListManager.kill();
        botActivityManager.kill();
        commandManager.kill();
        consoleCommandManager.kill();
        mainManager.kill();
        musicManager.kill();
        roleManager.kill();
        statisticsManager.kill();
        stayFitManager.kill();
        temporaryChannelManager.kill();
        ticketManager.kill();
        userVerificationManager.kill();
        FileUtils.saveSettings(settings);
    }

    public static void throwException(Exception exception){throwException(exception, false, false);}
    public static void throwException(Exception exception, boolean isFatal, boolean bypassDiscordMessage){

        if(isFatal) LogUtils.fatalError(exception.toString());
        else LogUtils.error(exception.toString());

        if(!bypassDiscordMessage)
            announcementManager.sendException(exception, isFatal);

    }

    public static void politeDisconnect(Server server){
        server.getMembers().forEach(member -> {
            if(server.hasPermission(member, PermissionType.ADMINISTRATOR)){
                member.sendMessage("Unfortunately NixBot is private bot for private server. Bot is leaving your server now.");
            }
        });
        server.leave().join();
    }

    public static boolean isUserAdmin(User user){
        for(Server server : bot.getServers()){
            if(!server.getIdAsString().equals(NIX_CREW_ID)){
                politeDisconnect(server);
                continue;
            }
            return server.hasPermission(user, PermissionType.ADMINISTRATOR);
        }
        return false;
    }

    public static String formatTimeToMinutes(long time){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String formatTimeToDateTime(long time){
        Date date=new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("d. M. y H:m");
        return format.format(date);
    }

}
