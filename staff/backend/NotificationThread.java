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
  
  DataInputStream input;
  
  public NotificationThread(String type, String id, Socket socket) throws IOException {
    try (DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
      input = new DataInputStream(socket.getInputStream());
      output.writeUTF(type.toUpperCase() + " " + id);
      if (!input.readUTF().equals("ACCEPTED")) {
        throw new IOException();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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