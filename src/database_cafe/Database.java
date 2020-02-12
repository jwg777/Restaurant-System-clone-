package database_cafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: Auto-generated Javadoc
/**
 * The Class Database.
 */
public class Database {


  /** The connection. */
  Connection connection;

  /**
   * Instantiates a new database.
   */
  public Database() {

    // for testing with set login credentials
    String user = "zfac032";
    String password = "66092";

    /*
     * String user = "", password = ""; System.out.println("Please enter your username"); Scanner
     * scan = new Scanner(System.in); user = scan.nextLine();
     * System.out.println("Please enter your password"); password = scan.nextLine(); scan.close();
     */

    // tunneling
    String database = "//localhost/CS2855/";

    // noMachine
    // String database = "////teachdb.cs.rhul.ac.uk/CS2855/";

    connection = connectToDatabase(user, password, database);

  }


  /**
   * Import file.
   *
   * @param file0 the file 0
   * @param table the table
   */
  // temporary method
  public void importFile(String file0, String table) {
    File inputFile = new File(file0);
    String line = "";
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(inputFile));
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
        insertIntoTable(table, "", line);
        line = br.readLine();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  /**
   * Connect to database.
   *
   * @param user the user
   * @param password the password
   * @param database the database
   * @return the connection
   */
  public static Connection connectToDatabase(String user, String password, String database) {
    System.out.println("~~~~~~~~~~~~~~~ PostgreSQL___JDBC Connection Testing ~~~~~~~~~~~~~~~");
    Connection connection = null;
    try {
      connection =
          DriverManager.getConnection("jdbc:postgresql:" + database + user, user, password);
    } catch (SQLException e) {
      System.out.println("Connection failed! Check output console");
      e.printStackTrace();
    }
    if (connection != null) {
      System.out.println("Database is activated!");
    } else {
      System.out.println("Failed to make connection!");
      return null;
    }
    return connection;
  }

  /**
   * Creates the table.
   *
   * @param tableName the table name
   */
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

  /**
   * Insert into table.
   *
   * @param tableName the table name
   * @param attributes the attributes
   * @param values the values
   */
  public void insertIntoTable(String tableName, String attributes, String values) {
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

  /**
   * Select.
   *
   * @param query the query
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
}
