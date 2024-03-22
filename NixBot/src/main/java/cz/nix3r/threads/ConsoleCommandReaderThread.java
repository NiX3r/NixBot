package cz.nix3r.threads;

import cz.nix3r.managers.ConsoleCommandManager;
import cz.nix3r.utils.CommonUtils;

import java.util.Scanner;

public class ConsoleCommandReaderThread extends Thread{

    @Override
    public void run(){

        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.nextLine();
            ConsoleCommandManager.run(command);
        }

    }

}