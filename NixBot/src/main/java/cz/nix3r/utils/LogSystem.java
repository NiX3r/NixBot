package cz.nix3r.utils;

import cz.nix3r.enums.LogType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class LogSystem {

    private static final int LOG_TO_SAVE  = 1;
    private static final String SESSION_MILLIS = String.valueOf(System.currentTimeMillis());

    public static int error_counter = 0;

    private static ArrayList<String> logs = new ArrayList<String>();

    public static void log(LogType type, String message){

        String line = "[" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)).replace(",", "").replace("/", ".") + "] " + type.toString() + " >> " + message;
        System.out.println(line);
        logs.add(line);

        if(type == LogType.FATAL_ERROR || type == LogType.ERROR)
            error_counter++;

        if(logs.size() == LOG_TO_SAVE)
            save();

    }

    public static void save(){

        FileWriter fw = null;
        new File("./logs/").mkdirs();
        try {
            fw = new FileWriter("./logs/" + SESSION_MILLIS + "-nixbot.log", true);
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
