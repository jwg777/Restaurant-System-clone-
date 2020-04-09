package server;

public class CustomerClient {

  Customer customer;
  CustomerRequests request;
  SocketThread notification;
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

  public void setNotification(SocketThread notification) {
    this.notification = notification;
  }

  public int getId() {
    return customerId;
  }


}
