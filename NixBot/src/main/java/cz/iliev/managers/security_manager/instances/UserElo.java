package cz.iliev.managers.security_manager.instances;

public class UserElo {

    private long userId;
    private int elo;

    public UserElo(long userId, int elo){
        this.userId = userId;
        this.elo = elo;
    }

    public long getUserId() {
        return userId;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void addElo(int elo){
        this.elo += elo;
    }
}
