package cz.nix3r.utils;

import cz.nix3r.enums.LogType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class LogSystem {

    private static final int LOG_TO_SAVE  = 5;

    private static ArrayList<String> logs = new ArrayList<String>();

    public static void log(LogType type, String message){

        String line = "[" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)).replace(",", "").replace("/", ".") + "] " + type.toString() + " >> " + message;
        System.out.println(line);
        logs.add(line);

        if(logs.size() == LOG_TO_SAVE)
            save();

    }

    public static void save(){

        FileWriter fw = null;
        try {
            fw = new FileWriter("./nixbot.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String line : logs){
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
            logs.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
