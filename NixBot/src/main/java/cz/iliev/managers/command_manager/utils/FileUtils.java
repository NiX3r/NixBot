package cz.iliev.managers.command_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.instances.SettingsInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;
import org.apache.commons.logging.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/dice_messages.json";

    public static List<String> loadDiceMessages(){
        LogUtils.info("Trying to load dice messages");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<List<String>>(){});
            LogUtils.info("Dice messages loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            var output = new ArrayList<String>(Arrays.asList(
                    "You rolled the dice and it landed on `%i%`! Looks like luck is on your side today.",
                    "Oh, snap! The dice rolled a `%i%`. Prepare for some epic gaming moments!",
                    "Congratulations! You rolled a whopping `%i%`. You're on a winning streak!",
                    "You got a `%i%`! Quick, make a wish! Maybe the dice will grant it.",
                    "Whoa! The dice revealed `%i%`. That's the magic number! Enjoy your victory.",
                    "Guess what? The dice rolled `%i%`. It seems like fortune favors the bold!",
                    "You rolled a `%i%` and unlocked the door to success. Keep going!",
                    "Incredible! The dice shows `%i%`. Prepare for some wild adventures!",
                    "Aha! The dice landed on `%i%`. It's your lucky charm today!",
                    "You rolled the dice and got `%i%`. Time to celebrate, my friend!"
            ));
            saveDiceMessages(output);
            return output;
        }
    }

    public static Exception saveDiceMessages(List<String> data){
        LogUtils.info("Trying to save dice messages");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Dice messages saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
