package cz.nix3r.instances.logInstances;

public class LogServerCategory {

    private long id;
    private String name;

    public LogServerCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
