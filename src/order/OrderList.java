package order;

import java.util.ConcurrentModificationException;
import consumable.Consumable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderList {

  private static OrderList instance = null;

  private ObservableList<Order> orderList = FXCollections.observableArrayList();

  float totalPrice = 0;

  private PriceListener priceListener;

  private OrderList() {}

  public static OrderList getInstance() {
    if (instance == null) {
      instance = new OrderList();
    }
    return instance;
  }

  public void addPriceListener(PriceListener priceListener) {
    this.priceListener = priceListener;
  }

  public void add(Order order) {
    totalPrice += order.getPrice();
    priceListener.onChange();
    order.addQuantity();

  }

  public Order add(Consumable consumable) {
    totalPrice += consumable.getPrice();
    priceListener.onChange();
    for (Order order : orderList) {
      if (consumable.getID() == order.getDishID()) {
        order.addQuantity();
        return null;
      }
    }
    Order order = new Order(consumable);
    orderList.add(order);
    return order;
  }

  public void minus(Order order) {
    try {
      if (!order.minusQuantity()) {
        orderList.remove(order);
      }
      totalPrice -= order.getPrice();
      priceListener.onChange();
    } catch (ConcurrentModificationException e) {

    }
  }

  public void minus(Consumable consumable) {
    try {
      for (Order order : orderList) {
        if (consumable.getID() == order.getDishID()) {
          if (!order.minusQuantity()) {
            orderList.remove(order);
          }
          totalPrice -= order.getPrice();
          priceListener.onChange();
        }
      }
    } catch (ConcurrentModificationException e) {

    }
  }

  public Order getOrder(Consumable consumable) {
    for (Order order : orderList) {
      if (consumable.getID() == order.getDishID()) {
        return order;
      }
    }
    return null;
  }

  public float getTotalPrice() {
    return totalPrice;
  }

  public ObservableList<Order> getOrderList() {
    return this.orderList;
  }

  @Override
  public String toString() {
    String string = "";
    for (Order order : orderList) {
      string += order.getDishName() + " x " + order.getQuantity() + " | ";
    }
    return string;
  }
}
