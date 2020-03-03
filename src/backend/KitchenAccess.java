package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import consumable.Consumable;
import consumable.MenuMap;
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
   * @return returns the result set given by the select query of the order table
   * @throws SQLException Thrown if sql error occurs
   */
  public ResultSet getOrders() throws SQLException {
    ResultSet rs = kitchenData.select("SELECT * FROM Orders");
    return rs;
  }
  
  /**
   * Retrieves the current menu from the database.
   * 
   * @throws SQLException Thrown if query fails.
   */
  public void getMenu() throws SQLException {
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
  }

  /**

   * This method will alter the state of an order. E.g change an order to complete.
   */
  public void setOrderStatus() {
    // kitchenData.insertIntoTable("some command for order status");
  }
}
