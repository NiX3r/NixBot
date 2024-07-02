package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.database_manager.entities.Member;
import cz.iliev.managers.database_manager.entities.Verification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.function.Consumer;

public class DatabaseMemberService {

    public static void addMember(Member member, Consumer<Exception> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO Member(ID,Nickname,CreateDate) VALUES (?,?,?)");
            statement.setLong(1, member.getId());
            statement.setString(2, member.getNickname());
            statement.setLong(3, member.getCreateDate());
            statement.execute();
            callback.accept(null);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

    public static void addVerification(Member member, Consumer<Exception> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO Verification(MemberID,Code,CreateDate,VerificateDate,Verificated) VALUES (?,?,?,?,?)");
            statement.setLong(1, member.getId());
            statement.setString(2, member.getVerification().getCode());
            statement.setLong(3, member.getVerification().getCreateDate());
            statement.execute();
            callback.accept(null);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

    public static void finishVerification(long memberId, Consumer<Exception> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("UPDATE Verification SET VerificateDate=?, Verificated=? WHERE MemberID=?");
            statement.setLong(1, System.currentTimeMillis());
            statement.setBoolean(2, true);
            statement.setLong(3, memberId);
            statement.execute();
            callback.accept(null);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

}
