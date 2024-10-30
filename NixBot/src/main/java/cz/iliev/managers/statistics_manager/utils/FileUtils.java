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

    private static final String PATH = "./data/statistics.json";
    private static final String ARCHIVE_PATH = "./archive/statistics/{year}/{month}/";

    public static StatisticsInstance loadStatistics(){
        LogUtils.info("Trying to load statistics");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, StatisticsInstance.class);
            LogUtils.info("Statistics loaded");
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new StatisticsInstance(
                    System.currentTimeMillis(),
                    new CommandStatsInstance(
                            new HashMap<String, Long>(),
                            new HashMap<String, Long>()
                    ),
                    new ManagerStatsInstance(0,0,0, 0,0,0,0,0, 0),
                    new MemberStatsInstance(
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>()
                    ),
                    new ServerStatsInstance(0,0,0,0,0,0),
                    new TextChannelStatsInstance(
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            0, 0, 0
                    ),
                    new VoiceChannelStatsInstance(
                            new HashMap<Long, Long>(),
                            new HashMap<Long, Long>(),
                            0, 0, 0
                    ),
                    new HashMap<Long, Long>()
            );
        }
    }

    public static Exception saveStatistics(StatisticsInstance data){
        LogUtils.info("Trying to save statistics");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH));
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
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = (calendar.get(Calendar.MONTH) + 1);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String path = ARCHIVE_PATH.replace("{year}", String.valueOf(year)).replace("{month}", String.valueOf(month));
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
