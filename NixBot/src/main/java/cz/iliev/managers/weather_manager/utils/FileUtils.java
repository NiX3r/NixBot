package cz.iliev.managers.weather_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/weather_subscribers.json";

    public static HashMap<Long, String> loadSubscribers(){
        LogUtils.info("Trying to load weather subscribers");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, String>>(){});
            LogUtils.info("Weather subscribers loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new HashMap<Long, String>();
        }
    }

    public static Exception saveSubscribers(HashMap<Long, String> data){
        LogUtils.info("Trying to save weather subscribers");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Weather subscribers saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
