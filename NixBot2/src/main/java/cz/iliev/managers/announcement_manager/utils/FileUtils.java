package cz.iliev.managers.announcement_manager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.iliev.utils.LogUtils;

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

}
