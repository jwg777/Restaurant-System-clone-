package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import consumable.MenuMap;
import order.Order;
//import server.Customer;

public class RequestThread extends Thread {

  private DataInputStream input;
  private DataOutputStream output;

  private String role;
  private String staffID;

  public RequestThread(Socket socket) {
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
      output.writeUTF("REQUEST STAFF " + username + " " + password);
      output.flush();
      String[] response = input.readUTF().split(" ");
      if (response[0].equals("ACCEPTED")) {
        role = response[1];
        staffID = username;
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean getMenu() {
    MenuMap menu = MenuMap.getInstance();
    boolean result = false;
    try {
      output.writeUTF("GETMENU");
      output.flush();
      String[] response;
      while ((response = ((String) input.readUTF()).split(" ")) != null) {
        if (response[0].equals("ENDMENU")) {
          result = true;
          break;
        } else {
          menu.put(new Consumable(response[0]));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public boolean confirmOrder(Order order) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("CONFIRM " + order.getOrderID());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean cancelOrder(Order order) {
    // If an order has been completed, it cannot be cancelled.
    if (order.getStatus().equals("COMPLETED")) {
      return false;
    }
    // A waiter can cancel an order at any time if not completed.
    // A kitchen employee can only cancel an order if it is confirmed or processing
    if (role.equals("KITCHEN")
        & (!order.getStatus().equals("CONFIRMED") | !order.getStatus().equals("PROCESSING"))) {
      return false;
    }
    try {
      output.writeUTF("CANCEL " + order.getOrderID());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean orderDelivered(Order order) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("DELIVERED " + order.getOrderID());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  /*public boolean deleteMessage(Customer customer) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("DELETEMESSAGE " + customer.getId());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  } */

  public boolean addDish(Consumable consumable) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("ADDDISH " + consumable.serializeToString());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean deleteDish(Consumable consumable) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("DELETEDISH " + consumable.serializeToString());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean updateDish(Consumable consumable) {
    if (!role.equals("WAITER")) {
      return false;
    }
    try {
      output.writeUTF("UPDATEDISH " + consumable.serializeToString());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean processingOrder(Order order) {
    if (!role.equals("KITCHEN")) {
      return false;
    }
    try {
      output.writeUTF("PROCESSING " + order.getOrderID());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean readyOrder(Order order) {
    if (!role.equals("KITCHEN")) {
      return false;
    }
    try {
      output.writeUTF("READY " + order.getOrderID());
      output.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return checkAccepted();
  }

  public boolean checkAccepted() {
    try {
      String[] response = ((String) input.readUTF()).split(" ");
      return response[0].equals("ACCEPTED");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
