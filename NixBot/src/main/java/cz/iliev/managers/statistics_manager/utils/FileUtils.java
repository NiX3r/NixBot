package cz.iliev.managers.statistics_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.managers.statistics_manager.instances.*;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String CURRENT_STATS_PATH = "./data/statistics.json";
    private static final String ARCHIVE_STATS_PATH = "./archive/statistics/{year}/{month}/";
    private static final String USER_LAST_ACTIVITY_PATH = "./data/users_last_activity.json";

    public static HashMap<Long, String> loadUsersLastActivity(){
        LogUtils.info("Trying to load users last activity");
        try {
            String json = new String(Files.readAllBytes(Paths.get(USER_LAST_ACTIVITY_PATH)));
            var output = new Gson().fromJson(json, new TypeToken<HashMap<Long, String>>(){});
            LogUtils.info("Users last activity loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new HashMap<Long, String>();
        }
    }

    public static Exception saveUsersLastActivity(HashMap<Long, String> data){
        LogUtils.info("Trying to save users last activity");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(USER_LAST_ACTIVITY_PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Users last activity saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

    public static StatisticsInstance loadStatistics(){
        LogUtils.info("Trying to load statistics");
        try {
            String json = new String(Files.readAllBytes(Paths.get(CURRENT_STATS_PATH)));
            var output = new Gson().fromJson(json, StatisticsInstance.class);
            LogUtils.info("Statistics loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new StatisticsInstance(
                    System.currentTimeMillis(),
                    0,0,0,0,0,0,
                    new HashMap<>(), new HashMap<>(), new HashMap<>(), 0, 0,
                    0,0,0,0,0,0,0,0,
                    new HashMap<>(), new HashMap<>(), new HashMap<>(), 0, 0,0,0,0,
                    new HashMap<>(), new HashMap<>(), 0,0,0,
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<>(), new HashMap<>(),new HashMap<>(),new HashMap<>(),
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<Long, HashMap<String, Long>>(),new HashMap<Long, HashMap<String, Long>>(),
                    new HashMap<>(), new HashMap<>(),new HashMap<>(),
                    new HashMap<>(), new HashMap<>(),new HashMap<>()
            );
        }
    }

    public static Exception saveStatistics(StatisticsInstance data){
        LogUtils.info("Trying to save statistics");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(CURRENT_STATS_PATH));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Statistics messages saved");
            archiveStatistics(data);
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

    public static Exception archiveStatistics(StatisticsInstance data){
        LogUtils.info("Trying to archive statistics");
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(data.currentTime);
            int year = calendar.get(Calendar.YEAR);
            int month = (calendar.get(Calendar.MONTH) + 1);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String path = ARCHIVE_STATS_PATH.replace("{year}", String.valueOf(year)).replace("{month}", String.valueOf(month));
            new File(path).mkdirs();
            path = path + year + "-" + month + "-" + day + "-statistics.json";
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(path));
            f_writer.write(new GsonBuilder().create().toJson(data));
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Statistics archived on '" + path + "'");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }
}
