package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketType;
import cz.iliev.utils.CommonUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseTicketService {

    public static void addTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void getTicket(long id, Consumer<TicketInstance> callback){

        try{
            // do the stuff
            callback.accept(null);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(null);
        }

    }

    public static void updateTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void removeTicket(TicketInstance ticket, Consumer<Boolean> callback){

        try{
            // do the stuff
            callback.accept(true);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(false);
        }

    }

    public static void getTicketStatuses(Consumer<HashMap<String, String>> callback){

        try{
            callback.accept(null);
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            callback.accept(null);
        }

    }

    public static void getTicketTypes(Consumer<Object> callback){

        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT * FROM TicketType");
            var reader = statement.executeQuery();

            List<TicketType> types = new ArrayList<>();

            while(reader.next()){
                types.add(new TicketType(
                        reader.getInt(1),
                        reader.getString(2),
                        reader.getString(3),
                        reader.getString(4)
                ));
            }
            reader.close();

            callback.accept(types);
        }
        catch (Exception ex){
            callback.accept(ex);
        }

    }

}
