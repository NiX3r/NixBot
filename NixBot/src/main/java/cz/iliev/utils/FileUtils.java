package cz.iliev.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.instances.SettingsInstance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./settings.json";

    public static void createDefaultFolders(){
        LogUtils.info("Trying to create default folders");
        new File("./data").mkdirs();
        new File("./archive/statistics").mkdirs();
        new File("./archive/tickets").mkdirs();
        new File("./logs").mkdirs();
        new File("./temp").mkdirs();

        LogUtils.info("Default folders created (or already existed)");
    }

    public static void deleteTempFiles(){
        LogUtils.info("Trying to delete temp files");
        for(var file : new File("./temp").listFiles()) {
            if (!file.isDirectory()){
                LogUtils.warning("Deleting '" + file.getName() + "' temp file");
                file.delete();
            }
        }
        LogUtils.info("Temp file deleted");
    }

    public static SettingsInstance loadSettings(){
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

    public static Exception saveSettings(SettingsInstance data){
        LogUtils.info("Trying to save settings");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Settings saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
