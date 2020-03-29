package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import consumable.Consumable;
import order.Order;

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
      String[] tempResponse = read().split(" ");
      server.addThread(this);
      if (tempResponse[0].equals("NOTIFICATION")) {
        initNotification(tempResponse);
      } else if (tempResponse[0].equals("REQUEST")) {
        initRequest(tempResponse);
      }
      /*
       * Change to visitor pattern here after.
       */
    } catch (InvalidClientTypeException e) {
      System.out.println("Invalid User Type tried to Connect");
    } catch (IOException e) {
      System.out.println(name + " has disconnected");
    } finally {
      close();
    }
  }

  public void initNotification(String[] notificationType) {
    try {
      switch (notificationType[1]) {
        case "CUSTOMER":
          customer(notificationType[2]);
          break;
        case "STAFF":
          ClientType employee = server.authenticate(notificationType[2], notificationType[3]);
          if (employee.equals(ClientType.WAITER)) {
            write("ACCEPTED WAITER");
            waiter(notificationType[2]);
          } else if (employee.equals(ClientType.KITCHEN)) {
            write("ACCEPTED KITCHEN");
            kitchen(notificationType[2]);
          }
          break;
        default:
          break;
      }
    } catch (IOException e) {
      System.out.println(name + " has disconnected");
    }
  }

  public void initRequest(String[] requestType) {
    try {
      switch (requestType[1]) {
        case "CUSTOMER":
          customer(requestType[2]);
          break;
        case "STAFF":
          ClientType employee = server.authenticate(requestType[2], requestType[3]);
          if (employee.equals(ClientType.WAITER)) {
            write("ACCEPTED WAITER");
            waiter(requestType[2]);
          } else if (employee.equals(ClientType.KITCHEN)) {
            write("ACCEPTED KITCHEN");
            kitchen(requestType[2]);
          }
          break;
        default:
          break;
      }
    } catch (IOException e) {
      System.out.println(name + " has disconnected");
    }
  }

  public void customer(String tableNum) throws IOException {
    String operator;
    String operand;
    // return id of the customer;
    int id = server.addCustomer(Integer.valueOf(tableNum));
    name = "CUSTOMER_" + id;
    write("ACCEPTED " + name);
    System.out.println("New Request Client joined [" + name + "]");
    do {
      String[] response = read().split(" ");
      operator = response[0];
      switch (operator.toUpperCase()) {
        case "ORDER":
          Order order = new Order(response[0]);
          break;
        default:
          operator = "STOP";
          break;
      }
    } while (operator.equals("STOP"));
  }

  public void waiter(String username) throws IOException {
    String operator;
    String operand;

    name = "WAITER_" + username;
    write("ACCEPTED WAITER");
    System.out.println("New Client joined [" + name + "]");
    /*
     * Adds everything from menu first.
     */
    System.out.println("Sending menu to " + name + "...");
    for (Consumable consumable : server.getMenuList()) {
      write("ADDDISH " + consumable.serializeToString());
    }
    System.out.println("Menu completly sent to " + name);
    /*
     * Adds orders from database first.
     */
    System.out.println("Sending orders to " + name + "...");
    for (Order order : server.getOrderList()) {
      write("ADDORDER " + order.serializeToString());
    }
    System.out.println("All orders sent to " + name);
    do {
      String[] response = read().split(" ");
      System.out.println("[" + name + "] : " + Arrays.toString(response));
      if (response.length > 2 || response.length == 0) {
        write("DISCONNECT");
        break;
      }
      operator = response[0];
      if (response.length == 2) {
        operand = response[1];
        switch (operator.toUpperCase()) {
          case "CONFIRM":
            System.out.println("Sending order " + operand + " to waiter");
            write("ACCEPTED");
            break;
          case "CANCEL":
            System.out.println("Cancelling order " + operand);
            write("ACCEPTED");
            break;
          case "DELIVERED":
            System.out.println("Marking order " + operand + " as delivered");
            write("ACCEPTED");
            break;
          case "DELETEMESSAGE":
            System.out.println("Deleting customer " + operand + " message");
            write("ACCEPTED");
            break;
          case "ADDDISH":
            System.out.println("Adding dish " + operand + " to menu");
            write("ACCEPTED");
            break;
          case "DELETEDISH":
            System.out.println("Deleting dish " + operand + " from menu");
            write("ACCEPTED");
            break;
          case "UPDATEDISH":
            System.out.println("Updating dish " + operand);
            write("ACCEPTED");
            break;
          default:
            operator = "STOP";
            break;
        }
      }

    } while (operator.equals("STOP"));
  }

  public void kitchen(String username) throws IOException {
    String operator;
    String operand;

    name = "KITCHEN_" + username;
    write("ACCEPTED KITCHEN");
    System.out.println("New Client joined [" + name + "]");
    /*
     * Adds orders from database first.
     */
    System.out.println("Sending orders to " + name + "...");
    for (Order order : server.getOrderList()) {
      write("ADDORDER " + order.serializeToString());
    }
    System.out.println("All orders sent to " + name);
    do {
      String[] response = read().split(" ");
      System.out.println("[" + name + "] : " + Arrays.toString(response));
      if (response.length > 2 || response.length == 0) {
        write("DISCONNECT");
        break;
      }
      operator = response[0];
      if (response.length == 2) {
        operand = response[1];
        switch (operator.toUpperCase()) {
          case "PROCESSING":
            System.out.println("Marking order " + operand + " as processing");
            write("ACCEPTED");
            break;
          case "READY":
            System.out.println("Marking order " + operand + " as ready");
            write("ACCEPTED");
            break;
          default:
            operator = "STOP";
            break;
        }
      }

    } while (operator.equals("STOP"));
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
