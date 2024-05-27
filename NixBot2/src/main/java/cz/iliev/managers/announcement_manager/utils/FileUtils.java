package cz.iliev.managers.announcement_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.announcement_manager.instances.MessagesInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FileUtils {

    private static final String PATH = "./data/messages.json";

    public static MessagesInstance loadMessages(){
        LogUtils.info("Trying to load messages");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, MessagesInstance.class);
            LogUtils.info("Messages loaded");
            return output;
        }
        catch (Exception ex){
            var output = new MessagesInstance(
                    new ArrayList<String>(Arrays.asList(
                            "Welcome! Prepare to be amazed!",
                            "Hello and welcome! Abandon all hope, ye who enter here!",
                            "Welcome to our community! Don't worry, we don't bite... hard!",
                            "We're glad to have you here! Our jokes are terrible, but we try!",
                            "Welcome aboard! Fasten your seatbelts and get ready for a wild ride!",
                            "Welcome, new friend! We've been expecting you, and the secret handshake is optional.",
                            "Greetings and welcome! Our sarcasm levels are off the charts, so buckle up!",
                            "A warm welcome to you! Just a heads up, puns are our currency here.",
                            "Welcome to the team! We work hard, but our coffee breaks are legendary.",
                            "Welcome, and enjoy your stay! Remember, laughter is the best medicine!"
                    )), new ArrayList<String>(Arrays.asList(
                            "Sad to see you go! Who will bring the snacks now?",
                            "Leaving so soon? Don't forget to take the office plant with you!",
                            "Goodbye, quitter! We never really liked you anyway... just kidding!",
                            "Off to new adventures? Take us with you in your suitcase, please!",
                            "Farewell, dear friend! Don't forget to send postcards from your secret hideout!",
                            "You're leaving? Time to change the password to the secret clubhouse!",
                            "Goodbye, comrade! May your memes always be dank and your WiFi always strong!",
                            "Leaving already? Did you find the treasure map we hid in the office fridge?",
                            "So long, and thanks for all the fish! Don't forget to bring the towel!",
                            "Adios, amigo! Remember, life is better when you're laughing!"
                    ))
            );
            saveMessages(output);
            return output;
        }
    }

    public static Exception saveMessages(MessagesInstance data){
        LogUtils.info("Trying to save messages");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Messages saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
