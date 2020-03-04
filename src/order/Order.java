package order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import consumable.Consumable;

/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 */
public class Order implements Comparable<Order>, Serializable {

  /** serial ID of order class */
  private static final long serialVersionUID = -2626289551987782153L;

  /** The unique identifier of the order. */
  private int orderID;

  /** The unique identifier of the customer who made the order. */
  private int custID;

  /**
   * The total price of the order, calculated by the total of the prices of each item in the order.
   */
  private float totalPrice;

  private String timeStamp;

  /**
   * The status of the order. Can be waiting, processing or ready.
   */
  private String status;

  // TODO find a way to represent the order time

  /** A list of the items ordered. */
  private List<Consumable> items;

  /**
   * Instantiates a new order by specifying its id, customer id, order time and contents.
   *
   * @param orderID the unique ID of the order
   * @param custID the unique ID of the customer who made the order
   * @param totalPrice the total price of the order
   * @param timeStamp this is the time at which the order was last updated
   * @param status the status of the order
   * @param items The items ordered
   */
  public Order(int orderID, int custID, float totalPrice, String timeStamp, String status,
      String items) {
    this.orderID = orderID;
    this.custID = custID;
    this.totalPrice = totalPrice;
    this.timeStamp = timeStamp;
    this.status = status;
    this.items = new ArrayList<Consumable>();
    // String[] ingredients = items.split(",");
    // for (String ingredient : ingredients) {
    // Consumable item = new Consumable(ingredient);
    // this.items.add(item);
    // }
  }

  public Order(String serializedString) {
    byte[] data = Base64.getDecoder().decode(serializedString);
    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
      Order temp = (Order) ois.readObject();
      this.orderID = temp.orderID;
      this.custID = temp.custID;
      this.totalPrice = temp.totalPrice;
      this.timeStamp = temp.timeStamp;
      this.status = temp.status;
      this.items = temp.items;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the order ID.
   *
   * @return the order ID
   */
  public int getOrderID() {
    return this.orderID;
  }

  /**
   * Returns the customer ID associated with the order.
   * 
   * @return the customer ID
   */
  public int getCustID() {
    return this.custID;
  }

  /**
   * Returns the total price of the order.
   * 
   * @return the price of the order
   */
  public float getTotalPrice() {
    return this.totalPrice;
  }

  /**
   * Returns the time at which the order was comfirmed
   * 
   * @return returns the timeStamp
   */
  public String getTimeStamp() {
    return this.timeStamp;
  }

  /**
   * Returns the status of the order.
   * 
   * @return the status of the order
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * Returns the list of items in the order.
   *
   * @return the items
   */
  public List<Consumable> getItems() {
    return this.items;
  }

  /**
   * Adds item to order.
   * 
   * @param item the item
   */
  public void addItem(Consumable item) {
    this.items.add(item);
  }

  /**
   * Removes item from order.
   * 
   * @param item the item
   */
  public void removeItem(Consumable item) {
    this.items.remove(item);
  }

  /**
   * Serialises Order to String.
   * 
   * @return String
   * @throws IOException
   */
  public String serializeToString() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(this);
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }

  /**
   * Comparable method for sorting.
   *
   * @param o the o.
   * @return the int.
   */
  @Override
  public int compareTo(Order o) {
    // TODO Auto-generated method stub
    return 0;
  }

}
