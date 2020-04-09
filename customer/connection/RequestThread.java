package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import consumable.MenuMap;
import order.Order;

public class RequestThread {

  private DataInputStream input;
  private DataOutputStream output;

  private String customerID;

  public RequestThread(Socket socket) {
    try {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {

    }
  }

  public String getID() {
    return this.customerID;
  }

  // CUSTOMER FUNCTIONS
  public boolean customerLogin(int tableNum) {
    try {
      output.writeUTF("REQUEST CUSTOMER " + tableNum);
      output.flush();
      String[] response = ((String) input.readUTF()).split(" ");
      if (response[0].equals("ACCEPTED")) {
        customerID = response[1];
        return true;
      }
    } catch (IOException e) {
      return false;
    }
    return false;
  }

  public boolean getMenu() {
    MenuMap menu = MenuMap.getInstance();
    try {
      output.writeUTF("GETMENU");
      output.flush();
      String[] response;
      while ((response = ((String) input.readUTF()).split(" ")) != null) {
        if (response[0].equals("ENDMENU")) {
          break;
        } else {
          menu.put(new Consumable(response[0]));
        }
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public boolean order(Order order) {
    try {
      output.writeUTF("ORDER " + order.serializeToString());
      output.flush();
      String[] response = ((String) input.readUTF()).split(" ");
      return response[0].equals("ACCEPTED");
    } catch (IOException e) {
      return false;
    }
  }

  public boolean cancelOrder(Order order) {
    try {
      output.writeUTF("CANCEL " + order.getOrderID());
      output.flush();
      String[] response = ((String) input.readUTF()).split(" ");
      return response[0].equals("ACCEPTED");
    } catch (IOException e) {
      return false;
    }
  }

  public boolean notify(String message) {
    try {
      output.writeUTF("NOTIFYWAITER " + customerID + " " + message);
      output.flush();
      String[] response = ((String) input.readUTF()).split(" ");
      return response[0].equals("ACCEPTED");
    } catch (IOException e) {
      return false;
    }
  }

  public boolean paymentConfirmed() {
    try {
      output.writeUTF("PAYMENTCONFIRMED " + customerID);
      output.flush();
      String[] response = ((String) input.readUTF()).split(" ");
      return response[0].equals("OK");
    } catch (IOException e) {
      return false;
    }
  }

}
