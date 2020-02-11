package order;

import java.util.ArrayList;
import java.util.HashMap;

public final class OrderMap {

  private static OrderMap instance = null;
  private HashMap<String, ArrayList<Order>> menu = new HashMap<>();

  private OrderMap() {}

  public static OrderMap getInstance() {
    if (instance == null) {
      instance = new OrderMap();
    }
    return instance;
  }

  public boolean isEmpty() {
    return menu.isEmpty();
  }

  public HashMap<String, ArrayList<Order>> getMenu() {
    return this.menu;
  }

  public void clear() {
    this.menu.clear();
  }

  public void put(String tab, Order request) {
    ArrayList<Order> tempList = new ArrayList<>();
    if (menu.containsKey(tab)) {
      for (Order order : menu.get(tab)) {
        tempList.add(order);
      }
    }
    tempList.add(request);
    menu.put(tab, tempList);
  }

  public ArrayList<String> keyArray() {
    ArrayList<String> tempList = new ArrayList<>();
    for (String string : menu.keySet()) {
      tempList.add(string);
    }
    return tempList;
  }

  public ArrayList<Order> get(String key) {
    return menu.get(key);
  }



}
