package cz.iliev.managers.database_manager.entities.ticket;

import cz.iliev.managers.database_manager.entities.Member;
import cz.iliev.managers.ticket_manager.instances.TicketType;

import java.util.List;

public class Ticket {

    private long id;
    private int localId;
    private long createDate;
    private long closeDate;
    private long channelId;

    // foreigns
    private TicketType type;
    private TicketStatus status;
    private List<Member> members;
    private List<String> attachments;

    public Ticket(long id, int localId, long createDate, long closeDate, long channelId, ) {
        this.id = id;
        this.localId = localId;
        this.createDate = createDate;
        this.closeDate = closeDate;
        this.channelId = channelId;
    }

    public long getId() {
        return id;
    }

    public int getLocalId() {
        return localId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(long closeDate) {
        this.closeDate = closeDate;
    }

    public long getChannelId() {
        return channelId;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
