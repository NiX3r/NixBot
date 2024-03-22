package cz.nix3r;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;

import java.util.Scanner;

public class NixBot {

    public static void main(String[] args) {

        LogSystem.log(LogType.INFO, "Bot started. Initializing ..");
        try{
            CommonUtils.setupBot();
        }
        catch (Exception ex){
            LogSystem.log(LogType.FATAL_ERROR, "Bot can't be initialized or loaded. Error: " + ex.getMessage());
        }

    }

}