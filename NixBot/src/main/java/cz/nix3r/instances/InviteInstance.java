package cz.nix3r.instances;

public class InviteInstance {

    private String code;
    private long creator_id;
    private int uses;

    public InviteInstance(String code, long creator_id, int uses) {
        this.code = code;
        this.creator_id = creator_id;
        this.uses = uses;
    }

    public String getCode() {
        return code;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public int getUses() {
        return uses;
    }

    public void incrementUses(){
        uses++;
    }
}
