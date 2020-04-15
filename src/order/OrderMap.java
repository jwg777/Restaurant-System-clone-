package order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javafx.util.converter.LocalDateTimeStringConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderMap.
 */
public final class OrderMap {

  /** The instance. */
  private static OrderMap instance = null;

  /** The orders. */
  private HashMap<String, ArrayList<Order>> orders = new HashMap<>();

  /**
   * Instantiates a new order map.
   */
  private OrderMap() {}

  /**
   * Gets the single instance of OrderMap.
   *
   * @return single instance of OrderMap
   */
  public static OrderMap getInstance() {
    if (instance == null) {
      instance = new OrderMap();
    }
    return instance;
  }

  /**
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  public boolean isEmpty() {
    return orders.isEmpty();
  }

  /**
   * Gets the orders.
   *
   * @return the orders
   */
  public HashMap<String, ArrayList<Order>> getOrders() {
    return this.orders;
  }

  /**
   * Clear.
   */
  public void clear() {
    this.orders.clear();
  }

  /**
   * Put.
   *
   * @param tab the tab
   * @param request the request
   */
  public void put(String tab, Order request) {
    ArrayList<Order> tempList = new ArrayList<>();
    if (orders.containsKey(tab)) {
      for (Order order : orders.get(tab)) {
        tempList.add(order);
      }
    }
    tempList.add(request);
    orders.put(tab, tempList);
  }

  /**
   * Key array.
   *
   * @return the array list
   */
  public ArrayList<String> keyArray() {
    ArrayList<String> tempList = new ArrayList<>();
    for (String string : orders.keySet()) {
      tempList.add(string);
    }
    return tempList;
  }

  /**
   * Gets the.
   *
   * @param key the key
   * @return the array list
   */
  public ArrayList<Order> get(String key) {
    return orders.get(key);
  }
}