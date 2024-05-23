package cz.iliev.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.iliev.instances.SettingsInstance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileUtils {

    private static final String PATH = "./settings.json";

    public static SettingsInstance loadTemporaryChannelCache(){
        LogUtils.info("Trying to load settings");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, SettingsInstance.class);
            LogUtils.info("Settings setter loaded");
            return output;
        }
        catch (Exception ex){
            return null;
        }
    }

}
