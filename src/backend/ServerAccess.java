package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;

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
  public static ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }

  public void start() {
    try {
      setConnection("167.99.149.174", 6666);
    } catch (IOException e) {
    }
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
    read.start();
  }

  public void write(String string) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println("Client -> Server: [" + string + "]");
          write.write(string);
        } catch (IOException e) {
        }
      }
    });
  }

  public boolean login(String type) throws IOException {
    write(type);
    String response = read.getResponse();
    return true;
    // if (response.equals("ACCEPTED")) {
    // return true;
    // }
    // throw new IOException();
  }

  public boolean login(String type, String username, String password) throws IOException {
    write(type);
    write(username);
    write(password);
    String response = read.getResponse();
    if (response.equals("ACCEPTED")) {
      return true;
    }
    throw new IOException();
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
