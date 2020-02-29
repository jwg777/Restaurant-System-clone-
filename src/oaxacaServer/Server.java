package oaxacaServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server program to manage orders, confirmations, and notifications.
 * 
 * @author Chak
 *
 */
public final class Server implements Runnable {

  /**
   * an instance for the singleton class.
   */
  private static Server instance = null;
  /**
   * port of the server.
   */
  private int port;
  /**
   * Set of threads that are connected to the server.
   */
  private ArrayList<UserThread> userThreads = new ArrayList<>();
  /**
   * boolean to show if the server is running
   */
  boolean running = false;
  /**
   * increments every time a client connects.
   */
  private int i = 0;


  /**
   * private constructor for singleton method.
   */
  private Server() {}

  /**
   * returns the singleton instance of the class
   * 
   * @return single instance of Server
   */
  public static Server getInstance() {
    if (instance == null) {
      instance = new Server();
    }
    return instance;
  }

  /**
   * set the port of the server.
   * 
   * @param port
   */
  public void setPort(int port) {
    this.port = port;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server started on port " + port);
      while (true) {
        Socket socket = serverSocket.accept();
        // Creates a new user thread
        UserThread newUser = new UserThread(socket);
        userThreads.add(newUser);
        newUser.start();
      }
    } catch (IOException e) {
      System.out.println("Port " + port + " is already used");
    }
  }

  /**
   * Removes the thread of the user.
   * 
   * @param user
   */
  public void removeThread(UserThread user) {
    userThreads.remove(user);
  }

  /**
   * returns a unique number for each client.
   * 
   * @return number
   */
  public String addNumebr() {
    return String.format("%04d", ++i);
  }

  public ArrayList<UserThread> getUserThreads() {
    return userThreads;
  }

}
