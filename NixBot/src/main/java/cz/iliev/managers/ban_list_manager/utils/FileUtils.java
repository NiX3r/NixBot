package cz.iliev.managers.ban_list_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.ban_list_manager.instances.BanInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./archive/ban_list_" + (LocalDateTime.now().getYear()) + ".json";

    public static List<BanInstance> loadBans(){
        LogUtils.info("Trying to load bans");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<ArrayList<BanInstance>>(){});
            LogUtils.info("Bans loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new ArrayList<BanInstance>();
        }
    }

    public static Exception saveBans(List<BanInstance> data){
        LogUtils.info("Trying to save bans");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Bans saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
