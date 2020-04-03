package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import order.Order;

public class StaffNotification extends SocketThread {

  public StaffNotification(DataInputStream input, DataOutputStream output) {
    super(input, output);
  }

  public void receivedNewOrder(Order order) {
    write("ORDER " + order.serializeToString());
  }

  public void confirmOrder(int orderID) {
    write("CONFIRM " + orderID);
  }

  public void cancelOrder(int orderID) {
    write("CANCEL " + orderID);
  }

  public void orderDelivered(int orderID) {
    write("DELIVERED " + orderID);
  }
  
  

}
