package order;

import java.util.ArrayList;
import java.util.List;
import consumable.Consumable;

/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 */
public class Order implements Comparable<Order> {

  /** The unique identifier of the order. */
  private int orderID;

  /** The unique identifier of the customer who made the order. */
  private int custID;

  /**
   * The total price of the order, calculated by the total of the prices of each item in the order
   */
  private float totalPrice;

  // TODO find a way to represent the order time

  /** A list of the items ordered. */
  private List<Consumable> items;

  /**
   * Instantiates a new order by specifying its id, customer id, order time and contents.
   *
   * @param orderID the unique ID of the order
   * @param cust_ID the unique ID of the customer who made the order
   * @param items The items ordered
   */
  public Order(int orderID, int custID, float totalPrice, String items) {
    this.orderID = orderID;
    this.custID = custID;
    this.totalPrice = totalPrice;
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
   * Returns the customer ID associated with the order
   * 
   * @return the customer ID
   */
  public int getCustID() {
    return this.custID;
  }

  public float getTotalPrice() {
    return this.totalPrice;
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
   * Adds item to order
   * 
   * @param item the item
   */
  public void addItem(Consumable item) {
    this.items.add(item);
  }

  /**
   * Removes item from order
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
