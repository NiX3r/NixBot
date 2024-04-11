package cz.nix3r.utils;

import cz.nix3r.enums.LogType;
import cz.nix3r.instances.*;
import cz.nix3r.listeners.*;
import cz.nix3r.managers.*;
import cz.nix3r.threads.ShutdownThread;
import cz.nix3r.timers.UpdateStatisticsMessageTimer;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.RegularServerChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonUtils {

    public static DiscordApi bot;
    public static String version;
    public static long time_since_start;

    public static AppSettingsInstance settings;
    public static MessagesInstance messages;
    public static List<RoleSetterInstance> roleSetter;

    public static TemporaryChannelManager tempChannelManager;
    public static InviteManager inviteManager;
    public static MusicManager musicManager;
    public static TicketManager ticketManager;
    public static StatisticsManager statisticsManager;

    private static UpdateStatisticsMessageTimer updateStatisticsMessageTimer;

    public static final String WELCOME_CHANNEL_ID = "611985124057284621";
    public static final String NIXBOT_CHANNEL_ID = "1058017127988211822";
    public static final String CMD_CHANNEL_ID = "1118284494198288445";
    public static final String CREATE_CHANNEL_CHANNEL_ID = "1118311195867369513";
    public static final String CREATE_CHANNEL_CATEGORY_ID = "1118291032065441882";
    public static final String UNKNOWN_CHANNEL_ID = "1119262818101903410";
    public static final String SUBMIT_CHANNEL_ID = "1216822816062701618";
    public static final String SUBMIT_CATEGORY_ID = "1216859370269311026";
    public static final String PROGRAMMING_CATEGORY = "893483481483583508";
    public static final String STATS_CHANNEL_ID = "1217475154582442086";
    public static final String NEWS_CHANNEL_ID = "1219218631632748655";
    public static final String ROLES_CHANNEL_ID = "1219225196594991124";
    public static final List<String> DEFAULT_ROLES_ID = new ArrayList<String>() {{add("1058009225491656724"); add("1219589725833265152");}};

    public static void setupBot(){

        LogSystem.log(LogType.INFO, "Setup default instances");
        time_since_start = System.currentTimeMillis();
        version = "2.7.1";

        LogSystem.log(LogType.INFO, "Load settings from file");
        if(FileUtils.loadSettings() != null){
            LogSystem.log(LogType.FATAL_ERROR, "Can't load settings. Turning the bot off");
        }

        LogSystem.log(LogType.INFO, "Initialize and connect bot");
        bot = new DiscordApiBuilder().setToken(CommonUtils.settings.getBotToken()).setAllIntents().login().join();

        LogSystem.log(LogType.INFO, "Setup managers");
        tempChannelManager = new TemporaryChannelManager();
        inviteManager = new InviteManager();
        musicManager = new MusicManager();
        statisticsManager = new StatisticsManager();

        LogSystem.log(LogType.INFO, "Load data from files");
        if(FileUtils.loadRoleSetter() != null)
            roleSetter = new ArrayList<RoleSetterInstance>();
        if(FileUtils.loadActiveTickets() != null){
            CommonUtils.ticketManager = new TicketManager(0, new HashMap<Long, Ticket>());
        }
        FileUtils.loadMessages();

        LogSystem.log(LogType.INFO, "Initializing and starting threads");
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        LogSystem.log(LogType.INFO, "Load platforms data into cache");

        // Refresh commands if needed
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
        bot.addMessageComponentCreateListener(new nMessageComponentCreateListener());
        bot.addMessageCreateListener(new nMessageCreateListener());

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
            invite.getInviter().ifPresent(inviter -> {
                long userId = 0;

                if(invite.getInviter().isPresent()){
                    userId = invite.getInviter().get().getId();
                }

                InviteInstance inv = new InviteInstance(invite.getCode(), userId, invite.getUses());
                inviteManager.addInvite(inv);
                LogSystem.log(LogType.INFO, "Added invite (code='" + inv.getCode() + "', inviter='" + inviter.getName() + "') into invite manager");

            });
        }

        LogSystem.log(LogType.INFO, "Update activity");
        bot.updateActivity(ActivityType.PLAYING, "with " + ((Server)bot.getServers().toArray()[0]).getMembers().size() + " users");

        LogSystem.log(LogType.INFO, "Initialize and start timers");
        updateStatisticsMessageTimer = new UpdateStatisticsMessageTimer();

        LogSystem.log(LogType.INFO, "Bot successfully initialized and loaded. It took " + (System.currentTimeMillis() - time_since_start) + "ms");

        bot.getServers().forEach(server -> {
            if(!server.getIdAsString().equals("611985124023730185"))
                server.leave();
        });

    }

    public static boolean isUserAdmin(Server server, User user){
        if(server.getMemberById(user.getId()).isPresent()){
            return server.hasPermission(user, PermissionType.ADMINISTRATOR);
        }
        return false;
    }

    public static RoleSetterInstance getRoleSetterByRoleId(long roleId){
        return getRoleSetterByRoleId(String.valueOf(roleId));
    }

    public static RoleSetterInstance getRoleSetterByRoleId(String roleId){
        for(RoleSetterInstance roleSetterInstance : roleSetter){
            if(String.valueOf(roleSetterInstance.getRoleId()).equals(roleId))
                return roleSetterInstance;
        }
        return null;
    }

    public static void shutdownBot() {
        LogSystem.log(LogType.INFO, "Shutting down the bot ..");
        FileUtils.saveSettings();
        FileUtils.saveActiveTickets();
        FileUtils.saveStatistics();
        FileUtils.saveRoleSetter();
        bot.disconnect();
        LogSystem.save();
    }
}
