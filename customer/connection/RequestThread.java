package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import order.Order;

public class RequestThread extends Thread {

  private DataInputStream input;
  private DataOutputStream output;

  public RequestThread(Socket socket) {
    try {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {

    }
  }

  // CUSTOMER FUNCTIONS
  public boolean customerLogin(int tableNum) {
    try {
      output.writeUTF("CUSTOMER " + tableNum);
      output.flush();
      if (input.readUTF().equals("ACCEPTED")) {
        return true;
      }
    } catch (IOException e) {
      return false;
    }
    return false;
  }

  public boolean order(Order order) {
    try {
      output.writeUTF("ORDER " + order.serializeToString());
      output.flush();
      if (input.readUTF().equals("ACCEPTED")) {
        return true;
      }
    } catch (IOException e) {
      return false;
    }
    return false;
  }

  // STAFF FUNCTIONS
  public String staffLogin(String username, String password) {
    return "WAITER";
  }

  public boolean confirmOrder(Order order) {
    return true;
  }

  @Override
  public void run() {

  }

}
