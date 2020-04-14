package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import consumable.MenuMap;
import order.Order;
import order.OrderMap;

public class NotificationThread extends Thread {

  MenuMap menuMap = MenuMap.getInstance();
  OrderMap orderMap = OrderMap.getInstance();

  private DataInputStream input;
  private DataOutputStream output;

  private String staffID;

  public NotificationThread(String username, Socket socket) throws IOException {
    try {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getID() {
    return this.staffID;
  }

  public boolean staffLogin(String username, String password) throws IOException {
    try {
      output.writeUTF("NOTIFICATION STAFF " + username + " " + password);
      output.flush();
      if (input.readUTF().equals("ACCEPTED WAITER")) {
        staffID = username;
        return true;
      }
      if (input.readUTF().equals("ACCEPTED KITCHEN")) {
        staffID = username;
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void run() {
    String response;
    String operand;
    String operator = null;
    do {
      try {
        response = input.readUTF();
        String[] responseArray = response.split(" ");
        operator = responseArray[0];
        operand = responseArray[1];
        switch (operator) {
          case "ADDDISH":
            menuMap.put(new Consumable(operand));
            break;
          case "DELETEDISH":
            menuMap.remove(new Consumable(operand));
            break;
          case "UPDATEDISH":
            break;
          case "ADDORDER":
            orderMap.put(new Order(operand));
            break;
          case "DELETEORDER":
            orderMap.remove(new Order(operand));
            break;
          case "UPDATEORDER":
            break;
        }
      } catch (IOException e) {
        System.out.println("Failed to update");
      }
    } while (operator.equals("DISCONNECT"));
    try {
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
