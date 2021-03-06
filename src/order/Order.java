package order;

import consumable.Consumable;
import java.util.ArrayList;
import java.util.List;

/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 */
public class Order implements Comparable<Order> {

  /** The unique identifier of the order. */
  private int orderID;

  /**
   * The total price of the order, calculated by the total of the prices of each item in the order.
   */
  private float totalPrice;

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
  public Order(int orderID, float totalPrice, String status, String items) {
    this.orderID = orderID;
    this.totalPrice = totalPrice;
    this.status = status;
    this.items = new ArrayList<Consumable>();
    String[] ingredients = items.split(",");
    for (String ingredient : ingredients) {
      Consumable item = new Consumable(ingredient);
      this.items.add(item);
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
   * Returns the total price of the order.
   * @return the price of the order
   */
  public float getTotalPrice() {
    return this.totalPrice;
  }
  
  /**
   * Returns the status of the order.
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
