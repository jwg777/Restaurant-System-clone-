package server;

import consumable.Consumable;
import order.Order;

public class CustomerClient {

  Customer customer;
  CustomerRequests request;
  CustomerNotification notification;
  int customerId;

  public CustomerClient(Customer newCustomer) {
    this.customer = newCustomer;
    this.customerId = customer.getId();
  }

  public void setRequest(CustomerRequests request) {
    this.request = request;
    Thread thread = new Thread(request);
    thread.start();
  }

  public void setNotification(CustomerNotification notification) {
    this.notification = notification;
  }

  public int getId() {
    return customerId;
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

  public void updateOrder(Order order) {
    notification.updateOrder(order);
  }

}
