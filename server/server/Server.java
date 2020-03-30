package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import consumable.Consumable;
import order.Order;

/**
 * Server program to manage orders, confirmations, and notifications.
 * 
 * @author Chak
 *
 */
public final class Server extends Thread {

  /**
   * an instance for the singleton class.
   */
  private static Server instance = null;
  /**
   * port of the server.
   */
  private int requestPort = 6666;
  /**
   * List of customers that are connected to the server.
   */
  private ArrayList<CustomerClient> customerThreads = new ArrayList<>();
  /**
   * List of waiter that are connected to the server.
   */
  private ArrayList<StaffClient> waiterThreads = new ArrayList<>();
  /**
   * List of kitchen that are connected to the server.
   */
  private ArrayList<StaffClient> kitchenThreads = new ArrayList<>();
  /**
   * boolean to show if the server is running
   */
  boolean running = false;

  Database database = Database.getInstance();

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
   * Starts the server.
   */
  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(requestPort)) {
      System.out.println("Server started on port " + requestPort);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("New Client Connected");
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        String[] line = dIn.readUTF().split(" ");
        if (line[0].equals("REQUEST")) {
          if (line[1].equals("CUSTOMER")) {
            SocketThread newRequest = new SocketThread(dIn, dOut);
            Customer newCustomer = new Customer(Integer.valueOf(line[2]));
            database.addCustomer(newCustomer); // adds the new customer to database and sets an id
            CustomerClient newCustomerClient = new CustomerClient(newCustomer);
            newCustomerClient.setRequest(newRequest); // adds class to process requests from client
            dOut.writeUTF("ACCEPTED " + newCustomer.getId());
            dOut.flush();
            customerThreads.add(newCustomerClient);
          } else if (line[1].equals("STAFF")) {
            ClientType type = database.authenticate(line[2], line[3]);
            if (type == ClientType.WAITER || type == ClientType.KITCHEN) {
              SocketThread newRequest = new SocketThread(dIn, dOut);
              dOut.writeUTF("ACCEPTED " + type.name().toUpperCase());
              dOut.flush();
              Staff newStaff = new Staff(type, line[2], line[3]);
              StaffClient newClient = new StaffClient(newStaff);
              newClient.setRequest(newRequest);
              ((ArrayList<StaffClient>) (type == ClientType.WAITER ? waiterThreads
                  : kitchenThreads)).add(newClient);
            } else {
              dOut.writeUTF("DENIED");
              dOut.flush();
            }
          }
        } else if (line[0].equals("NOTIFICATION")) {
          if (line[1].equals("CUSTOMER")) {
            int id = Integer.parseInt(line[2]);
            for (CustomerClient customerClient : customerThreads) {
              if (customerClient.getId() == id) {
                SocketThread newNotification = new SocketThread(dIn, dOut);
                customerClient.setNotification(newNotification);
              }
            }
          } else if (line[1].equals("STAFF")) {
            for (StaffClient staffClient : waiterThreads) {
              if (staffClient.getUsername().equals(line[2])) {
                SocketThread newNotification = new SocketThread(dIn, dOut);
                staffClient.setNotification(newNotification);
              }
            }
            for (StaffClient staffClient : kitchenThreads) {
              if (staffClient.getUsername().equals(line[2])) {
                SocketThread newNotification = new SocketThread(dIn, dOut);
                staffClient.setNotification(newNotification);
              }
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println("Port " + requestPort + " is already used");
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

  public ArrayList<CustomerClient> getCustomerThreads() {
    return customerThreads;
  }

  public ArrayList<StaffClient> getWaiterThreads() {
    return waiterThreads;
  }

  public ArrayList<StaffClient> getKitchenThreads() {
    return kitchenThreads;
  }

  public ArrayList<Consumable> getMenuList() {
    return database.getDishList();
  }

  public ArrayList<Order> getOrderList() {
    return database.getOrderList();
  }

  public int addCustomer(int tableNum) {
    Customer temp = new Customer(tableNum);
    database.addCustomer(temp);
    return temp.getId();
  }

  public void removeCustomer(int tableNum) {
    database.removeCustomer(tableNum);
  }
}
