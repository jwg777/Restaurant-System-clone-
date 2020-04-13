package server;

import consumable.Consumable;
import order.Order;

public class StaffClient {

  String username;
  Staff staff;
  StaffRequests request;
  StaffNotification notification;
  ClientType role;

  public StaffClient(Staff newStaff) {
    this.staff = newStaff;
    this.username = staff.getUsername();
  }

  public void setRequest(StaffRequests request) {
    this.request = request;
    Thread thread = new Thread(request);
    thread.start();
  }

  public void setNotification(StaffNotification notification) {
    this.notification = notification;
  }

  public String getUsername() {
    return username;
  }

  public void receivedNewOrder(Order order) {
    notification.receivedNewOrder(order);
  }

  public void confirmOrder(int orderID) {
    notification.confirmOrder(orderID);
  }

  public void cancelOrder(int orderID) {
    notification.cancelOrder(orderID);
  }

  public void orderDelivered(int orderID) {
    notification.orderDelivered(orderID);
  }

  public void processingOrder(int orderID) {
    notification.processingOrder(orderID);
  }

  public void readyOrder(int orderID) {
    notification.readyOrder(orderID);
  }

  public void addDish(Consumable consumable) {
    notification.addDish(consumable);
  }

  public void deleteDish(Consumable consumable) {
    notification.deleteDish(consumable);
  }

  public void updateDish(Consumable consumable) {
    notification.updateDish(consumable);
  }

}
