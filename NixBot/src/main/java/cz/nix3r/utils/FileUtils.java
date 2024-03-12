package cz.nix3r.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sedmelluq.discord.lavaplayer.remote.RemoteNode;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.Ticket;
import cz.nix3r.managers.TicketManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

public class FileUtils {

    public static final String TICKETS_ARCHIVE = "./data/tickets_archive/";

    private static final String ACTIVE_TICKETS_PATH = "./data/active_tickets.json";

    public static Exception saveActiveTickets(){

        LogSystem.log(LogType.INFO, "Trying to save active tickets");
        createDefaultFolders();

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
        createArchiveFolders(ticket);

        try {
            String path = getArchiveFullPath(ticket) + "/archived.json";
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

    private static void createDefaultFolders(){
        File file = new File(TICKETS_ARCHIVE);
        file.mkdirs();
        file = null;
    }

    private static void createArchiveFolders(Ticket ticket){
        File file = new File(getArchiveFullPath(ticket));
        file.mkdirs();
        file = null;
    }

    public static String getArchiveFullPath(Ticket ticket){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ticket.getCreateDate());
        return TICKETS_ARCHIVE + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + ticket.getId() + "-" + ticket.getAuthor().getName();
    }

}
