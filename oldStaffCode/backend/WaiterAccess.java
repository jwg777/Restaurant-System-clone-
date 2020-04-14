package backend;
/**
 * This class will act as a buffer for the database and the waiter controller.
 * 
 * @author : TeamProject2020 group 22
 * 
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import order.Order;
import order.OrderMap;

/**
 * Class containing methods for accessing and interacting with the database for the Waiter view.
 * 
 * @author joshuagargan
 *
 */
public class WaiterAccess {

  /** Field for accessing methods within dataInteract class */
  //DataInteract waiterData;

  /**
   * Constructor to get instance of DataInteract class.
   */
  public WaiterAccess() {
    //waiterData = DataInteract.getInstance();
  }


  /**
   * This method will allow orders state in the database to be changed
   * 
   * @param tableupdate
   */
  public void markDelivered(String tableupdate) {
    // waiterData.insertIntoTable("insert delivered data");
  }

  /**
   * Retrieves the current menu from the database.
   * 
   * @throws SQLException Thrown if query fails.
   */
  public void getMenu() throws SQLException {
/**
    ResultSet rs = waiterData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");

      tempMap.put(new Consumable(type, itemName, itemPrice, calories, allergens));
    }
*/

  }

  /**
   * Retrieves the table of orders from the database.
   * 
   * @throws SQLException Thrown if query fails.
   */
  public void viewOrders() throws SQLException {
   /**
    ResultSet rs = waiterData.select("SELECT * FROM ORDERS WHERE status != 'started' ORDER BY orderTime");
    OrderMap tempMap = OrderMap.getInstance();

    while (rs.next()) {
      int orderID = rs.getInt("orderID");
      int custID = rs.getInt("cust_ID");
      float totalPrice = rs.getFloat("total_price");
      String timeStamp = (rs.getTimestamp("orderTime")).toString();
      String dish = rs.getString("dish");
      String status = rs.getString("status");
      tempMap.put(status, new Order(orderID, custID, totalPrice, timeStamp, status, dish));
    }
  
**/
  }
  /**
   * Deletes an order from the database.
   * 
   * @param order the order to be deleted
   * @throws SQLException Thrown if update fails.
   */
  public void removeOrder(Order order) throws SQLException {
   // waiterData.update("DELETE FROM Orders WHERE orderID = '" + order.getOrderID() + "'");
  }

  /**
   * When an order is confirmed, move it to the processing orders list.
   * 
   * @param order the confirmed order
   * @throws SQLException Thrown if update fails.
   */
  public void confirmOrder(Order order) throws SQLException {
    /**waiterData.update(
        "UPDATE Orders SET status = 'processing' WHERE orderID = '" + order.getOrderID() + "'");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    waiterData.update("UPDATE Orders SET orderTime = '" + dtf.format(now) + "' WHERE orderID = '"
        + order.getOrderID() + "'"); */
  }

  /**
   * This method will store orders from the database in a resultSet
   * 
   * @return
   */
  public ResultSet viewReady() {
    return null;
    // return waiterData.select("query for ready dishes ready to collect");

  }

  /**
   * Method to delete a given dish from the Menu table in the database.
   * 
   * @param dishName the name of the dish to be deleted
   * 
   */
  public void deleteMenuItem(String dishName) {
    //waiterData.executeDelete("DELETE FROM Menu " + "WHERE dish = '" + dishName + "';");
  }

  /**
   * Method to add a given dish to the Menu table in the database.
   * 
   * @param attributes if columns are specified, it is used in the Insert query
   * @param values the values to be inserted to the database.
   * 
   */
  public void addMenuItem(String attributes, String values) {
    //waiterData.insertIntoTable("Menu", attributes, values);
  }

  /**
   * Method checks if the item already exists in the Menu table in the database.
   * 
   * @param dishName the name of the dish to check
   * @return returns whether or not the dish exists in the table
   * 
   */
  public boolean checkKeyExists(String dishName) {
    /**try {
      ResultSet rs = waiterData.select("SELECT * FROM MENU WHERE dish = '" + dishName + "';");
      if (rs.next()) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }*/
    return false; 
  }
  
  /**
   * This method returns the alerts currently stored in the database and puts them in a result set.
   * @return resultset storing alerts.
   */
  public void getAlerts() {
    //ResultSet rs = waiterData.select("SELECT * FROM Messages");
    //return rs;
  }
  
  /**
   * This method removes alerts from the databse, deleting messages from the database.
   * @param message
   */
  public void removeAlert(String message) {
    //waiterData.executeDelete("DELETE FROM Messages " + "WHERE message = '" + message + "';");
  }
  
  /**
   * This method returns payements from the databse into a resultset.
   * It is to be called when the getPayements button is pressed in the Waiter View,
   */
  public void getPayements() {
    //ResultSet rs = waiterData.select("SELECT * FROM PAYEMENTS");
    //return rs;
  }
  
  /**
   * This method removes payements form the databse when they have been
   * confirmed as paid on the waiter view.
   * @param payement
   */
  public void confirmPayement(String payement) {
    //waiterData.executeDelete("DELETe FROM Payements " + ..");
  }
}
