package cz.iliev.managers.ban_list_manager.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.ban_list_manager.instances.MemberInstance;
import cz.iliev.managers.ban_list_manager.instances.PunishmentInstance;
import cz.iliev.utils.CommonUtils;
import cz.iliev.utils.LogUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    private static final String PATH = "./data/ban_list.csv";

    public static List<PunishmentInstance> loadActiveBans(){
        LogUtils.info("Trying to load active bans");
        try {
            List<PunishmentInstance> bans = new ArrayList<PunishmentInstance>();
            List<String> lines = Files.readAllLines(Paths.get(PATH));
            lines.remove(0);
            lines.forEach(line -> {
                var punishment = serializePunishment(line);
                if(punishment.getType() == BanType.BAN){
                    if((punishment.getDuration() == 0) || (System.currentTimeMillis() > (punishment.getTime() + punishment.getDuration()))){
                        bans.add(punishment);
                    }
                }
            });
            LogUtils.info("Active bans loaded");
            return bans;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new ArrayList<PunishmentInstance>();
        }
    }

    public static List<PunishmentInstance> loadPunishmentPage(int page){
        LogUtils.info("Trying to load punishment page");
        try {
            List<PunishmentInstance> bans = new ArrayList<PunishmentInstance>();
            List<String> lines = Files.readAllLines(Paths.get(PATH));
            lines.remove(0);
            Collections.reverse(lines);
            for(int i = 0; i < 10; i++){
                bans.add(serializePunishment(lines.get(i + page * 10)));
            }
            LogUtils.info("Punishment page loaded");
            return bans;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return new ArrayList<PunishmentInstance>();
        }
    }

    public static Exception savePunishment(PunishmentInstance ban){
        LogUtils.info("Trying to save ban");
        try{
            BufferedWriter f_writer
                    = new BufferedWriter(new FileWriter(PATH, true));

            String data = ban.getType() + ";" + ban.getMember().getMemberId() + ";" + ban.getMember().getMemberName() + String.join(",", ban.getMember().getRolesName() + ";" +
                    ban.getAdmin().getMemberId() + ";" + ban.getAdmin().getMemberName() + ";" + String.join(",", ban.getAdmin().getRolesName()) + ";" +
                    ban.getTime() + ";" + ban.getDuration() + ";" + ban.getDescription());

            f_writer.newLine();
            f_writer.write(data);
            f_writer.flush();
            f_writer.close();
            LogUtils.info("Ban saved");
            return null;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return ex;
        }
    }

    private static PunishmentInstance serializePunishment(String line){
        String[] data = line.split(";");
        try{
            var output = new PunishmentInstance(
                    BanType.valueOf(data[0]),
                    new MemberInstance(
                            Long.parseLong(data[1]),
                            data[2],
                            List.of(data[3].split(","))
                    ),
                    new MemberInstance(
                            Long.parseLong(data[4]),
                            data[5],
                            List.of(data[6].split(","))
                    ),
                    Long.parseLong(data[7]),
                    Long.parseLong(data[8]),
                    data[9]
            );
            return output;
        }
        catch (Exception ex){
            CommonUtils.throwException(ex);
            return null;
        }
    }

}
