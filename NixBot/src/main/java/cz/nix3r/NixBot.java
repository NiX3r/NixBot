package cz.nix3r;

import cz.nix3r.enums.LogType;
import cz.nix3r.managers.ConsoleCommandManager;
import cz.nix3r.threads.ConsoleCommandReaderThread;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;

import java.util.Scanner;

public class NixBot {

    public static void main(String[] args) {

        LogSystem.info("Bot started. Initializing ..");
        try{
            CommonUtils.setupBot();
            ConsoleCommandReaderThread commandThread = new ConsoleCommandReaderThread();
            commandThread.run();
        }
        catch (Exception ex){
            LogSystem.fatalError("Bot can't be initialized or loaded. Error: " + ex.getMessage());
        }

    }

}