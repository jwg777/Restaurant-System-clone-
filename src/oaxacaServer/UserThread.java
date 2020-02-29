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
  
  private String name;

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

  public ClientType getType() {
    return type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    try (DataInputStream dIn = new DataInputStream(socket.getInputStream())) {
      type = ClientType.getType((String) dIn.readUTF());
      
    } catch (IOException e) {

    }
  }

  public void writeToClient(String string) {
    try (DataOutputStream dOut = new DataOutputStream(socket.getOutputStream())) {
      dOut.writeUTF(string);
      dOut.flush();
    } catch (IOException e) {

    }
  }

}
