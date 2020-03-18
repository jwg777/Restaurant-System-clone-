package connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
  public ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }

  public boolean setConnection(String ip, String tableNumber) {
    try {
      request = new RequestThread(new Socket(ip, port));
      if (request.customerLogin(Integer.parseInt(tableNumber))) {
        notification =
            new NotificationThread("NOTIFICATION CUSTOMER", request.getID(), new Socket(ip, port));
        Thread t = new Thread(notification);
        t.start();
        return true;
      }
    } catch (IOException e) {
    }
    return false;
  }


}
