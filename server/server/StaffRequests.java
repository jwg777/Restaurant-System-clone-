package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class StaffRequests extends SocketThread implements Runnable {
  
  private Database database = Database.getInstance();

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
          database.updateOrderStatus(operand, "CONFIRM");
          break;
        case "CANCEL":
          break;
        case "DELIVERED":
          break;
        case "ADDDISH":
          break;
        case "DELETEDISH":
          break;
        case "UPDATEDISH":
          break;
      }
    }
  }


}
