package cz.iliev.managers.ticket_manager.instances;

public class TicketType {

    private int id;
    private String name;
    private String emoji;
    private String description;

    public TicketType(int id, String name, String emoji, String description){

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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
