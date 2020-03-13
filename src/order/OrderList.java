package order;

import java.util.ConcurrentModificationException;
import consumable.Consumable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderList {

  private static OrderList instance = null;

  private ObservableList<Order> orderList = FXCollections.observableArrayList();

  float totalPrice = 0;

  private OrderList() {}

  public static OrderList getInstance() {
    if (instance == null) {
      instance = new OrderList();
    }
    return instance;
  }

  public void add(Order order) {
    totalPrice += order.getPrice();
    order.addQuantity();
  }

  public void add(Consumable consumable) {
    totalPrice += consumable.getPrice();
    for (Order order : orderList) {
      if (consumable.getID() == order.getDishID()) {
        order.addQuantity();
        return;
      }
    }
    orderList.add(new Order(consumable));
  }

  public void minus(Order order) {
    try {
      if (!order.minusQuantity()) {
        orderList.remove(order);
        totalPrice -= order.getPrice();
      }
    } catch (ConcurrentModificationException e) {

    }
  }

  public void minus(Consumable consumable) {
    try {
      for (Order order : orderList) {
        if (consumable.getID() == order.getDishID()) {
          if (!order.minusQuantity()) {
            orderList.remove(order);
            totalPrice -= consumable.getPrice();
          }
        }
      }
    } catch (ConcurrentModificationException e) {

    }
  }

  public float getTotalPrice() {
    return totalPrice;
  }

  public ObservableList<Order> getOrderList() {
    return this.orderList;
  }
}
