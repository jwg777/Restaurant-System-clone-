package database_cafe;

import java.sql.*;
import java.util.Scanner;

public class database {

  public static void main(String[] args) throws SQLException {
    
    String user = "";
    String password = ""; 
    
    System.out.println("Please enter your username");
    Scanner scan = new Scanner(System.in);
    user = scan.nextLine();
    System.out.println("Please enter your password");
    password = scan.nextLine();
    scan.close();
     
    String database = "//teachdb.cs.rhul.ac.uk/CS2855/";
    
    Connection connection = connectToDatabase(user, password, database); 
    
    if(connection != null) {
      System.out.println("Database is activated!");
    } else {
      System.out.println("Error occurred, access denied!");
      return;
    }
    createTable(connection, "Orders(orderID int primary key, price float, orderTimes time)");
    createTable(connection, "Customers(customerID varchar(20) primary key, password varchar(20), tableNumber int, orderID int references Orders(orderID))");
    createTable(connection, "Waiters(waitersID varchar(20) primary key, password varchar(20), tableNumber int)");
    createTable(connection, "Kitchen(kitchenID varchar(20) primary key, password varchar(20), orderID int references Orders(orderID))");
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
    return connection;
  }
 
  public static void createTable(Connection connection, String tableName) {
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

}
