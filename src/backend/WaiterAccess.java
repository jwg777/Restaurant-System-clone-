package backend;

import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import order.Order;
import order.OrderMap;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaiterAccess {
  DataInteract waiterData;

  public WaiterAccess() {
    waiterData = DataInteract.getInstance();
  }
  
  /**
   * Retrieves the current menu from the database.
   * @throws SQLException Thrown if query fails.
   */
  public void getMenu() throws SQLException {

    ResultSet rs = waiterData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      String type = rs.getString("type");
      float itemPrice = rs.getFloat("price");
      tempMap.put(type, new Consumable(itemName, itemPrice));
    }


  }

  public void markDelivered(String tableupdate) {
    // waiterData.insertIntoTable("insert delivered data");
  }

  /**
   * Retrieves the table of orders from the database.
   * @throws SQLException Thrown if query fails.
   */
  public void viewOrders() throws SQLException {
    ResultSet rs = waiterData.select("SELECT * FROM ORDERS");
    OrderMap tempMap = OrderMap.getInstance();
    
    while (rs.next()) {
      int orderID = rs.getInt("orderID");
      int custID = rs.getInt("cust_ID");
      float totalPrice = rs.getFloat("total_price");
      String dish = rs.getString("dish");
      String status = rs.getString("status");
      tempMap.put(status, new Order(orderID, custID, totalPrice, status, dish));
    }
  }

  public ResultSet viewReady() {
    return null;
    // return waiterData.select("query for ready dishes ready to collect");
  }
}
