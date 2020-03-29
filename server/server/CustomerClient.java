package server;

public class CustomerClient {
  Customer customer;
  SocketThread request;
  SocketThread notification;

  public CustomerClient(Customer newCustomer) {
    this.customer = newCustomer;
  }

  public void setRequest(SocketThread request) {
    this.request = request;
  }

  public void setNotification(SocketThread notification) {
    this.notification = notification;
  }

  
  
}
