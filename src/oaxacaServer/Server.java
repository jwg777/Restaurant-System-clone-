package oaxacaServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public final class Server implements Runnable {

  /** The instance. */
  private static Server instance = null;

  /** The port. */
  private int port;

  /** The usernames. */
  private Set<String> usernames = new HashSet<>();

  /** The user threads. */
  private Set<UserThread> userThreads = new HashSet<>();

  /** The list listeners. */
  private final List<ListListener> listListeners = new ArrayList<>();

  /** The queue. */
  private Queue<String> queue = new LinkedList<>();

  /** The running. */
  boolean running = false;

  /** The i. */
  private int i = 0;


  /**
   * Instantiates a new server.
   */
  private Server() {}

  /**
   * Gets the single instance of Server.
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
   * Sets the port.
   *
   * @param port the new port
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Run.
   */
  @Override
  public void run() {
    if (running) {
      listListeners.remove(listListeners.size() - 1);
      write("Server already started");
      return;
    }
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      running = true;
      write("Server started on port " + port);
      while (running) {
        Socket socket = serverSocket.accept();
        // Creates a new user thread
        UserThread newUser = new UserThread(socket);
        userThreads.add(newUser);
        newUser.start();
      }
      serverSocket.close();
    } catch (IOException e) {
      write("Port " + port + " is already used");
      listListeners.remove(listListeners.size() - 1);
    } finally {
      running = false;
    }
  }

  /**
   * Write.
   *
   * @param string the string
   */
  public void write(String string) {
    String time = java.time.LocalTime.now().toString().split("\\.")[0];
    queue.add("[" + time + "] " + string);
    for (ListListener listener : listListeners) {
      listener.onListChange();
    }
  }

  /**
   * Adds the user name.
   *
   * @param string the string
   */
  public void addUserName(String string) {
    usernames.add(string);
  }

  /**
   * Removes the user.
   *
   * @param string the string
   * @param user the user
   */
  public void removeUser(String string, UserThread user) {
    usernames.remove(string);
    userThreads.remove(user);
  }

  /**
   * Adds the listener.
   *
   * @param listener the listener
   */
  public void addListener(ListListener listener) {
    listListeners.add(listener);
  }

  /**
   * Gets the queue.
   *
   * @return the queue
   */
  public Queue<String> getQueue() {
    return queue;
  }

  /**
   * Close.
   */
  public void close() {
    write("Server on port " + port + " closed.");
    running = false;
    listListeners.clear();
  }

  /**
   * Number of clients.
   *
   * @return the int
   */
  public int numberOfClients() {
    return usernames.size();
  }

  /**
   * Adds the numebr.
   *
   * @return the string
   */
  public String addNumebr() {
    return String.format("%04d", ++i);
  }
}
