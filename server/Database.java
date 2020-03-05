

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

  private ArrayList<Staff> staffList = new ArrayList<>();

  /**
   * Constructor for class. Connects to the database.
   */
  private Database() {
    String user = "oaxaca";
    String database = "//localhost:5432/";
    connection = connectToDatabase(user, database);
    update();
  }

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public ArrayList<Consumable> getDishList() {
    return dishList;
  }

  public ArrayList<Order> getOrderList() {
    return orderList;
  }

  public ArrayList<Staff> getStaffList() {
    return staffList;
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

  public void update() {
    updateDishes();
    updateOrders();
    updateStaffs();
  }

  public void addDish(Consumable consumable) {
    Statement st = null;
    try {
      st = connection.createStatement();
      String temp = "";
      String ingredients = "";
      for (String ingredient : consumable.getIngredients()) {
        temp += ingredient + "^";
      }
      // Removes last character of string.
      if (temp.length() > 0) {
        ingredients = temp.substring(0, temp.length() - 1);
      }
      st.execute(
          "INSERT INTO dishes(name, price, category, available, ingredients, calories) VALUES ('"
              + consumable.getName() + "', " + consumable.getPrice() + ", '" + consumable.getType()
              + "', " + consumable.getIsAvailable() + ", '" + ingredients + "', "
              + consumable.getCalories() + ");");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    updateDishes();
  }

  public void removeDish(Consumable consumable) {
    Statement st = null;
    try {
      st = connection.createStatement();
      st.execute("DELETE FROM dishes WHERE name = '" + consumable.getName() + "';");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    updateDishes();
  }
  
  public void addOrder(Order order) {
    
  }
  
  public void removeOrder(Order order) {
    
  }

  private void updateStaffs() {
    ResultSet rs = select("staffs");
    staffList.clear();
    try {
      while (rs.next()) {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        staffList.add(new Staff(ClientType.getType(role), username, password));
      }
    } catch (SQLException e) {
      System.out.println("Failed to get staff list from database");
    }
  }

  private void updateOrders() {
    ResultSet rs = select("orders");
    orderList.clear();
    try {
      while (rs.next()) {
        int orderID = rs.getInt("order_id");
        int custID = rs.getInt("cust_id");
        int dishID = rs.getInt("dish_id");
        String timeStamp = (rs.getTimestamp("order_time")).toString();
        String status = rs.getString("status");
        orderList.add(new Order(orderID, custID, dishID, timeStamp, status));
      }
    } catch (SQLException e) {
      System.out.println("Failed to get order list from database");
    }
  }

  public void updateDishes() {
    ResultSet rs = select("dishes");
    dishList.clear();
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
        dishList.add(new Consumable(id, category, name, price, calories, isAvailable, ingredients));
      }
    } catch (SQLException e) {
      System.out.println("Failed to get dish list from database");
    }
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
