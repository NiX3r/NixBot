package cz.iliev.managers.wordle_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.wordle_manager.instances.WordleGameInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileUtils {

    private static final String PATH = "./data/wordle_games.json";

    public static HashMap<Long, WordleGameInstance> loadGames(){
        LogUtils.info("Trying to load wordle games");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, WordleGameInstance>>(){});
            LogUtils.info("Wordle games loaded");
            return output;
        }
        catch (Exception ex){
            return new HashMap<Long, WordleGameInstance>();
        }
    }

    public static Exception saveGames(HashMap<Long, WordleGameInstance> data){
        LogUtils.info("Trying to save wordle games");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Wordle games saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
