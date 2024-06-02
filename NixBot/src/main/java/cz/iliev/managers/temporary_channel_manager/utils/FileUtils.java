package cz.iliev.managers.temporary_channel_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileUtils {

    private static final String PATH = "./data/temp_channels.json";

    public static HashMap<Long, Long> loadTemporaryChannelCache(){
        LogUtils.info("Trying to load temporary channels");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, Long>>(){});
            LogUtils.info("Temporary channels setter loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new HashMap<Long, Long>();
        }
    }

    public static Exception saveTemporaryChannelCache(HashMap<Long, Long> data){
        LogUtils.info("Trying to save temporary channels");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Temporary channels saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
