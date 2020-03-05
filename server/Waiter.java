

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import consumable.Consumable;
import order.Order;

public class Waiter {

  Database database = Database.getInstance();

  private static Waiter instance = null;

  private static ArrayList<Consumable> menuList = new ArrayList<>();

  private static ArrayList<Order> orderList = new ArrayList<>();

  private Waiter() {
    try {
      menuList = getMenu();
      orderList = getOrders();
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

  public ArrayList<Order> getOrderList() {
    return orderList;
  }

  public ArrayList<Consumable> getMenu() throws SQLException {
    return database.getDishes();
  }

  public ArrayList<Order> getOrders() throws SQLException {
    ResultSet rs = database.select("SELECT * FROM Orders");
    ArrayList<Order> list = new ArrayList<>();
    while (rs.next()) {
      int orderID = rs.getInt("order_id");
      int custID = rs.getInt("cust_id");
      int dishID = rs.getInt("dish_id");
      String timeStamp = (rs.getTimestamp("order_time")).toString();
      String status = rs.getString("status");
      list.add(new Order(orderID, custID, dishID, timeStamp, status));
    }
    return list;
  }
}
