package cz.nix3r;

import cz.nix3r.enums.LogType;
import cz.nix3r.listeners.*;
import cz.nix3r.utils.CommandUtils;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.server.Server;

public class NixBot {

    public static void main(String[] args) {

        LogSystem.log(LogType.INFO, "Bot started. Initializing ..");
        try{
            CommonUtils.time_since_start = System.currentTimeMillis();
            CommonUtils.version = "1.3";
            CommonUtils.bot = new DiscordApiBuilder().setToken("MTA1ODAyMzc0MTA3NjAxNzIyMg.GtNiZE.YbTL7Nn3LQEIW1spqg2BvedptvjDydsFZ5E2Y4").setAllIntents().login().join();
            CommandUtils.createCommands();
            CommonUtils.bot.addServerMemberBanListener(new nServerMemberBanListener());
            CommonUtils.bot.addServerMemberJoinListener(new nServerMemberJoinListener());
            CommonUtils.bot.addServerMemberLeaveListener(new nServerMemberLeaveListener());
            CommonUtils.bot.addServerMemberUnbanListener(new nServerMemberUnbanListener());
            CommonUtils.bot.addSlashCommandCreateListener(new nSlashCommandCreateListener());
            CommonUtils.bot.updateActivity(ActivityType.PLAYING, "with " + ((Server)CommonUtils.bot.getServers().toArray()[0]).getMembers().size() + " users");
            LogSystem.log(LogType.INFO, "Bot successfully initialized and loaded");
        }
        catch (Exception ex){
            LogSystem.log(LogType.FATAL_ERROR, "Bot can't be initialized or loaded. Error: " + ex.getMessage());
        }

    }

}