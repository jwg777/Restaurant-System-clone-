

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
    return database.getDishList();
  }

  public ArrayList<Order> getOrders() throws SQLException {
    return database.getOrderList();
  }
}
