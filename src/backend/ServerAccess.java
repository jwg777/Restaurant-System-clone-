package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Class to manage accessing server, include reading and writing.
 * 
 * @author Chak
 */
final public class ServerAccess extends Thread {

  /**
   * Singleton instance of ServerAccess class.
   */
  private static ServerAccess instance = null;
  /**
   * Socket to connect to the server.
   */
  private Socket socket;
  /**
   * ReadThread to read from server.
   */
  private static ReadThread read = ReadThread.getInstance();
  /**
   * WriteThread to write to server.
   */
  private static WriteThread write = WriteThread.getInstance();
  /**
   * private constructor for singleton class.
   */
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

  /**
   * Sets up the connection to the server. Creates a socket connection, input stream and output
   * stream.
   * 
   * @param ip
   * @param port
   * @throws IOException
   */
  public void setConnection(String ip, int port) throws IOException {
    this.socket = new Socket(ip, port);
    read.setInput(new DataInputStream(socket.getInputStream()));
    write.setOutput(new DataOutputStream(socket.getOutputStream()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {

  }
  
  public void write(String string) throws IOException {
    write.write(string);
  }

  /**
   * Closes the server.
   * 
   * @throws IOException
   */
  public void close() throws IOException {
    read.close();
    write.close();
    socket.close();
  }
}
