package database_cafe;

import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class database {

    public static void main(String[] argv) throws SQLException {
        
        Connection connection = connectToDatabase("avnadmin", "xcz21ixoaa93iixq", "");
        if (connection != null) {
            System.out.println("Connection made!");
        } else {
            System.out.println("failed to make connection");
            return;
        }
       
    }
    
    public static Connection connectToDatabase(String user, String password, String database) {
        System.out.println("-------- PostgreSQL JDBC ------------");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql:" + database + user, 
                            user, password);
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
            }
            return connection;
    }
    
    public static void createTable(Connection connection, String tableDes ) {
        Statement st = null;
        String tableName = "";
        for (int i = 0; i < tableDes.length(); i++) {
            if (tableDes.charAt(i) == '(') {
                tableName = tableDes.substring(0, i);
                break;
            }
        }
        try {
            st = connection.createStatement();
            st.execute("DROP TABLE IF EXISTS " + tableName + " CASCADE");
            st.execute("CREATE TABLE " + tableDes);
            st.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
