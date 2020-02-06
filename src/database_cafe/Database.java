package database_cafe;

import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Database {
    
    Connection connection;
    
    public Database() {
    //for testing with set login credentials
      String user = "";
      String password = "";
      
      /*String user = "", password = "";
      System.out.println("Please enter your username");
      Scanner scan = new Scanner(System.in);
      user = scan.nextLine();
      System.out.println("Please enter your password");
      password = scan.nextLine();
      scan.close();*/
      
      //tunneling
      String database = "//localhost/CS2855/";
      
      //noMachine
      //String database = "////teachdb.cs.rhul.ac.uk/CS2855/";
      
      connection = connectToDatabase(user, password, database);
    }

    
    //temporary method
    public void importFile(String file0) {
        File inputFile = new File(file0);
        String line = "";
        BufferedReader br;
        try {
              br = new BufferedReader(new FileReader(inputFile));
              line = br.readLine();
              while(line != null) {
                String[] splitRead = line.split(">");
                line = "'";
                for(int i = 0; i < splitRead.length; i++) {
                  if(i == 0) {
                    line += splitRead[i];
                  } else {
                    line += "', '" + splitRead[i];
                  }
                }
                line += "'";
                insertIntoTable("Menu", "", line);
                line = br.readLine();
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
    }

  public static Connection connectToDatabase(String user, String password, String database) {
    System.out.println("~~~~~~~~~~~~~~~ PostgreSQL___JDBC Connection Testing ~~~~~~~~~~~~~~~");
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:postgresql:" + database + user, user, password);
    } catch (SQLException e) {
      System.out.println("Connection failed! Check output console");
      e.printStackTrace();
    }
    if(connection != null) {
      System.out.println("Database is activated!");
    } else {
      System.out.println("Failed to make connection!");
      return null;
    }
    return connection;
  }
 
  public void createTable(String tableName) {
    Statement st = null;
    String table = "";
    for (int i = 0; i < tableName.length(); i++) {
        if (tableName.charAt(i) == '(') {
            table = tableName.substring(0, i);
            break;
        }
    }
    try {
      st = connection.createStatement();
      st.execute("DROP TABLE IF EXISTS " + table + " CASCADE");
      st.execute("CREATE TABLE " + tableName);
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void insertIntoTable(String tableName, String attributes, String values) {
    Statement st = null;
    try {
      st = connection.createStatement();
      if(attributes == "") {
        st.execute("INSERT INTO " + tableName + " VALUES (" + values + ");");
      } else {
        st.execute("INSERT INTO " + tableName + "(" + attributes + ") VALUES (" + values + ");");
      }
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public ResultSet select(String query) {
    Statement st = null;
    ResultSet rs = null;
    try {
      st = connection.createStatement();
      rs = st.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }
}
