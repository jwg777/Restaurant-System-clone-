package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import consumable.Consumable;
import order.Order;

public class CustomerRequests extends SocketThread implements Runnable {

  private Server server = Server.getInstance();

  public CustomerRequests(DataInputStream input, DataOutputStream output) {
    super(input, output);
  }

  @Override
  public void run() {
    while (true) {
      String[] line = read().split(" ");
      switch (line[0]) {
        case "ORDER":
          server.newOrderReceived(new Order(line[1]));
          write("ACCEPTED");
          break;
        case "CANCEL":
          server.cancelOrder(line[1]);
          write("ACCEPTED");
          break;
        case "GETMENU":
          for (Consumable dish : server.getMenuList()) {
            write(dish.serializeToString());
          }
          write("ENDMENU");
          break;
        default:
          write("DENIED");
      }
    }
  }

}
