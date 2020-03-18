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
      String operator = "";
      String operand = "";
      String[] tempResponse = read().split(" ");
      server.addThread(this);
      if (tempResponse[0].equals("NOTIFICATION")) {
        String notificationType = tempResponse[1];
        switch (tempResponse[1]) {
          case "CUSTOMER":
            break;
          case "KITCHEN":
            break;
          case "WAITER":
            break;
        }
      } else if (tempResponse[0].equals("CUSTOMER")) {
        customer(tempResponse[1]);
      } else if (tempResponse[0].contentEquals("STAFF")) {
        switch (server.authenticate(tempResponse[1], tempResponse[2])) {
          case WAITER:
            write("ACCEPTED WAITER");
            waiter(tempResponse[1]);
            break;
          case KITCHEN:
            write("ACCEPTED KITCHEN");
            kitchen(tempResponse[1]);
            break;
          default:
            break;
        }
        /*
         * Change to visitor pattern here after.
         */
      }
    } catch (InvalidClientTypeException e) {
      System.out.println("Invalid User Type tried to Connect");
    } catch (IOException e) {
      System.out.println(name + " has disconnected");
    } finally {
      close();
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
      switch (response[0].toUpperCase()) {
        case "ORDER":
          Order order = new Order(response[0]);

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
