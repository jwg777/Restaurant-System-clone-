package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;


/**
 * Class containing methods for accessing and interacting with the database for the customer view.
 *
 */
public class CustomerAccess {

  /** The customer data. */
  DataInteract customerData;

  /**
   * Instantiates a new customer access.
   */
  public CustomerAccess() {
    customerData = DataInteract.getInstance();
  }


/**
 * Method updates menu in the class menu map. (does not return anything as menu map is a singleton.
 * When this method is called, the menu table in the databse will be loaded into menu map.
 * @throws SQLException the SQL exception
 */
  public void getMenu() throws SQLException {
    
    //customerData.loadFile();
    ResultSet rs = customerData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");
      
      tempMap.put(type, new Consumable(itemName, itemPrice, calories, allergens));
    }


  }

  /**
   * This method will be for placing orders. It will send data into the database
   * to fill the order table.
   * @param orders the orders
   */
  public void placeOrder(String orders) {
    // customerData.insertIntoTable("insert order data");
  }

  /**
   * This method will be to store feedback in the database.
   * @param feedback the feedback
   */
  public void notifyWaiter(String message) {
    customerData.insertIntoTable("Messages", "", message);
  }
  
}

