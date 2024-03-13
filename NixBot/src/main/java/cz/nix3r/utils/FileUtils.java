package cz.nix3r.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sedmelluq.discord.lavaplayer.remote.RemoteNode;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.AppSettingsInstance;
import cz.nix3r.instances.StatisticsInstance;
import cz.nix3r.instances.Ticket;
import cz.nix3r.managers.TicketManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;

public class FileUtils {

    public static final String TICKETS_ARCHIVE = "./data/tickets_archive/";
    public static final String STATISTICS_ARCHIVE = "./data/statistics_archive/";
    public static final String SETTINGS_PATH = "./settings.json";
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
            LogSystem.log(LogType.FATAL_ERROR, "Settings cannot be loaded. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.FATAL_ERROR, "Settings cannot be saved. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.FATAL_ERROR, "Active tickets cannot be saved. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.FATAL_ERROR, "Active tickets cannot be loaded. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.ERROR, "Ticket cannot be saved. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.ERROR, "Statistics cannot be saved. Error: " + ex.getMessage());
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
            LogSystem.log(LogType.FATAL_ERROR, "Current month statistics cannot be loaded. Error: " + ex.getMessage());
            return new StatisticsInstance(new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(),
                    new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(), new HashMap<Long, Long>(),
                    new HashMap<Long, Long>(), 0, 0, 0, 0, 0, 0,
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
