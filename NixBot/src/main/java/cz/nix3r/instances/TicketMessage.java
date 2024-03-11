package cz.nix3r.instances;

import java.util.List;

public class TicketMessage {

    private long id;
    private TicketMember author;
    private String content;
    private List<byte[]> files;

    public TicketMessage(long id, TicketMember author, String content, List<byte[]> files) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.files = files;
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

    public List<byte[]> getFiles() {
        return files;
    }

    public void setFiles(List<byte[]> files) {
        this.files = files;
    }

}
