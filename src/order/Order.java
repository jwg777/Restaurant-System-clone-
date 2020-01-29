package order;

import java.util.ArrayList;
import java.util.Collections;
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
	
	//TODO find a way to represent the order time
	
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
	public Order(int orderID, int custID, List<Consumable> items) {
		this.orderID = orderID;
		this.custID = custID;
		this.items = new ArrayList<Consumable>();
		for (Consumable item : items) {
			this.items.add(item);
		}
		Collections.sort(this.items);
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
	 * @return the customer ID
	 */
	public int getCustID() {
	    return this.custID;
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
	 * @param item the item
	 */
	public void addItem(Consumable item) {
	    this.items.add(item);
	}
	
	/**
	 * Removes item from order
	 * @param item the item
	 */
	public void removeItem(Consumable item) {
	    this.items.remove(item);
	}
	
	/**
	 * Sets order to new
	 * @param isNew true if new
	 */
	public void setNew(boolean isNew) {
	    this.isNew = isNew;
	}
	
	/**
	 * Checks if order is new
	 * @return true if new
	 */
	public boolean isNew() {
	    return this.isNew;
	}
	
	/**
	 * Sets order to in progress
	 * @param isInProgress true if in progress
	 */
	public void setInProgress(boolean isInProgress) {
	    this.isInProgress = isInProgress;
	}
	
	/**
	 * Checks if order is in progress
	 * @return true if in progress
	 */
	public boolean isInProgress() {
	    return this.isInProgress;
	}
	
	/**
	 * Sets order to completed
	 * @param isCompleted true if completed
	 */
	public void setCompleted(boolean isCompleted) {
	    this.isCompleted = isCompleted;
	}
	
	/**
	 * Checks if order is completed
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
