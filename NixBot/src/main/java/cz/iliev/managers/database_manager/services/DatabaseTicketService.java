package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketType;
import cz.iliev.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseTicketService {

    public static void AddTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void GetTicket(long id, Consumer<TicketInstance> callback){

        try{
            // do the stuff
            callback.accept(null);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(null);
        }

    }

    public static void UpdateTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void RemoveTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void GetTicketStatuses(Consumer<HashMap<String, String>> callback){

        try{
            callback.accept(null);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(null);
        }

    }

    public static void GetTicketTypes(Consumer<List<TicketType>> callback){

        try{
            // do the stuff
            callback.accept(null);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(null);
        }

    }

}
