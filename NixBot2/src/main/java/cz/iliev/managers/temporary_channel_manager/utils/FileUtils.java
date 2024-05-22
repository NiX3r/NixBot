package cz.iliev.managers.temporary_channel_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileUtils {

    private static final String PATH = "./data/temp_channels.json";

    public static HashMap<Long, Long> loadTemporaryChannelCache(){
        LogUtils.info("Trying to load users verification codes");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, Long>>(){});
            LogUtils.info("Users verification codes setter loaded");
            return output;
        }
        catch (Exception ex){
            //DiscordUtils.throwError(ex); TODO - send announcement on error
            return null;
        }
    }

    public static Exception saveUsersVerificationCodes(HashMap<Long, Long> data){
        LogUtils.info("Trying to save users verification codes");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Users verification codes saved");
            return null;
        }
        catch (Exception ex){
            //DiscordUtils.throwError(ex); TODO - send announcement on error
            return ex;
        }
    }

}
