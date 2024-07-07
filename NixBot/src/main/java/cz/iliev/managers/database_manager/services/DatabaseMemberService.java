package cz.iliev.managers.database_manager.services;

import cz.iliev.managers.database_manager.entities.Member;

import java.sql.PreparedStatement;
import java.util.function.Consumer;

public class DatabaseMemberService {

    public static void addMember(Member member, Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("INSERT INTO Member(ID,Nickname,CreateDate) VALUES (?,?,?)");
            statement.setLong(1, member.getId());
            statement.setString(2, member.getNickname());
            statement.setLong(3, member.getCreateDate());
            statement.execute();
            callback.accept(true);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

    public static void getMember(long memberId, Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("SELECT * FROM Member WHERE ID=?");
            statement.setLong(1, memberId);
            var reader = statement.executeQuery();

            if(!reader.next()){
                reader.close();
                callback.accept(null);
                return;
            }

            Member member = new Member(
                    reader.getLong(1),
                    reader.getString(2),
                    reader.getLong(3)
            );

            long verificateDate = reader.getLong(4);
            if(reader.wasNull())
                member.setVerificateDate(-1);
            else
                member.setVerificateDate(verificateDate);

            String email = reader.getString(5);
            if(reader.wasNull())
                member.setEmail(null);
            else
                member.setEmail(email);

            reader.close();

            callback.accept(member);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

    public static void finishVerification(long memberId, Consumer<Object> callback){
        try{
            PreparedStatement statement = DatabaseCommonService.getConnection().prepareStatement("UPDATE Member SET VerificateDate=? WHERE ID=?");
            statement.setLong(1, System.currentTimeMillis());
            statement.setLong(2, memberId);
            statement.execute();
            callback.accept(true);
        }
        catch (Exception ex){
            callback.accept(ex);
        }
    }

}
