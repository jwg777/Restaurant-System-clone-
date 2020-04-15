package database_cafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Class that connects to the postgresql server database and contains various methods for
 * interacting with this database.
 */
public class Database {
  /** Field for storing the connection */
  @FXML
  private AnchorPane revScroll;

  Connection connection;

  /**
   * Constructor for class. Connects to the database.
   */
  public Database() {

    // for testing with set login credentials
    String user = "oxaca";
    String pass = "m3xic0"; 

    // tunneling
    String database = "//localhost/";

    connection = connectToDatabase(user, pass, database);

  }

  /**
   * This method is used as a temporary measure. It allows text files to load data into the
   * database.
   * 
   * @param file0
   * @param table
   */
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
   * This method connects to the database using username, password, and the address of the database
   * 
   * @param user
   * @param password
   * @param database
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
   * This method creates a table in the database. It will drop a table if it already exists. (This
   * means currently every run of the program will restart the entire databse).
   * 
   * @param tableName
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
   * This method inserts a row into a table.
   * 
   * @param tableName
   * @param attributes This value can be empty and is only used in various circumstances.
   * @param values
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

  /*
   * public String getReviews() {
   * 
   * String query = null; try { Statement st1 = connection.createStatement(); ResultSet rs1 =
   * st1.executeQuery("SELECT * FROM Reviews WHERE star_rating = 5"); while (rs1.next()) { query =
   * rs1.getString("name") +" "+ rs1.getString("star_rating") +" "+ rs1.getString("review");
   * //this.revScroll.getChildren().add(initialiseLabel(query, 400, 50)); System.out.println(query);
   * } } catch (Exception e) { e.printStackTrace(); } return query; }
   * 
   * private Label initialiseLabel(String name, double width, double height) { Label label = new
   * Label(name); label.setPrefSize(width, height); return label; }
   */

}
