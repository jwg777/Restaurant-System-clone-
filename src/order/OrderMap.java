package order;

import java.util.ArrayList;
import java.util.HashMap;

public final class OrderMap {

  private static OrderMap instance = null;
  private HashMap<String, ArrayList<Order>> orders = new HashMap<>();

  private OrderMap() {}

  public static OrderMap getInstance() {
    if (instance == null) {
      instance = new OrderMap();
    }
    return instance;
  }

  public boolean isEmpty() {
    return orders.isEmpty();
  }

  public HashMap<String, ArrayList<Order>> getOrders() {
    return this.orders;
  }

  public void clear() {
    this.orders.clear();
  }

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

  public ArrayList<String> keyArray() {
    ArrayList<String> tempList = new ArrayList<>();
    for (String string : orders.keySet()) {
      tempList.add(string);
    }
    return tempList;
  }

  public ArrayList<Order> get(String key) {
    return orders.get(key);
  }



}
