package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

// TODO: Auto-generated Javadoc
/**
 * The Class KitchenAccess.
 */
public class KitchenAccess {

  /** The kitchen data. */
  DataInteract kitchenData;

  /**
   * Instantiates a new kitchen access.
   */
  public KitchenAccess() {
    kitchenData = DataInteract.getInstance();
  }

  /**
   * Change menu.
   */
  public void changeMenu() {
    // some query to alter menu
  }

  /**
   * Gets the orders.
   *
   * @return the orders
   */
  public ResultSet getOrders() {
    // return kitchenData.select("some query for orders");
    return null;
  }

  /**
   * Sets the order status.
   */
  public void setOrderStatus() {
    // kitchenData.insertIntoTable("some command for order status");
  }
}
