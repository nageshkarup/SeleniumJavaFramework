package nk.selenium.utils;

import nk.selenium.constants.Constants;

import java.sql.*;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://" + PropertyFileUtils.getValue("DB_HOST") + ":3306/" + PropertyFileUtils.getValue("DB_NAME");
    private static final String USER = PropertyFileUtils.getValue("DB_USERNAME");
    private static final String PASSWORD =PropertyFileUtils.getValue("DB_PASSWORD");
    private static Connection connection;

    private DatabaseConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        }catch (Exception e){
            throw new RuntimeException("Error connecting the database "+e.getMessage());
        }
    }

    public static Connection getConnection()  {
        if(connection == null)
            new DatabaseConnection();
        return connection;
    }

    public static void closeConnection() {
        try {
            if(connection != null)
                connection.close();
        }catch (Exception e){
            throw new RuntimeException("Error while closing the connection of database "+e.getMessage());
        }
    }


}
