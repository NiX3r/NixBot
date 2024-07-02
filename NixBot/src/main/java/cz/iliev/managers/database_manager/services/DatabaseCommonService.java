package cz.iliev.managers.database_manager.services;

import cz.iliev.utils.CommonUtils;

import java.sql.*;
import java.util.function.Consumer;

public class DatabaseCommonService {

    private static Connection connection;

    public static void setupConnection(Consumer<Exception> callback){

        try {

            if(connection != null && !connection.isClosed())
                return;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + CommonUtils.settings.getDatabaseHost() + "/" + CommonUtils.settings.getDatabaseName(), CommonUtils.settings.getDatabaseUsername(), CommonUtils.settings.getDatabasePassword());

            callback.accept(null);

        }
        catch(Exception e) {
            callback.accept(e);
        }

    }

    public static Connection getConnection() {
        try{
            if(connection == null || connection.isClosed()){
                setupConnection(err -> {});
                if(connection == null || connection.isClosed()){
                    CommonUtils.throwException(new Exception("Database can't be connected"), true, false);
                    return null;
                }
            }
            return connection;
        }
        catch (Exception ex){
            CommonUtils.throwException(new Exception("Database can't be connected"), true, false);
            return null;
        }
    }

}
