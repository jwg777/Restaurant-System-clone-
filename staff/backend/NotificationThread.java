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
  
}