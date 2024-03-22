package cz.nix3r.managers;

import cz.nix3r.instances.InviteInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InviteManager {

    private HashMap<String, InviteInstance> inviteMap;

    public InviteManager() {
        inviteMap = new HashMap<String, InviteInstance>();
    }

    public InviteInstance addInvite(InviteInstance instance){
        inviteMap.put(instance.getCode(), instance);
        return instance;
    }

    public InviteInstance getInviteByCode(String code){
        if(inviteMap.containsKey(code)) return inviteMap.get(code);
        return null;
    }

    public HashMap<String, InviteInstance> getInviteMap(){
        return inviteMap;
    }
}
