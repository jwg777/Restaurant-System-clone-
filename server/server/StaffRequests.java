package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class StaffRequests extends SocketThread implements Runnable {

  private Server server = Server.getInstance();

  public StaffRequests(DataInputStream input, DataOutputStream output) {
    super(input, output);
  }

  @Override
  public void run() {
    while (true) {
      String[] line = read().split(" ");
      String operand = line[1];
      switch (line[0]) {
        case "CONFIRM":
          server.orderConfirmed(operand);
          write("ACCEPTED");
          break;
        case "CANCEL":
          server.cancelOrder(operand);
          write("ACCEPTED");
          break;
        case "DELIVERED":
          server.deliveredOrder(operand);
          break;
        case "ADDDISH":
          server.addNewDish(operand);
          write("ACCEPTED");
          break;
        case "DELETEDISH":
          server.deleteDish(operand);
          write("ACCEPTED");
          break;
        case "UPDATEDISH":
          server.updateDish(operand);
          write("ACCEPTED");
          break;
        case "READY":
          server.readyOrder(operand);
          write("ACCEPTED");
          break;
        default:
          write("DENIED");
      }
    }
  }


}
