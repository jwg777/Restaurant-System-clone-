package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

public class WaiterAccess {
  DataInteract waiterData;
  
  public WaiterAccess() {
    waiterData = DataInteract.getInstance();
  }
  
  public void markDelivered(String tableupdate) {
    //waiterData.insertIntoTable("insert delivered data");
  }
  
  public ResultSet viewOrders() {
    return null;
    //return waiterData.select("query for orders");
  }
  
  public ResultSet viewReady() {
    return null;
    //return waiterData.select("query for ready dishes ready to collect");
  }
  
  public void deleteMenuItem(String dishName) {
    waiterData.executeDelete("DELETE FROM Menu " +
        "WHERE dish = '" + dishName + "';");
  }
  
  public void addMenuItem(String tableName, String attributes, String values) {
    waiterData.insertIntoTable(tableName, attributes, values);
  }
}
