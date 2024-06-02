package cz.iliev.managers.console_command_manager.threads;

import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.util.Scanner;

public class ConsoleCommandManagerThread extends Thread{

    @Override
    public void run(){

        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.nextLine();
            String[] commandSplitter = command.split(" ");

            if(commandSplitter.length < 2)
                return;

            LogUtils.info("Console command caught. Command: '" + command + "'");

            switch (commandSplitter[0]){

                case "ann": case "announcement":
                    CommonUtils.mainManager.onConsoleCommand(command);
                    break;

            }

            LogUtils.info("End of console command event");
        }

    }

}