package cz.iliev.managers.user_verification_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/verification.json";

    public static HashMap<Long, String> loadVerification(){
        LogUtils.info("Trying to load verification");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, String>>(){});
            LogUtils.info("Verification loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new HashMap<Long, String>();
        }
    }

    public static Exception saveVerification(HashMap<Long, String> data){
        LogUtils.info("Trying to save verification");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Verification saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
