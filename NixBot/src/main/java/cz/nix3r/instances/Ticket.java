package cz.nix3r.instances;

import cz.nix3r.enums.TicketStatus;
import cz.nix3r.utils.CommandUtils;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.FileUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class Ticket {
    private int id;
    private TicketMember author;
    private long create_date;
    private long channel_id;
    private String topic;
    private TicketStatus status;
    private List<TicketMember> members;
    private List<TicketMessage> messages;

    public Ticket(int id, TicketMember author, long create_date, long channel_id, String topic, TicketStatus status, List<TicketMember> members, List<TicketMessage> messages) {
        this.id = id;
        this.author = author;
        this.create_date = create_date;
        this.channel_id = channel_id;
        this.topic = topic;
        this.status = status;
        this.members = members;
        this.messages = messages;
    }

    public TicketMember getAuthor() {
        return author;
    }

    public void setAuthor(TicketMember author) {
        this.author = author;
    }

    public long getCreateDate() {
        return create_date;
    }

    public void setCreateDate(long create_date) {
        this.create_date = create_date;
    }

    public long getChannelId() {
        return channel_id;
    }

    public void setChannelId(long channel_id) {
        this.channel_id = channel_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public List<TicketMember> getMembers() {
        return members;
    }

    public void setMembers(List<TicketMember> members) {
        this.members = members;
    }

    public List<TicketMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TicketMessage> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public boolean hasMember(long id){
        for(TicketMember member : members){
            if(member.getId() == id)
                return true;
        }
        return false;
    }

    public static int closeTicket(Server server, TextChannel channel, User user, boolean isResolved){

        Ticket ticket = CommonUtils.ticketManager.getActiveTickets().get(channel.getId());

        if(ticket == null)
            return -1;

        if(!CommonUtils.isUserAdmin(server, user) || ticket.getAuthor().getId() != user.getId()){
            return -2;
        }

        if(isResolved)
            ticket.setStatus(TicketStatus.RESOLVED);
        else
            ticket.setStatus(TicketStatus.CLOSED);

        FileUtils.saveTicket(ticket);
        CommonUtils.ticketManager.removeTicketFromCacheByOwnerId(ticket.getAuthor().getId());

        server.getChannelById(channel.getId()).get().delete().join();

        server.getMemberById(ticket.getAuthor().getId()).ifPresent(serverUser -> {
            serverUser.sendMessage("Konverzace s podporou byla uzavřena.").join();
        });

        return 1;

    }

}
