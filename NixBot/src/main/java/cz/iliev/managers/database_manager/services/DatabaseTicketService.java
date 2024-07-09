package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.database_manager.entities.ticket.Ticket;
import cz.iliev.managers.database_manager.entities.ticket.TicketStatus;
import cz.iliev.managers.ticket_manager.instances.TicketInstance;
import cz.iliev.managers.ticket_manager.instances.TicketType;
import cz.iliev.utils.CommonUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseTicketService {

    public static void addTicket(Ticket ticket, Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO Ticket(LocalID,CreateDate,ChannelID,TicketTypeID,TicketStatusID) VALUES (?,?,?,?,?)");
            statement.setInt(1, ticket.getLocalId());
            statement.setLong(2, ticket.getCreateDate());
            statement.setLong(3, ticket.getChannelId());
            statement.setInt(4, ticket.getType().getId());
            statement.setInt(5, ticket.getStatus().getId());
            statement.execute();
            callback.accept(true);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

    public static void getActiveTickets(Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT * FROM Ticket WHERE CloseDate=null");
            var reader = statement.executeQuery();

            List<Ticket> statuses = new ArrayList<>();

            while(reader.next()){
                statuses.add(new Ticket(
                        reader.getLong(1),
                        reader.getInt(2),
                        reader.getLong(2),
                        reader.getLong(4)
                ));
            }
            reader.close();

            callback.accept(statuses);
        }
        catch (Exception ex){
            callback.accept(ex);
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

    public static void getTicketStatuses(Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT * FROM TicketStatus");
            var reader = statement.executeQuery();

            List<TicketStatus> statuses = new ArrayList<>();

            while(reader.next()){
                statuses.add(new TicketStatus(
                        reader.getInt(1),
                        reader.getString(2),
                        reader.getString(3)
                ));
            }
            reader.close();

            callback.accept(statuses);
        }
        catch (Exception ex){
            callback.accept(ex);
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
