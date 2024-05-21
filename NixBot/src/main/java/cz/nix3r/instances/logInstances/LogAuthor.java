package cz.nix3r.instances.logInstances;

public class LogAuthor {
    private long id;
    private String name;
    private String profileUri;

    public LogAuthor(long id, String name, String profileUri) {
        this.id = id;
        this.name = name;
        this.profileUri = profileUri;
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

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }
}
