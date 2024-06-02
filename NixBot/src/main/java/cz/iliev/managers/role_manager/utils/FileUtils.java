package cz.iliev.managers.role_manager.utils;

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
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/role_setter.json";

    public static List<RoleSetterInstance> loadRoleSetter(){
        LogUtils.info("Trying to load role setter");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, new TypeToken<List<RoleSetterInstance>>(){});
            LogUtils.info("Role setter messages loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new ArrayList<RoleSetterInstance>();
        }
    }

    public static Exception saveRoleSetter(List<RoleSetterInstance> data){
        LogUtils.info("Trying to save role setter");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Role setter messages saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

}
