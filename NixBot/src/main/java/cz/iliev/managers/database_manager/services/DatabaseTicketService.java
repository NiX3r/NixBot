package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.database_manager.entities.ticket.Ticket;
import cz.iliev.managers.database_manager.entities.ticket.TicketStatus;
import cz.iliev.managers.database_manager.entities.ticket.TicketType;
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

                Ticket ticket = new Ticket(
                        reader.getLong(1),
                        reader.getInt(2),
                        reader.getLong(3),
                        reader.getLong(4),
                        reader.getLong(5),
                        reader.getLong(6)
                );

                ticket.setType(CommonUtils.ticketManager.getTicketTypes().get(reader.getInt(7)));
                ticket.setStatus(CommonUtils.ticketManager.getTicketStatuses().get(reader.getInt(8)));

                statuses.add(ticket);
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

            HashMap<Integer, TicketStatus> statuses = new HashMap<Integer, TicketStatus>();

            while(reader.next()){

                TicketStatus status = new TicketStatus(
                        reader.getInt(1),
                        reader.getString(2),
                        reader.getString(3)
                );

                statuses.put(status.getId(), status);
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

            HashMap<Integer, TicketType> types = new HashMap<Integer, TicketType>();

            while(reader.next()){

                TicketType type = new TicketType(
                        reader.getInt(1),
                        reader.getString(2),
                        reader.getString(3),
                        reader.getString(4)
                );

                types.put(type.getId(), type);

            }
            reader.close();

            callback.accept(types);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

}
