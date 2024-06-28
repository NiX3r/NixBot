package cz.iliev.managers.database_manager.entities.ticket;

public class TicketType {

    private int id;
    private String name;
    private String emoji;
    private String description;

    public TicketType(int id, String name, String emoji, String description) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getDescription() {
        return description;
    }
}
