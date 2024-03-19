package cz.nix3r.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.*;
import cz.nix3r.managers.TicketManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileUtils {

    public static final String TICKETS_ARCHIVE = "./data/tickets_archive/";
    public static final String STATISTICS_ARCHIVE = "./data/statistics_archive/";
    public static final String SETTINGS_PATH = "./settings.json";
    public static final String MESSAGES_PATH = "./messages.json";
    public static final String ROLE_SETTER_PATH = "./data/role_setter.json";
    private static final String ACTIVE_TICKETS_PATH = "./data/active_tickets.json";

    public static Exception loadSettings(){
        LogSystem.log(LogType.INFO, "Trying to load settings");
        try {
            String json = new String(Files.readAllBytes(Paths.get(SETTINGS_PATH)));
            CommonUtils.settings = new Gson().fromJson(json, AppSettingsInstance.class);
            LogSystem.log(LogType.INFO, "Settings loaded");
            return null;
        }
        catch (Exception ex){
            LogSystem.log(LogType.ERROR, "Can't load settigns. Error: " + ex.getMessage());
            return ex;
        }
    }

    public static Exception saveSettings(){
        LogSystem.log(LogType.INFO, "Trying to save settings");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(SETTINGS_PATH));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.settings));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Settings saved");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }
    }

    public static Exception loadMessages(){
        LogSystem.log(LogType.INFO, "Trying to load messages");
        try {
            String json = new String(Files.readAllBytes(Paths.get(MESSAGES_PATH)));
            CommonUtils.messages = new Gson().fromJson(json, MessagesInstance.class);
            LogSystem.log(LogType.INFO, "messages loaded");
            return null;
        }
        catch (Exception ex){
            // Load default messages
            CommonUtils.messages = new MessagesInstance(new ArrayList<String>(Arrays.asList(
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
            )), new ArrayList<String>(Arrays.asList(
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
            )), new ArrayList<String>(Arrays.asList(
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
            )));
            saveMessages();
            return ex;
        }
    }

    public static Exception saveMessages(){
        LogSystem.log(LogType.INFO, "Trying to save messages");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(MESSAGES_PATH));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.messages));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Messages saved");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }
    }

    public static Exception loadRoleSetter(){
        LogSystem.log(LogType.INFO, "Trying to load role setter");
        try {
            String json = new String(Files.readAllBytes(Paths.get(ROLE_SETTER_PATH)));
            CommonUtils.roleSetter = new Gson().fromJson(json, new TypeToken<List<RoleSetterInstance>>(){});
            LogSystem.log(LogType.INFO, "Role setter loaded");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }
    }

    public static Exception saveRoleSetter(){
        LogSystem.log(LogType.INFO, "Trying to save role setter");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(ROLE_SETTER_PATH));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.roleSetter));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Role setter saved");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }
    }

    public static Exception saveActiveTickets(){

        LogSystem.log(LogType.INFO, "Trying to save active tickets");
        createDefaultTicketFolders();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    ACTIVE_TICKETS_PATH));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.ticketManager));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Active tickets saved");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }

    }

    public static Exception loadActiveTickets(){

        LogSystem.log(LogType.INFO, "Trying to load active tickets");
        try {
            String json = new String(Files.readAllBytes(Paths.get(ACTIVE_TICKETS_PATH)));
            CommonUtils.ticketManager = new Gson().fromJson(json, TicketManager.class);
            LogSystem.log(LogType.INFO, "Active tickets loaded");
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }

    }

    public static Exception saveTicket(Ticket ticket){

        LogSystem.log(LogType.INFO, "Trying to save ticket");
        createTicketArchiveFolders(ticket);

        try {
            String path = getTicketArchiveFullPath(ticket) + "/archived.json";
            BufferedWriter f_writer = new BufferedWriter(
                    new FileWriter(path)
            );
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.ticketManager.getActiveTickets().get(ticket.getChannelId())));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Ticket saved on: " + path);
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }

    }

    public static Exception saveStatistics(){
        LogSystem.log(LogType.INFO, "Trying to save statistics");
        try{
            createDefaultStatisticsFolders();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String path = STATISTICS_ARCHIVE + "statistics-" + ((calendar.get(Calendar.MONTH) + 1)) + "-" + (calendar.get(Calendar.YEAR)) + ".json";
            BufferedWriter f_writer = new BufferedWriter(
                    new FileWriter(path)
            );
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.statisticsManager.getStatistics()));
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Statistics saved on: " + path);
            return null;
        }
        catch (Exception ex){
            DiscordUtils.throwError(ex);
            return ex;
        }
    }

    public static StatisticsInstance loadCurrentMonthStatistics(){
        LogSystem.log(LogType.INFO, "Trying to load current month statistics");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String path = STATISTICS_ARCHIVE + "statistics-" + ((calendar.get(Calendar.MONTH) + 1)) + "-" + (calendar.get(Calendar.YEAR)) + ".json";
            String json = new String(Files.readAllBytes(Paths.get(path)));
            StatisticsInstance instance = new Gson().fromJson(json, StatisticsInstance.class);
            LogSystem.log(LogType.INFO, "Current month statistics loaded");
            return instance;
        }
        catch (Exception ex){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            DiscordUtils.throwError(ex);
            return new StatisticsInstance(new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(),
                    new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(),
                    new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(),
                    new HashMap<Long, Long>(), new HashMap<String, Long>(), new HashMap<String, Long>(), 0,
                    0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1);
        }
    }

    private static void createDefaultTicketFolders(){
        File file = new File(TICKETS_ARCHIVE);
        file.mkdirs();
        file = null;
    }

    private static void createDefaultStatisticsFolders(){
        File file = new File(STATISTICS_ARCHIVE);
        file.mkdirs();
        file = null;
    }

    private static void createTicketArchiveFolders(Ticket ticket){
        File file = new File(getTicketArchiveFullPath(ticket));
        file.mkdirs();
        file = null;
    }

    public static String getTicketArchiveFullPath(Ticket ticket){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ticket.getCreateDate());
        return TICKETS_ARCHIVE + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + ticket.getId() + "-" + ticket.getAuthor().getName();
    }

}
