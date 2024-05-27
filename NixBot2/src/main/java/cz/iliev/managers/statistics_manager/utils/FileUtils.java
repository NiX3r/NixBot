package cz.iliev.managers.statistics_manager.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.role_manager.instances.RoleSetterInstance;
import cz.iliev.managers.statistics_manager.instances.*;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/statistics.json";
    private static final String ARCHIVE_PATH = "./archive/statistics/";

    public static StatisticsInstance loadStatistics(){
        LogUtils.info("Trying to load role setter");
        try {
            String json = new String(Files.readAllBytes(Paths.get(PATH)));
            var output = new Gson().fromJson(json, StatisticsInstance.class);
            LogUtils.info("Role setter messages loaded");
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

    public static Exception archiveStatistics(StatisticsInstance data){
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
