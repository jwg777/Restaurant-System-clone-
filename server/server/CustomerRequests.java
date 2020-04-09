package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import order.Order;

public class CustomerRequests extends SocketThread implements Runnable {

  private Database database = Database.getInstance();

  public CustomerRequests(DataInputStream input, DataOutputStream output) {
    super(input, output);
  }

  @Override
  public void run() {
    while (true) {
      String[] line = read().split(" ");
      switch (line[0]) {
        case "ORDER":
          Order order = new Order(line[1]);
          database.addOrder(order);
          
          break;
      }
    }
  }

}
