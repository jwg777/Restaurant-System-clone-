package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import order.Order;
import server.ClientType;
import server.Customer;

public class RequestThread extends Thread {

  private DataInputStream input;
  private DataOutputStream output;

  private ClientType role;

  public RequestThread(Socket socket) {
    try {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean staffLogin(String username, String password) throws IOException {
    try {
      output.writeUTF("REQUEST STAFF " + username + " " + password);
      output.flush();
      String[] response = input.readUTF().split(" ");
      if (response[0].equals("ACCEPTED")) {
        role = ClientType.getType(response[1]);
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean confirmOrder(Order order) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("CONFIRM " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean cancelOrder(Order order) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("CANCEL " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean orderDelivered(Order order) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("DELIVERED " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean deleteMessage(Customer customer) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("DELETEMESSAGE " + customer.getId());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean addDish(Consumable consumable) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("ADDDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean deleteDish(Consumable consumable) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("DELETEDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean updateDish(Consumable consumable) {
    if (role != ClientType.WAITER) {
      return false;
    }
    try {
      output.writeUTF("UPDATEDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean processingOrder(Order order) {
    if (role != ClientType.KITCHEN) {
      return false;
    }
    try {
      output.writeUTF("PROCESSING " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean readyOrder(Order order) {
    if (role != ClientType.KITCHEN) {
      return false;
    }
    try {
      output.writeUTF("READY " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
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
