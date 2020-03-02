package oaxacaServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
  
  private static Database instance = null;
  
  private static Connection connection = null;
  /**
   * Constructor for class. Connects to the database.
   */
  private Database() {
    String user = "oaxaca";
    String database = "//localhost:5432/";
    connection = connectToDatabase(user, database);
  }
  
  public static Database getInstance() {
    if(instance == null) {
      instance = new Database();
    }
    return instance;
  }
  
  /**
   * This method connects to the database using username, password, and the address of the database
   * 
   * @param user
   * @param database
   * @return
   */
  public static Connection connectToDatabase(String user,String database) {
    try {
      connection =
          DriverManager.getConnection("jdbc:postgresql:" + database + user);
    } catch (SQLException e) {
      System.out.println("Failed to connect to Database");
      System.exit(0);
    }
    if (connection == null) {
      System.out.println("Failed to connect to Database");
      System.exit(0);
    }
    return connection;
  }
  
  /**
   * This method is for quering the database and stores the result in a resultSet.
   * 
   * @param query
   * @return the result set
   */
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

  public void execute(String query) {
    Statement st = null;
    try {
      st = connection.createStatement();
      st.execute(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(String update) {
    Statement st = null;
    try {
      st = connection.createStatement();
      st.executeUpdate(update);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
}
