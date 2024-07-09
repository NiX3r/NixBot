package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.ban_list_manager.enums.BanType;
import cz.iliev.managers.database_manager.entities.Member;

import java.sql.PreparedStatement;
import java.util.function.Consumer;

public class DatabaseBanService {

    public static void addPunishment(long memberId, long adminId, BanType type, long createDate, long duration, String reason, Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO BanList(MemberID,AdminID,Type,CreateDate,Duration,Reason) VALUES (?,?,?,?,?,?)");
            statement.setLong(1, memberId);
            statement.setLong(2, adminId);
            statement.setString(3, type.toString());
            statement.setLong(4, createDate);
            statement.setLong(5, duration);
            statement.setString(6, reason);
            statement.execute();
            callback.accept(true);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

}
