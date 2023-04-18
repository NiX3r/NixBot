package cz.nix3r.utils;

import org.javacord.api.DiscordApi;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static DiscordApi bot;
    public static String version;

    public static final String WELCOME_CHANNEL_ID = "611985124057284621";
    public static final String NIXBOT_CHANNEL_ID = "1058017127988211822";
    public static final List<String> DEFAULT_ROLES_ID = new ArrayList<String>() {{add("1058009225491656724");}};

}
