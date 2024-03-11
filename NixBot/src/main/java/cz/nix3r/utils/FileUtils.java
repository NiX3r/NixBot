package cz.nix3r.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.nix3r.instances.Ticket;
import cz.nix3r.managers.TicketManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

public class FileUtils {

    private static final String ACTIVE_TICKETS_PATH = "./data/active_tickets.json";

    public static Exception saveActiveTickets(){

        createDefaultFolders();

        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    ACTIVE_TICKETS_PATH));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.ticketManager));
            f_writer.flush();
            f_writer.close();
            return null;
        }
        catch (Exception ex){
            return ex;
        }

    }

    public static Exception loadActiveTickets(){

        createDefaultFolders();

        try {
            String json = new String(Files.readAllBytes(Paths.get(ACTIVE_TICKETS_PATH)));
            CommonUtils.ticketManager = new Gson().fromJson(json, TicketManager.class);
            return null;
        }
        catch (Exception ex){
            return ex;
        }

    }

    public static Exception saveTicket(Ticket ticket){

        createDefaultFolders();

        try {
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(
                    getArchiveFullPath() + "/" + ticket.getId() + "-" + ticket.getAuthor().getName() + ".json"));
            f_writer.write(new GsonBuilder().create().toJson(CommonUtils.ticketManager.getActiveTickets().get(ticket.getChannelId())));
            f_writer.flush();
            f_writer.close();
            return null;
        }
        catch (Exception ex){
            return ex;
        }

    }

    private static void createDefaultFolders(){
        Date date = new Date();
        File file = new File(getArchiveFullPath());
        file.mkdirs();
        file = null;
    }

    private static String getArchiveFullPath(){
        LocalDateTime date = LocalDateTime.now();
        return "./data/tickets/" + date.getYear() + "/" + date.getMonthValue() + "/" + date.getDayOfMonth();
    }

}
