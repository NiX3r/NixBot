package cz.iliev.utils;

import cz.iliev.enums.LogType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;

public class LogUtils {

    private static final String RESET_COLOR = "\u001B[0m";

    private static final HashMap<LogType, String> COLOR_PALLETE = new HashMap<LogType, String>(){{
        put(LogType.INFO, "\u001B[37m");
        put(LogType.DEBUG, "\u001B[35m");
        put(LogType.WARNING, "\u001B[33m");
        put(LogType.ERROR, "\u001B[93m");
        put(LogType.FATAL, "\u001B[31m");
    }};

    private static int errorCounter = 0;
    private static String path = "./logs/" + CommonUtils.START_TIME + "-nixbot.log";

    public static void info(String message){log(LogType.INFO, message);}
    public static void debug(String message){log(LogType.DEBUG, message);}
    public static void warning(String message){log(LogType.WARNING, message);}
    public static void error(String message){log(LogType.ERROR, message);}
    public static void fatalError(String message){log(LogType.FATAL, message);}

    private static void log(LogType type, String message){

        String line = COLOR_PALLETE.get(type) +  "[" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)).replace(",", "").replace("/", ".") + "] " + type.toString() + " >> " + message + RESET_COLOR;
        System.out.println(line);

        if(type == LogType.FATAL || type == LogType.ERROR)
            errorCounter++;

        if(type != LogType.DEBUG)
            save(line);

    }

    public static void save(String line){

        FileWriter fw = null;
        new File("./logs/").mkdirs();
        try {
            fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            bw.newLine();
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getPath(){
        return path;
    }

    public static int getErrorCounter(){
        return errorCounter;
    }

}
