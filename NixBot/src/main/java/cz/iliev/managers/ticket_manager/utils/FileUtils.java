package cz.iliev.managers.ticket_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/active_tickets.json";
    private static final String ARCHIVE_PATH = "./archive/tickets/";

    public static HashMap<Long, TicketInstance> loadActiveTickets(){
        LogUtils.info("Trying to load active tickets");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, TicketInstance>>(){});
            LogUtils.info("Active tickets messages loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new HashMap<Long, TicketInstance>();
        }
    }

    public static Exception saveActiveTickets(HashMap<Long, TicketInstance> data){
        LogUtils.info("Trying to save active tickets");
        createDefaultTicketFolders();
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Active tickets messages saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

    public static Exception archiveTicket(TicketInstance ticket){
        LogUtils.info("Trying to archive ticket");
        createTicketArchiveFolders(ticket);
        try {
            String path = getTicketArchiveFullPath(ticket) + "/archived.json";
            BufferedWriter f_writer = new BufferedWriter(
                    new FileWriter(path)
            );
            f_writer.write(new GsonBuilder().create().toJson(ticket));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Ticket archived on: " + path);
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

    private static void createDefaultTicketFolders(){
        File file = new File(ARCHIVE_PATH);
        file.mkdirs();
        file = null;
    }

    private static void createTicketArchiveFolders(TicketInstance ticket){
        File file = new File(getTicketArchiveFullPath(ticket));
        file.mkdirs();
        file = null;
    }

    public static String getTicketArchiveFullPath(TicketInstance ticket){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ticket.getCreateDate());
        return ARCHIVE_PATH + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + ticket.getId() + "-" + ticket.getAuthor().getName();
    }

}
