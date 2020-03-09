package backend;

import java.sql.ResultSet;
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
  DataInteract kitchenData;

  /**
   * Constructor which gets the instance of the singleton class DataInteract
   */
  public KitchenAccess() {
    kitchenData = DataInteract.getInstance();
  }

  /**
   * This method will return the currenly stored orders from the database and update OrderMap.
   */
  public void getOrders() throws Exception {
    ResultSet rs = kitchenData.select("SELECT * FROM ORDERS ORDER BY orderTime");
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
    kitchenData.update("DELETE FROM Orders WHERE orderID = '" + order.getOrderID() + "'");
  }


}
