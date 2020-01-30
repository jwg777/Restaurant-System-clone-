package database_cafe;
/** This class is used to create the connection to the database and the tables.
 * 
 * @author : Team project group 22
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Database {

  static Connection connection = null;

  /** Constructor for the database object, creating and filling in initial values.
   * 
   * @throws SQLException Thrown if  a problem occurs with communicating with the database server.
   */
  public Database() throws SQLException {
    
    String user = "";
    System.out.println("Please enter your username");
    Scanner scan = new Scanner(System.in);
    user = scan.nextLine();
    String password = "";
    System.out.println("Please enter your password");
    password = scan.nextLine();
    scan.close();
 
    String database = "//localhost/CS2855/"; 
    //change localhost to teachdb.cs.rhul.ac.uk if using nomachine.
   
    connection = connectToDatabase(user, password, database);
   
    if (connection != null) {
      System.out.println("Database is activated!");
    } else {
      System.out.println("Error occurred, access denied!");
      return;
    }
    createTable("Customers(cust_id int primary key, "
        + "password varchar(20), "
        + "tableNumber int)");
    createTable("Menu(dish varChar(100) primary key, "
        + "price float, "
        + "info varchar(300))");
    createTable("Orders(orderID int primary key, "
        + "cust_id int, "
        + "total_price float, "
        + "orderTime Timestamp, "
        + "dish varchar(100), "
        + "status varchar(100), "
        + "foreign key (cust_id) references Customers(cust_id) on delete cascade,"
        + " foreign key (dish) references Menu(dish) on delete cascade)");
    createTable("Staff(staff_id int primary key, "
        + "password varchar(100), "
        + "role varchar(20))");
    createTable("Issues(issue_id int primary key, "
        + "issue_note varchar(200), "
        + "cust_id int, foreign key (cust_id) references Customers(cust_id) on delete cascade)");
    File menuFile = new File("Menu");
    String line = "";
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(menuFile));
      line = br.readLine();
      while (line != null) {
        String[] splitRead = line.split(">");
        line = "'";
        for (int i = 0; i < splitRead.length; i++) {
          if (i == 0) {
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

  /** This is the method used to create a connection to the database.
   * 
   * @param user this is user name needed to access the database server
   * @param password this is the password needed to access the database server
   * @param database this is the directory of the database
   * @return returns the connection to the database
   * 
   */
  public static Connection connectToDatabase(String user, String password, String database) {
    System.out.println("~~~~~~~~~~~~~~~ PostgreSQL___JDBC Connection Testing ~~~~~~~~~~~~~~~");
    try {
      connection = 
        DriverManager.getConnection("jdbc:postgresql:" + database + user, user, password);
    } catch (SQLException e) {
      System.out.println("Connection failed! Check output console");
      e.printStackTrace();
    }
    return connection;
  }
 
  /** This is the method used to create a table in the database.
   * 
   * @param tableName given for the new table to be created
   * 
   */
  public static void createTable(String tableName) {
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
  
  /** This method is used to insert data into an existing table.
   * 
   * @param tableName for the table that is having data put inside it
   * @param attributes optional, if only specific fields want to be filled in
   * @param values this is the data to be input in string format
   * 
   */
  public static void insertIntoTable(String tableName, String attributes, String values) {
    Statement st = null;
    try {
      st = connection.createStatement();
      if (attributes == "") {
        st.execute("INSERT INTO " + tableName + " VALUES (" + values + ");");
      } else {
        st.execute("INSERT INTO " + tableName + "(" + attributes + ") VALUES (" + values + ");");
      }
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /** This is used when executing a query to store the result.
   * 
   * @param query is the query to be executed
   * @return will return the result set of executing the query
   * 
   */
  public static ResultSet executeQuery(String query) {
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
  
  /** Method to get connection to the database.
   * 
   * @return will return the connection to the database
   */
  public Connection getConnection() {
    return connection;
  }

}