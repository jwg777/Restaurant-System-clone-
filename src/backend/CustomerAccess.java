package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerAccess.
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
   * Gets the menu.
   *
   * @return the menu
   * @throws SQLException the SQL exception
   */
  public void getMenu() throws SQLException {

    ResultSet rs = customerData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      String type = rs.getString("type");
      float itemPrice = rs.getFloat("price");
      tempMap.put(type, new Consumable(itemName, itemPrice));
    }


  }

  /**
   * Place order.
   *
   * @param orders the orders
   */
  public void placeOrder(String orders) {
    // customerData.insertIntoTable("insert order data");
  }

  /**
   * Give feedback.
   *
   * @param feedback the feedback
   */
  public void giveFeedback(String feedback) {
    // customerData.insertIntoTable("insert feedback data");
  }
}

