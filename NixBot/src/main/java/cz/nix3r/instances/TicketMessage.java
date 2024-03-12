package cz.nix3r.instances;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class TicketMessage {

    private long id;
    private TicketMember author;
    private String content;
    private HashMap<String, String> attachments;

    public TicketMessage(long id, TicketMember author, String content, HashMap<String, String> attachments) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.attachments = attachments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TicketMember getAuthor() {
        return author;
    }

    public void setAuthor(TicketMember author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(HashMap<String, String> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(String key, String fileName){
        if(!attachments.containsKey(key))
            this.attachments.put(key, fileName);
    }

}
