package cz.nix3r.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.nix3r.enums.LogType;
import cz.nix3r.instances.StreamingPlatformInstance;
import cz.nix3r.utils.LogSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StreamingPlatformReminderManager {

    private List<StreamingPlatformInstance> platforms = new ArrayList<StreamingPlatformInstance>();
    private final String PLATFORMS_FILE_PATH = "./platforms.json";
    public final String[] RECEIVER_DISCORD_ID_ARRAY = new String[]{"397714589548019722", "901953892957446185"};

    public void loadPlatforms(){

        File platformFile = new File(PLATFORMS_FILE_PATH);

        if(platformFile.exists()){

            LogSystem.log(LogType.INFO, "Platforms file exists. Trying to load into cache");

            try {
                String json = new String(Files.readAllBytes(Paths.get(PLATFORMS_FILE_PATH)));
                platforms = new ArrayList<StreamingPlatformInstance>(Arrays.asList(new Gson().fromJson(json, StreamingPlatformInstance[].class)));
                LogSystem.log(LogType.INFO, "Successfully loaded into cache. " + platforms.size() + " platforms loaded");
            } catch (IOException e) {
                platformFile = null;
                createPlatformsFileAndLoadDefaultsIntoCache();
            }

        }
        else{
            LogSystem.log(LogType.WARNING, "Platforms does not file exists. Creating from defaults and load into cache");
            platformFile = null;
            createPlatformsFileAndLoadDefaultsIntoCache();
        }

    }

    private void createPlatformsFileAndLoadDefaultsIntoCache(){

        StreamingPlatformInstance platform = new StreamingPlatformInstance("Netflix", 0, new String[]{"Daniel", "Jirka", "Anca"});
        platforms.add(platform);
        platform = new StreamingPlatformInstance("Disney", 0, new String[]{"Daniel", "Andrea", "Vojta", "Hedvika", "Andrea", "Jirka"});
        platforms.add(platform);
        platform = new StreamingPlatformInstance("HBO", 0, new String[]{"Kamoska Anci", "Hedvika", "Jirka"});
        platforms.add(platform);

        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(
            PLATFORMS_FILE_PATH));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(platforms);
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Successfully created platforms file and loaded into cache");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void incrementIndexes(){
        for(StreamingPlatformInstance platform : platforms)
            platform.incrementCurrentIndex();
    }

    public void saveCachePlatformsInFile(){
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(
                    PLATFORMS_FILE_PATH));
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(platforms);
            f_writer.write(json);
            f_writer.flush();
            f_writer.close();
            LogSystem.log(LogType.INFO, "Successfully saved platforms into file");
        } catch (IOException e) {
            LogSystem.log(LogType.ERROR, "Error while saving into cache. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /// Return hash map
    /// Key = platform name | Name = current month payer name
    public HashMap<String, String> getPlatformPayersHashMap(){
        HashMap<String, String> output = new HashMap<>();

        for(StreamingPlatformInstance platform : platforms)
            output.put(platform.getName(), platform.getList()[platform.getCurrentIndex()]);

        return output;
    }

}
