package cz.nix3r.utils;

import cz.nix3r.instances.logInstances.LogAuthor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EventLogSystem {

    private static final String path = "./logs/events/";
    private static final String SESSION_MILLIS = String.valueOf(System.currentTimeMillis());

    public static void createFile(){
        new File(path).mkdirs();
        FileWriter fw = null;
        try {
            fw = new FileWriter(path + SESSION_MILLIS + "-logs.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String line = "Name,Time,Author ID,Author Name,Author Profile URI,Data";
            bw.write(line);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logEvent(String name, LogAuthor author, String data){
        FileWriter fw = null;
        new File(path).mkdirs();
        try {
            fw = new FileWriter(path + SESSION_MILLIS + "-logs.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String line = name + "," + System.currentTimeMillis() + "," + author.getId() + "," + author.getName() + ",\"" + author.getProfileUri() + "\",\"" + (data == null ? "" : data.replace("\"", "'")) + "\"";
            bw.newLine();
            bw.write(line);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
