package cz.nix3r.managers;

import cz.nix3r.utils.FileUtils;

import java.util.HashMap;
import java.util.Random;

public class UserVerificationManager {

    // Key = user ID | Value = code
    private HashMap<Long, String> usersCodes;

    public UserVerificationManager(){
        usersCodes = FileUtils.loadUsersVerificationCodes();
        if(usersCodes == null){
            usersCodes = new HashMap<Long, String>();
        }
    }

    public String addUser(long userId){
        if(usersCodes.containsKey(userId))
            return null;
        Random rand = new Random();
        int r = rand.nextInt(100000,1000000);
        String code = Integer.toHexString(r);
        usersCodes.put(userId, code);
        return code;
    }

    public HashMap<Long, String> getUsersCodes() {
        return usersCodes;
    }
}
