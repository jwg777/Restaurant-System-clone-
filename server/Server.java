

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import consumable.Consumable;
import order.Order;

/**
 * Server program to manage orders, confirmations, and notifications.
 * 
 * @author Chak
 *
 */
public final class Server {

  /**
   * an instance for the singleton class.
   */
  private static Server instance = null;
  /**
   * port of the server.
   */
  private int port;
  /**
   * List of customers that are connected to the server.
   */
  private ArrayList<UserThread> customerThreads = new ArrayList<>();
  /**
   * List of waiter that are connected to the server.
   */
  private ArrayList<UserThread> waiterThreads = new ArrayList<>();
  /**
   * List of kitchen that are connected to the server.
   */
  private ArrayList<UserThread> kitchenThreads = new ArrayList<>();
  /**
   * boolean to show if the server is running
   */
  boolean running = false;

  Database database = Database.getInstance();

  Waiter waiter = Waiter.getInstance();

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

  /**
   * Starts the server.
   */
  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server started on port " + port);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("New Client Connected");
        // Creates a new user thread
        UserThread newUser = new UserThread(socket);
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
   * @throws InvalidClientTypeException
   * @throws UserNotFoundException
   */
  public void removeThread(UserThread user) throws InvalidClientTypeException {
    switch (user.getType()) {
      case CUSTOMER:
        customerThreads.remove(user);
        break;
      case WAITER:
        waiterThreads.remove(user);
        break;
      case KITCHEN:
        kitchenThreads.remove(user);
        break;
      default:
        throw new InvalidClientTypeException();
    }
  }

  public void addThread(UserThread user) throws InvalidClientTypeException {
    switch (user.getType()) {
      case CUSTOMER:
        customerThreads.add(user);
        break;
      case WAITER:
        waiterThreads.add(user);
        break;
      case KITCHEN:
        kitchenThreads.add(user);
        break;
      case INVALID:
        throw new InvalidClientTypeException();

    }
  }

  public ClientType authenticate(String username, String password) {
    for (Staff staff : database.getStaffList()) {
      if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
        return staff.getRole();
      }
    }
    return ClientType.INVALID;
  }

  public ArrayList<UserThread> getCustomerThreads() {
    return customerThreads;
  }

  public ArrayList<UserThread> getWaiterThreads() {
    return waiterThreads;
  }

  public ArrayList<UserThread> getKitchenThreads() {
    return kitchenThreads;
  }

  public ArrayList<Consumable> getMenuList() {
    return database.getDishList();
  }

  public ArrayList<Order> getOrderList() {
    try {
      // If user is water
      return waiter.getOrders();
      // If user is kitchen
      // return kitchen.getOrders();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int addCustomer(int tableNum) {
    database.addCustomer(new Customer(tableNum));
    return 0;
  }
}
