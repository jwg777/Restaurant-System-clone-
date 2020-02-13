package backend;
/** This class will act as a buffer for the database and the waiter controller.
 * 
 * @author : TeamProject2020 group 22
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;

public class WaiterAccess {
  DataInteract waiterData;

  public WaiterAccess() {
    waiterData = DataInteract.getInstance();
  }

  public void markDelivered(String tableupdate) {
    // waiterData.insertIntoTable("insert delivered data");
  }

  public ResultSet viewOrders() {
    return null;
    // return waiterData.select("query for orders");
  }

  public ResultSet viewReady() {
    return null;
    // return waiterData.select("query for ready dishes ready to collect");
  }

  /** Method to delete a given dish from the Menu table in the database.
   * 
   * @param dishName the name of the dish to be deleted
   * 
   */
  public void deleteMenuItem(String dishName) {
    waiterData.executeDelete("DELETE FROM Menu " + "WHERE dish = '" + dishName + "';");
  }

  /** Method to add a given dish to the Menu table in the database.
   * 
   * @param attributes if columns are specified, it is used in the Insert query
   * @param values the values to be inserted to the database.
   * 
   */
  public void addMenuItem(String attributes, String values) {
    waiterData.insertIntoTable("Menu", attributes, values);
  }

  /** Method checks if the item already exists in the Menu table in the database.
   * 
   * @param dishName the name of the dish to check
   * @return returns whether or not the dish exists in the table
   * 
   */
  public boolean checkKeyExists(String dishName) {
    try {
      ResultSet rs = waiterData.select("SELECT * FROM MENU WHERE dish = '" + dishName + "';");
      if (rs.next()) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  public void getMenu() throws SQLException {
    ResultSet rs = waiterData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstace();
    
    while (rs.next()) {
      String itemName = rs.getString("dish");
      String type = rs.getString("type");
      float itemPrice = rs.getFloat("price");
      tempMap.put(type, new Consumable(itemName, itemPrice));
    }
  }
}
