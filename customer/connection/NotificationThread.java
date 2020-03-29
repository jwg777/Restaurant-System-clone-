package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import consumable.MenuMap;

public class NotificationThread extends Thread {

  MenuMap menuMap = MenuMap.getInstance();

  DataInputStream input;

  public NotificationThread(String type, String id, Socket socket) throws IOException {
    try (DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
      input = new DataInputStream(socket.getInputStream());
      output.writeUTF(type.toUpperCase() + " " + id);
      if (!input.readUTF().equals("ACCEPTED")) {
        throw new IOException();
      }
    } catch (IOException e) {
      throw new IOException();
    }

    /*
     * 1. Creates new socket connection 2. Read and Write stream 3. establish client type 4. close
     * write stream
     */
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
            System.out.println("Updated dish");
            break;
        }
      } catch (IOException e) {
        System.out.println("Failed to update");
      }
    } while (operator.equals("DISCONNECT"));
    try {
      input.close();
    } catch (IOException e) {
    }

  }
}
