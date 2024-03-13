package cz.nix3r.managers;

import cz.nix3r.instances.Ticket;

import java.util.HashMap;

public class TicketManager {

    private int index;
    private HashMap<Long, Ticket> activeTickets; // KEY = Text Channel's ID | VALUE = Ticket instance

    public TicketManager(int index, HashMap<Long, Ticket> active_tickets){
        this.index = index;
        this.activeTickets = active_tickets;
    }

    public int getIndex() {
        return index;
    }

    public void updateIndex() {
        this.index++;
    }

    public HashMap<Long, Ticket> getActiveTickets() {
        return activeTickets;
    }

    public void addTicket(Ticket ticket){
        activeTickets.put(ticket.getChannelId(), ticket);
    }

    public Ticket getTicketByOwnerId(long id){
        for(Ticket ticket : activeTickets.values()){
            if(ticket.getAuthor().getId() == id)
                return ticket;
        }
        return null;
    }

    public void removeTicketFromCacheByOwnerId(long id){
        for(Ticket ticket : activeTickets.values()){
            if(ticket.getAuthor().getId() == id){
                activeTickets.remove(ticket.getChannelId());
            }
        }
    }

}
