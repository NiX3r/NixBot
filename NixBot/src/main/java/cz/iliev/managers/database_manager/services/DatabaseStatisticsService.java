package cz.iliev.managers.database_manager.services;

import com.google.common.hash.Hashing;
import cz.iliev.managers.database_manager.entities.Member;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.function.Consumer;

public class DatabaseStatisticsService {

    public static void updateStatistics(String statisticsCategoryName, long memberID, String nonMemberKey, String currentDate, long value, Consumer<Object> callback){

        String id = translateToKey(statisticsCategoryName, memberID == -1 ? null : String.valueOf(memberID), nonMemberKey, currentDate);

        try{

            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT Value FROM Statistics WHERE ID=?");
            statement.setString(1, id);
            var reader = statement.executeQuery();
            boolean exists = reader.next();
            long newValue = exists ? reader.getLong(1) + value : value;
            reader.close();

            if(exists){
                statement = DatabaseCommonService.getConnection().prepareStatement("UPDATE Statistics SET Value=? WHERE ID=?");
                statement.setLong(1, newValue);
                statement.setString(2, id);
                statement.execute();
            }
            else {
                statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO Statistics(ID,MemberID,NonMemberKey,StatisticsCategoryName,CurrentDate,Value) Values(?,?,?,?,?,?)");
                statement.setString(1, id);
                if(memberID == -1)
                    statement.setNull(2, Types.BIGINT);
                else
                    statement.setLong(2, memberID);
                statement.setString(3, nonMemberKey);
                statement.setString(4, statisticsCategoryName);
                statement.setString(5, currentDate);
                statement.setLong(6, newValue);
                statement.execute();
            }

            callback.accept(true);

        }
        catch (Exception ex){
            callback.accept(ex);
        }

    }

    public static void getStatistics(String statisticsCategoryName, String memberID, String nonMemberKey, String currentDate, Consumer<Object> callback){

        String id = translateToKey(statisticsCategoryName, memberID, nonMemberKey, currentDate);

        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT Value FROM Statistics WHERE ID=?");
            statement.setString(1, id);
            var reader = statement.executeQuery();
            if(!reader.next()){
                reader.close();
                callback.accept(null);
            }
            long value = reader.getLong(1);
            reader.close();
            callback.accept(value);
        }
        catch (Exception ex){
            callback.accept(ex);
        }

    }

    private static String translateToKey(String statisticsCategoryName, String memberID, String nonMemberKey, String currentDate){
        String hash = statisticsCategoryName + (memberID == null ? "" : memberID) + (nonMemberKey == null ? "" : nonMemberKey) + (currentDate == null ? "" : currentDate);
        return Hashing.sha256()
                .hashString(hash, StandardCharsets.UTF_8)
                .toString();
    }

}
