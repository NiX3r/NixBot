package cz.nix3r;

import cz.nix3r.enums.LogType;
import cz.nix3r.listeners.nServerMemberBanListener;
import cz.nix3r.listeners.nServerMemberJoinListener;
import cz.nix3r.listeners.nServerMemberLeaveListener;
import cz.nix3r.listeners.nServerMemberUnbanListener;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.DiscordApiBuilder;

public class NixBot {

    public static void main(String[] args) {

        LogSystem.log(LogType.INFO, "Bot started. Initializing ..");
        try{
            CommonUtils.Bot = new DiscordApiBuilder().setToken("MTA1ODAyMzc0MTA3NjAxNzIyMg.GxVGRB.ec4vp_XrZRVxcFwvQdiKtOpW0OMCQaAUsQA3uY").setAllIntents().login().join();
            CommonUtils.Bot.addServerMemberBanListener(new nServerMemberBanListener());
            CommonUtils.Bot.addServerMemberJoinListener(new nServerMemberJoinListener());
            CommonUtils.Bot.addServerMemberLeaveListener(new nServerMemberLeaveListener());
            CommonUtils.Bot.addServerMemberUnbanListener(new nServerMemberUnbanListener());
            LogSystem.log(LogType.INFO, "Bot successfully initialized and loaded");
        }
        catch (Exception ex){
            LogSystem.log(LogType.FATAL_ERROR, "Bot can't be initialized or loaded. Error: " + ex.getMessage());
        }

    }

}