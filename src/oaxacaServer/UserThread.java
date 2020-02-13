package oaxacaServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class UserThread.
 */
public class UserThread extends Thread {

  /** The socket. */
  private Socket socket;

  /** The server. */
  private Server server = Server.getInstance();

  /**
   * Constructor to create a User thread
   * @param socket
   */
  public UserThread(Socket socket) {
    this.socket = socket;
  }

  /* (non-Javadoc)
   * @see java.lang.Thread#run()
   */
  public void run() {
    try {
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      ClientType type = ClientType.valueOf((String) dis.readUTF());
      String name = type.name() + "_" + server.addNumebr();
      server.addUserName(name);
      server.write("New client connected: " + name);
      for(ClientListener listener: server.clientListeners) {
        listener.onClientChange();
      }
      String response;
      do {
        dis = new DataInputStream(socket.getInputStream());
        response = (String) dis.readUTF();
        server.write("[" + name + "] : " + response);
      } while (!response.equals("STOP"));
      socket.close();
      server.removeUser(name, this);
      server.write(name + " has disconnected");
    } catch (IOException e) {

    } catch (IllegalArgumentException e) {
      server.removeThread(this);
    }
  }

}
