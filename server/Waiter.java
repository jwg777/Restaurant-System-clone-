

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import consumable.Consumable;
import order.Order;

public class Waiter {

  Database database = Database.getInstance();

  private static Waiter instance = null;

  private static ArrayList<Consumable> menuList = new ArrayList<>();

  private Waiter() {
    try {
      menuList = getMenu();
    } catch (SQLException e) {
    }
  }

  public static Waiter getInstance() {
    if (instance == null) {
      instance = new Waiter();
    }
    return instance;
  }

  public ArrayList<Consumable> getMenuList() {
    return menuList;
  }

  public ArrayList<Consumable> getMenu() throws SQLException {
    ResultSet rs = database.select("SELECT * FROM Menu");
    ArrayList<Consumable> list = new ArrayList<>();
    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");
      list.add(new Consumable(type, itemName, itemPrice, calories, allergens));
    }
    return list;
  }

  public ArrayList<Order> getOrders() throws SQLException {
    ResultSet rs = database.select("SELECT * FROM Orders");
    ArrayList<Order> list = new ArrayList<>();
    while (rs.next()) {
      int orderID = rs.getInt("orderID");
      int custID = rs.getInt("cust_ID");
      float totalPrice = rs.getFloat("total_price");
      String timeStamp = (rs.getTimestamp("orderTime")).toString();
      String dish = rs.getString("dish");
      String status = rs.getString("status");
      list.add(new Order(orderID, custID, totalPrice, timeStamp, status, dish));
    }
    return list;
  }
}
