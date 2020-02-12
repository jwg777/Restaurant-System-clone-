package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

/**
 * Class containing methods for accessing and interacting with the database for the Waiter view.
 * @author joshuagargan
 *
 */
public class WaiterAccess {
  
  /** Field for accessing methods within dataInteract class */
  DataInteract waiterData;

  /**
   * Constructor to get instance of DataInteract class.
   */
  public WaiterAccess() {
    waiterData = DataInteract.getInstance();
  }

  /**
   * This method will allow orders state in the database to be changed 
   * @param tableupdate
   */
  public void markDelivered(String tableupdate) {
    // waiterData.insertIntoTable("insert delivered data");
  }

  /**
   * This method will get the orders form the databse and return this in a result set
   * @return ResultSet
   */
  public ResultSet viewOrders() {
    return null;
    // return waiterData.select("query for orders");
  }

  /**
   * This method will store orders from the database in a resultSet
   * @return
   */
  public ResultSet viewReady() {
    return null;
    // return waiterData.select("query for ready dishes ready to collect");
  }
}
