package cz.iliev.managers.security_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.security_manager.instances.UserElo;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/users_elo.json";

    public static List<UserElo> loadUsersElo(){
        LogUtils.info("Trying to load users ELO");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<List<UserElo>>(){});
            LogUtils.info("Users ELO loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new ArrayList<UserElo>();
        }
    }

    public static Exception saveUsersElo(List<UserElo> data){
        LogUtils.info("Trying to save users ELO");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Users ELO saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
