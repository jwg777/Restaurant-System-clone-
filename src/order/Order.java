package order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

  /** The order has not been started. */
  private boolean isNew = true;

  /** The order is being prepared. */
  private boolean isInProgress = false;

  /** The order is ready to be served. */
  private boolean isCompleted = false;

  /**
   * Instantiates a new order by specifying its id, customer id, order time and contents.
   *
   * @param orderID the unique ID of the order
   * @param cust_ID the unique ID of the customer who made the order
   * @param items The items ordered
   */
  public Order(int orderID, int custID, String items) {
    this.orderID = orderID;
    this.custID = custID;
    this.items = new ArrayList<Consumable>();
    String[] ingredients = items.split(",");
    for (String ingredient : ingredients) {
      DecimalFormat df = new DecimalFormat("0.00");
      Consumable item = new Consumable(ingredient, Float.parseFloat(df.format(Math.random() * 16)));
      this.totalPrice += item.getPrice();
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
   * Sets order to new
   */
  public void setNew() {
    this.isNew = true;
    this.isInProgress = false;
    this.isCompleted = false;
  }

  /**
   * Checks if order is new
   * 
   * @return true if new
   */
  public boolean isNew() {
    return this.isNew;
  }

  /**
   * Sets order to in progress
   */
  public void setInProgress() {
    this.isInProgress = true;
    this.isNew = false;
    this.isCompleted = false;
  }

  /**
   * Checks if order is in progress
   * 
   * @return true if in progress
   */
  public boolean isInProgress() {
    return this.isInProgress;
  }

  /**
   * Sets order to completed
   */
  public void setCompleted() {
    this.isCompleted = true;
    this.isNew = false;
    this.isInProgress = false;
  }

  /**
   * Checks if order is completed
   * 
   * @return true if completed
   */
  public boolean isCompleted() {
    return this.isCompleted;
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
