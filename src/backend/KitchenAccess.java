package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import order.Order;
import order.OrderMap;


/**
 * 
 * Class containing methods for accessing and interacting with the database for the kitchen view.
 * 
 * @author joshuagargan
 *
 */
public class KitchenAccess {


  /** Field kitchenData- a singleton of the DataInteract class */
  //DataInteract kitchenData;

  /**
   * Constructor which gets the instance of the singleton class DataInteract
   */
  public KitchenAccess() {
    //kitchenData = DataInteract.getInstance();
  }

  /**
   * This method will return the currenly stored orders from the database and update OrderMap.
   * @throws SQLException Thrown if sql error occurs
   */
  public void getOrders() throws SQLException {
    //ResultSet rs = kitchenData.select("SELECT * FROM Orders WHERE status != 'waiting' ORDER BY orderTime");
    OrderMap map = OrderMap.getInstance();
    
 /**
    while (rs.next()) {
      int orderID = rs.getInt("orderID");
      int custID = rs.getInt("cust_ID");
      float totalPrice = rs.getFloat("total_price");
      String timeStamp = (rs.getTimestamp("orderTime")).toString();
      String dish = rs.getString("dish");
      String status = rs.getString("status");
      map.put(status, new Order(orderID, custID, totalPrice, timeStamp, status, dish));
  
    }
    **/
  }
  
  /**
   * Retrieves the current menu from the database.
   * 
   * @throws SQLException Thrown if query fails.
   */
  public void getMenu() throws SQLException {
    /**
    ResultSet rs = kitchenData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");

      tempMap.put(new Consumable(type, itemName, itemPrice, calories, allergens));
    }
    **/
  }
  
  public boolean getIfPaid(int custID) throws SQLException{
    /**
    ResultSet rs = kitchenData.select("SELECT * FROM Customers WHERE cust_id = '" + custID + "'");
    while (rs.next()) {
      return rs.getBoolean("paid");
    }**/
    return false;
    
  }

  /**
   * 
   * This method will alter the state of an order. E.g change an order to complete.
   */
  public void setOrderStatus() {
    // kitchenData.insertIntoTable("some command for order status");
  }

  /**
   * method to remove the orders placed.
   * 
   * @param order
   */
  public void removeOrders(Order order) {
    //kitchenData.update("DELETE FROM Orders WHERE orderID = '" + order.getOrderID() + "'");
  }
  
  /**
   * Method will be called when kitchenview sends a message to the waiter.
   * the String will be sent to the databse where it will be stored
   * @param message
   */
  public void sendMessageWaiter(String message) {
    //kitchenData.insertIntoTable("messages" + message);
  }
  
  /**
   * This method will return a resultset that will be drisplayed on the gui for the KitchenView
   * IT will allow kitchen staff to see what messages the waiters have sent
   * @return
   */
  public void returnWaiterMessages() {
    //kitchenData.select("SELECT * messages FROM Message");
  }


}
