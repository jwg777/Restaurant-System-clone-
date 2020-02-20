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
   * Set of user names that are connected to the server.
   */
  private Set<String> usernames = new HashSet<>();
  /**
   * Set of threads that are connected to the server.
   */
  private Set<UserThread> userThreads = new HashSet<>();
  /**
   * List of list view listener.
   */
  private final List<ListListener> listListeners = new ArrayList<>();
  /**
   * List of client listener.
   */
  public final List<ClientListener> clientListeners = new ArrayList<>();
  /**
   * Queue for logs to be displayed
   */
  private Queue<String> queue = new LinkedList<>();
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
   * 
   * Returns the set of user names.
   * 
   * @return user names
   */
  public Set<String> getUsernames() {
    return usernames;
  }

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
   * Tells the listeners to write on the list view.
   * 
   * @param string
   */
  public void write(String string) {
    String time = java.time.LocalTime.now().toString().split("\\.")[0];
    queue.add("[" + time + "] " + string);
    for (ListListener listener : listListeners) {
      listener.onListChange();
    }
  }

  /**
   * Adds username to the set of usernames.
   * 
   * @param string
   */
  public void addUserName(String string) {
    usernames.add(string);
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
   * remove the user completely from the server.
   * 
   * @param username
   * @param user
   */
  public void removeUser(String string, UserThread user) {
    usernames.remove(string);
    removeThread(user);
  }

  /**
   * adds listener for list view.
   * 
   * @param listener
   */
  public void addListListener(ListListener listener) {
    listListeners.add(listener);
  }

  /**
   * add listener for clients.
   * 
   * @param listener
   */
  public void addClientListener(ClientListener listener) {
    clientListeners.add(listener);
  }

  /**
   * gets the queue of logs.
   * 
   * @return the queue
   */
  public Queue<String> getQueue() {
    return queue;
  }

  /**
   * closes the server.
   */
  public void close() {
    write("Server on port " + port + " closed.");
    running = false;
    listListeners.clear();
  }

  /**
   * return the number of clients.
   * 
   * @return number of clients
   */
  public int numberOfClients() {
    return usernames.size();
  }

  /**
   * returns a unique number for each client.
   * 
   * @return number
   */
  public String addNumebr() {
    return String.format("%04d", ++i);
  }

  public Set<UserThread> getUserThreads() {
    return userThreads;
  }

}
