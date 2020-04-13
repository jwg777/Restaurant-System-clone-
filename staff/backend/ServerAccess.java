package backend;

import java.io.IOException;
import java.net.Socket;
import consumable.Consumable;
import order.Order;
import server.Customer;

/**
 * Class to manage accessing server, include reading and writing.
 * 
 * @author Chak
 */
final public class ServerAccess {

  /**
   * Singleton instance of ServerAccess class.
   */
  private static ServerAccess instance = null;

  RequestThread request;
  NotificationThread notification;
  int port = 6666;

  /**
   * private constructor for singleton class.
   */
  private ServerAccess() {}

  /**
   * Gets the instance of ServerAccess.
   * 
   * @return instance
   */
  public static ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }

  /**
   * Sets up the connection to the server. Creates a socket connection, input stream and output
   * stream.
   * 
   * @param ip the IP address of the server
   * @param username the user's user name
   * @param password the user's password
   * @throws IOException Thrown if any input/output is not completed
   */
  public boolean setConnection(String ip, String username, String password) {
    try {
      request = new RequestThread(new Socket(ip, port));
      if (request.staffLogin(username, password)) {
        notification = new NotificationThread(request.getID(), new Socket(ip, port));
        Thread t = new Thread(notification);
        t.start();
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean getMenu() {
    return request.getMenu();
  }

  public boolean confirmOrder(Order order) {
    return request.confirmOrder(order);
  }

  public boolean cancelOrder(Order order) {
    return request.cancelOrder(order);
  }

  public boolean orderDelivered(Order order) {
    return request.orderDelivered(order);
  }

  public boolean deleteMessage(Customer customer) {
    return request.deleteMessage(customer);
  }

  public boolean addDish(Consumable consumable) {
    return request.addDish(consumable);
  }

  public boolean deleteDish(Consumable consumable) {
    return request.deleteDish(consumable);
  }

  public boolean updateDish(Consumable consumable) {
    return request.updateDish(consumable);
  }

  public boolean processingOrder(Order order) {
    return request.processingOrder(order);
  }

  public boolean readyOrder(Order order) {
    return request.readyOrder(order);
  }
}
