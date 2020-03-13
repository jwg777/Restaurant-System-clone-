package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import order.Order;
import server.Customer;

public class RequestThread extends Thread {

  private DataInputStream input;
  private DataOutputStream output;

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
      output.writeUTF("STAFF");
      output.flush();
      if (!input.readUTF().equals("OK")) {
        throw new IOException();
      }
      output.writeUTF(username + " " + password);
      output.flush();
      if (input.readUTF().equals("ACCEPTED WAITER")) {
        return true;
      }
      if (input.readUTF().equals("ACCEPTED KITCHEN")) {
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean confirmOrder(Order order) {
    try {
      output.writeUTF("CONFIRM " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean cancelOrder(Order order) {
    try {
      output.writeUTF("CANCEL " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean orderDelivered(Order order) {
    try {
      output.writeUTF("DELIVERED " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteMessage(Customer customer) {
    try {
      output.writeUTF("DELETEMESSAGE " + customer.getId());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean addDish(Consumable consumable) {
    try {
      output.writeUTF("ADDDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteDish(Consumable consumable) {
    try {
      output.writeUTF("DELETEDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean updateDish(Consumable consumable) {
    try {
      output.writeUTF("UPDATEDISH " + consumable.serializeToString());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean processingOrder(Order order) {
    try {
      output.writeUTF("PROCESSING " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean readyOrder(Order order) {
    try {
      output.writeUTF("READY " + order.getOrderID());
      output.flush();
      checkAccepted();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
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
