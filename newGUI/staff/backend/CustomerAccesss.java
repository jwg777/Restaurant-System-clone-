package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;


/**
 * Class containing methods for accessing and interacting with the database for the customer view.
 *
 */
public class CustomerAccesss {

  /** The customer data. */
  DataInteract customerData;
  //ArrayList<String> revList;

  /**
   * Instantiates a new customer access.
   */
  public CustomerAccesss() {
    customerData = DataInteract.getInstance();
  }


  /**
   * Method updates menu in the class menu map. (does not return anything as menu map is a
   * singleton. When this method is called, the menu table in the databse will be loaded into menu
   * map.
   * 
   * @throws SQLException the SQL exception
   */
  public void getMenu() throws SQLException {

    // customerData.loadFile();
    ResultSet rs = customerData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();
    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");

   //   tempMap.put(new Consumable(type, itemName, itemPrice, calories, allergens));
    }


  }

  /**
   * This method will be for placing orders. It will send data into the database to fill the order
   * table.
   * 
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

  /** This method will get the status and last update time for an order.
   * 
   * @param orderID Unqiue to each order to be used in select query
   * @return returns the status and last update time
   * @throws SQLException Thrown if error with SQL query occurs
   * 
   */
  public String getStatusAndTime(String orderID) throws SQLException {
    String statusAndTime = ">";
    ResultSet rs = customerData.select("SELECT status, orderTime FROM Orders " 
                                     + "WHERE OrderID = '" + orderID + "'");
    while (rs.next()) {
      statusAndTime = rs.getString("status") + ">" + rs.getString("orderTime");
    }
    return statusAndTime;
  }

  public ArrayList<String> getReviews() {
    
    ArrayList<String> revList = new ArrayList<String>();
    ResultSet rs = customerData.select("SELECT * FROM Reviews");
    String query = null;
    try {
      while (rs.next()) {
        query =
            rs.getString("name") + " " + rs.getString("star_rating") + " " + rs.getString("review");
        revList.add(query);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return revList;
  }

}

