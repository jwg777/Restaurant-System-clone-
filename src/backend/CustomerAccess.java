package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


/**
 * Class containing methods for accessing and interacting with the database for the customer view.
 *
 */
public class CustomerAccess {

  /** The customer data. */
  DataInteract customerData;
  //ArrayList<String> revList;

  /**
   * Instantiates a new customer access.
   */
  public CustomerAccess() {
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
      
      tempMap.put(new Consumable(type, itemName, itemPrice, calories, allergens));
    }


  }

  
  /**
   * This method will be for placing orders. It will send data into the database to fill the order
   * table.
   * 
   * @param orders the orders
   * @throws SQLException 
   */
  public void placeOrder( ListView<String> orders, int table_num, float price) throws SQLException {
    
    String dishes = orders.getItems().stream().map(Object::toString).collect(Collectors.joining(", "));
    customerData.insertIntoTable("Orders", "", "'" + table_num + "', '" + price + "', '" + dishes + "', 'ordered'");

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
    ResultSet rs = customerData.select("SELECT status FROM Orders " 
                                     + "WHERE OrderID = '" + orderID + "'");
    while (rs.next()) {
      statusAndTime = rs.getString("status");
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

