package connection;

import java.io.IOException;
import java.net.Socket;
import order.Order;

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

  public boolean setConnection(String ip, String tableNumber) {
    try {
      request = new RequestThread(new Socket(ip, port));
      if (request.customerLogin(Integer.parseInt(tableNumber))) {
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

  public void getMenu() {
    request.getMenu();
  }

  public void order(Order order) {
    request.order(order);
  }

  public void cancelOrder(Order order) {
    request.cancelOrder(order);
  }

  public void paymentConfirmed() {
    request.paymentConfirmed();
  }

}
