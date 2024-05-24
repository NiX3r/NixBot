package cz.iliev.managers.ticket_manager.instances;

import cz.iliev.managers.ticket_manager.enums.TicketStatus;

import java.util.List;

public class TicketInstance {
    private int id;
    private TicketMemberInstance author;
    private long create_date;
    private long channel_id;
    private String topic;
    private TicketStatus status;
    private List<TicketMemberInstance> members;
    private List<TicketMessageInstance> messages;

    public TicketInstance(int id, TicketMemberInstance author, long create_date, long channel_id, String topic, TicketStatus status, List<TicketMemberInstance> members, List<TicketMessageInstance> messages) {
        this.id = id;
        this.author = author;
        this.create_date = create_date;
        this.channel_id = channel_id;
        this.topic = topic;
        this.status = status;
        this.members = members;
        this.messages = messages;
    }

    public TicketMemberInstance getAuthor() {
        return author;
    }

    public void setAuthor(TicketMemberInstance author) {
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

    public List<TicketMemberInstance> getMembers() {
        return members;
    }

    public void setMembers(List<TicketMemberInstance> members) {
        this.members = members;
    }

    public List<TicketMessageInstance> getMessages() {
        return messages;
    }

    public void setMessages(List<TicketMessageInstance> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public boolean hasMember(long id){
        for(TicketMemberInstance member : members){
            if(member.getId() == id)
                return true;
        }
        return false;
    }
}
