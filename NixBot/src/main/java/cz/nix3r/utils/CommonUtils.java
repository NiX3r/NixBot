package cz.nix3r.utils;

import org.javacord.api.DiscordApi;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static DiscordApi bot;
    public static String version;
    public static long time_since_start;

    public static final String WELCOME_CHANNEL_ID = "611985124057284621";
    public static final String NIXBOT_CHANNEL_ID = "1058017127988211822";
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

}
