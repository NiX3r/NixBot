package cz.iliev.managers.database_manager.entities.ticket;

public class TicketStatus {

    private int id;
    private String name;
    private String description;

    public TicketStatus(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
