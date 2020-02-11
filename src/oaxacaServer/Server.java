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

public final class Server implements Runnable {

  private static Server instance = null;
  private int port;
  private Set<String> usernames = new HashSet<>();
  private Set<UserThread> userThreads = new HashSet<>();
  private final List<ListListener> listListeners = new ArrayList<>();
  private Queue<String> queue = new LinkedList<>();
  boolean running = false;
  private int i = 0;


  private Server() {}

  public static Server getInstance() {
    if (instance == null) {
      instance = new Server();
    }
    return instance;
  }

  public void setPort(int port) {
    this.port = port;
  }

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

  public void write(String string) {
    String time = java.time.LocalTime.now().toString().split("\\.")[0];
    queue.add("[" + time + "] " + string);
    for (ListListener listener : listListeners) {
      listener.onListChange();
    }
  }

  public void addUserName(String string) {
    usernames.add(string);
  }

  public void removeUser(String string, UserThread user) {
    usernames.remove(string);
    userThreads.remove(user);
  }

  public void addListener(ListListener listener) {
    listListeners.add(listener);
  }

  public Queue<String> getQueue() {
    return queue;
  }

  public void close() {
    write("Server on port " + port + " closed.");
    running = false;
    listListeners.clear();
  }

  public int numberOfClients() {
    return usernames.size();
  }

  public String addNumebr() {
    return String.format("%04d", ++i);
  }
}
