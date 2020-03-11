package order;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderMap.
 */
public final class OrderMap {

  /** The instance. */
  private static OrderMap instance = null;

  /** The orders. */
  private ObservableMap<String, ObservableList<Order>> orders = FXCollections.observableHashMap();

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
  public ObservableMap<String, ObservableList<Order>> getOrders() {
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
  public void put(Order request) {
    String status = request.getStatus();
    ObservableList<Order> list;
    if (!orders.containsKey(status)) {
      list = FXCollections.observableArrayList();
      orders.put(status, list);
    } else {
      list = orders.get(status);
    }
    list.add(request);
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
  public ObservableList<Order> get(String key) {
    return orders.get(key);
  }

  public void remove(Order order) {
    String status = order.getStatus();
    ObservableList<Order> tempList = orders.get(status);
    for (Order tempOrder : tempList) {
      if (tempOrder.equals(order)) {
        tempList.remove(tempOrder);
        break;
      }
    }
    orders.put(status, tempList);
  }
}
