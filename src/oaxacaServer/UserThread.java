package oaxacaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class UserThread.
 */
public class UserThread extends Thread {

  private ClientType type;

  /** The socket. */
  private Socket socket;

  /** The server. */
  private Server server = Server.getInstance();

  /**
   * Constructor to create a User thread
   * 
   * @param socket
   */
  public UserThread(Socket socket) {
    this.socket = socket;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    try (DataInputStream dIn = new DataInputStream(socket.getInputStream());
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream())) {
      type = ClientType.getType((String) dIn.readUTF());
      if (type == ClientType.INVALID) {
        dOut.writeUTF("INVALID");
        dOut.flush();
        server.write("[Server] : DENIED ACCESS " + socket.getInetAddress().getHostAddress());
        socket.close();
        return;
      }
      String name = type.name() + "_" + server.addNumebr();
      server.addUserName(name);
      server.write("New client connected: " + name);
      dOut.writeUTF("ACCEPTED");
      dOut.flush();
      server.write("[Server => "+name+"] : ACCEPTED");
      for (ClientListener listener : server.clientListeners) {
        listener.onClientChange();
      }
      String response;
      do {
        response = (String) dIn.readUTF();
        server.write("[" + name + "] : " + response);
      } while (!response.equals("STOP"));
      server.removeUser(name, this);
      server.write(name + " has disconnected");
    } catch (IOException e) {

    }
  }

}
