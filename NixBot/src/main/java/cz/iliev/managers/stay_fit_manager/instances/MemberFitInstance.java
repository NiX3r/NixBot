package cz.iliev.managers.stay_fit_manager.instances;

public class MemberFitInstance {

    private long memberId;
    private HydrateInstance hydrate;

    public MemberFitInstance(long memberId, HydrateInstance hydrate){
        this.memberId = memberId;
        this.hydrate = hydrate;
    }

    public long getMemberId() {
        return memberId;
    }

    public HydrateInstance getHydrate() {
        return hydrate;
    }

    public void setHydrate(HydrateInstance hydrate) {
        this.hydrate = hydrate;
    }
}
