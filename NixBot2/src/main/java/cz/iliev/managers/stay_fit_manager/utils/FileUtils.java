package cz.iliev.managers.stay_fit_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.stay_fit_manager.instances.MemberFitInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/hydrate/{id}.json";

    public static MemberFitInstance loadFits(long memberId){
        LogUtils.info("Trying to load members fit");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH.replace("{id}", String.valueOf(memberId)))));
            var output = new Gson().fromJson(json, MemberFitInstance.class);
            LogUtils.info("Members fit loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return null;
        }
    }

    public static Exception saveFits(MemberFitInstance data, long memberId){
        LogUtils.info("Trying to save members fit");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH.replace("{id}", String.valueOf(memberId))));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Members fit saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
