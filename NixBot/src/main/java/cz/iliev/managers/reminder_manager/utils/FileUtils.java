package cz.iliev.managers.reminder_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.instances.SettingsInstance;
import cz.iliev.managers.reminder_manager.instances.ReminderInstace;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/reminders.json";

    public static List<ReminderInstace> loadSettings(){
        LogUtils.info("Trying to load settings");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<List<ReminderInstace>>(){});
            LogUtils.info("Settings setter loaded");
            return output;
        }
        catch (Exception ex){
            return null;
        }
    }

    public static Exception saveSettings(List<ReminderInstace> data){
        LogUtils.info("Trying to save reminders");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Reminders saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
