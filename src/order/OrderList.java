package order;

import java.util.ConcurrentModificationException;
import consumable.Consumable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderList {

  private static OrderList instance = null;

  private ObservableList<Order> orderList = FXCollections.observableArrayList();

  private OrderList() {}

  public static OrderList getInstance() {
    if (instance == null) {
      instance = new OrderList();
    }
    return instance;
  }

  public void add(Consumable consumable) {
    for (Order order : orderList) {
      if (consumable.getID() == order.getDishID()) {
        order.addQuantity();
        return;
      }
    }
    orderList.add(new Order(consumable));
  }

  public void minus(Consumable consumable) {
    try {
      for (Order order : orderList) {
        if (consumable.getID() == order.getDishID()) {
          if (!order.minusQuantity()) {
            orderList.remove(order);
          }
        }
      }
    } catch (ConcurrentModificationException e) {

    }
  }

  public ObservableList<Order> getOrderList() {
    return this.orderList;
  }
}
