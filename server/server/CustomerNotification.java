package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import consumable.Consumable;
import order.Order;

public class CustomerNotification extends SocketThread {

  public CustomerNotification(DataInputStream input, DataOutputStream output) {
    super(input, output);
    // TODO Auto-generated constructor stub
  }

  public void addDish(Consumable consumable) {
    write("ADDDISH " + consumable.serializeToString());
  }

  public void deleteDish(Consumable consumable) {
    write("DELETEDISH " + consumable.serializeToString());
  }

  public void updateDish(Consumable consumable) {
    write("UPDATEDISH " + consumable.serializeToString());
  }

  public void updateOrder(Order order) {
    write("UPDATEORDER " + order.serializeToString());
  }
}
