package oaxacaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * The Class UserThread.
 */
public class UserThread extends Thread {

  /**
   * Client type.
   */
  private ClientType type;

  /**
   * Name of Client.
   */
  private String name;

  /** The socket. */
  private Socket socket;

  /** The server. */
  private Server server = Server.getInstance();

  /**
   * Input Stream.
   */
  DataInputStream dIn;
  
  /**
   * Output Stream.
   */
  DataOutputStream dOut;

  /**
   * Constructor to create a User thread
   * 
   * @param socket
   */
  public UserThread(Socket socket) {
    this.socket = socket;
    try {
      this.dIn = new DataInputStream(socket.getInputStream());
      this.dOut = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {

    }
  }
  /*
   * Gets the type of Client.
   * 
   * @return type.
   */
  public ClientType getType() {
    return type;
  }

  public String read() throws IOException {
    return (String) dIn.readUTF();
  }

  public void write(String string) throws IOException {
    dOut.writeUTF(string);
    dOut.flush();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    try {
      String operator = "";
      String operand = "";
      type = ClientType.getType(read());
      server.addThread(this);
      this.name = type.name() + "_" + server.addNumebr();
      write("ACCEPTED " + name);
      System.out.println("New Client joined [" + name + "]");
      while (true) {
        String[] response = read().split(" ");
        System.out.println("[" + name + "] : " + Arrays.toString(response));
        if (response[0].equals("STOP")) {
          break;
        }
        if (response.length > 2) {
          write("DISCONNECT");
          break;
        }
        operator = response[0];
        operand = response[1];
        
      }
    } catch (InvalidClientTypeException e) {
      System.out.println("Invalid User Type tried to Connect");
    } catch (IOException e) {
      System.out.println("IOException Catched");
    } finally {
      close();
    }
  }
  
  public boolean customer() {
    return false;
  }
  
  public boolean waiter() {
    return false;
  }
  
  public boolean kitchen() {
    return false;
  }

  public void close() {
    try {
      dOut.close();
      dIn.close();
      socket.close();
    } catch (IOException e) {

    }
  }

}
