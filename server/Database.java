

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import consumable.Consumable;
import order.Order;

public final class Database {

  private static Database instance = null;

  private static Connection connection = null;

  private ArrayList<Consumable> dishList = new ArrayList<>();

  private ArrayList<Order> orderList = new ArrayList<>();

  private HashMap<String, String> staffList = new HashMap<>();

  /**
   * Constructor for class. Connects to the database.
   */
  private Database() {
    String user = "oaxaca";
    String database = "//localhost:5432/";
    connection = connectToDatabase(user, database);
    getAll();
  }

  public static Database getInstance() {
    if (instance == null) {
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
  public static Connection connectToDatabase(String user, String database) {
    try {
      connection = DriverManager.getConnection("jdbc:postgresql:" + database + user, user, "");
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

  public void getAll() {
    dishList = getDishes();
    orderList = getOrders();
  }

  private ArrayList<Order> getOrders() {
    
    return null;
  }

  public ArrayList<Consumable> getDishes() {
    ResultSet rs = select("dishes");
    ArrayList<Consumable> temp = new ArrayList<>();
    try {
      while (rs.next()) {
        int id = rs.getInt("dish_id");
        String name = rs.getString("name");
        float price = rs.getFloat("price");
        String category = rs.getString("category");
        boolean isAvailable = rs.getBoolean("available");
        ArrayList<String> ingredients = new ArrayList<>();
        int calories = rs.getInt("calories");
        for (String ingredient : rs.getString("ingredients").split("^")) {
          ingredients.add(ingredient);
        }
        temp.add(new Consumable(id, category, name, price, calories, isAvailable, ingredients));
      }
    } catch (SQLException e) {
      System.out.println("Failed to get dish list from database");
    }
    return temp;
  }

  /**
   * This method is for quering the database and stores the result in a resultSet.
   * 
   * @param query
   * @return the result set
   */
  public ResultSet select(String table) {
    Statement st = null;
    ResultSet rs = null;
    try {
      st = connection.createStatement();
      rs = st.executeQuery("SELECT * FROM " + table);
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
