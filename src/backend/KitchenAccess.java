package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;


/**
 * 
 * Class containing methods for accessing and interacting with the database for the kitchen view.
 * @author joshuagargan
 *
 */
public class KitchenAccess {

  
  /** Field kitchenData- a singleton of the DataInteract class */
  DataInteract kitchenData;

  /**
   * Constructor which gets the instance of the singleton class DataInteract
   */
  public KitchenAccess() {
    kitchenData = DataInteract.getInstance();
  }

  /**
   * This method will change the menu with new data.
   */
  public void changeMenu() {
    // some query to alter menu
  }

  /**
   * This method will return the currenly stored orders from the database and update OrderMap.
   * @return
   */
  public ResultSet getOrders() {
    // return kitchenData.select("some query for orders");
    return null;
  }

  /**

   * This method will alter the state of an order. E.g change an order to complete.
   */
  public void setOrderStatus() {
    // kitchenData.insertIntoTable("some command for order status");
  }
}
