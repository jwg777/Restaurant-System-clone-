package server;

public class CustomerClient {

  Customer customer;
  SocketThread request;
  SocketThread notification;
  int customerId;

  public CustomerClient(Customer newCustomer) {
    this.customer = newCustomer;
    this.customerId = customer.getId();
  }

  public void setRequest(SocketThread request) {
    this.request = request;
  }

  public void setNotification(SocketThread notification) {
    this.notification = notification;
  }

  public int getId() {
    return customerId;
  }


}
