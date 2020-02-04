package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

public class KitchenAccess {
  
  DataInteract kitchenData;
  
  public KitchenAccess() {
    kitchenData = new DataInteract();
  }
  
  public void changeMenu() {
    //some query to alter menu
  }
  
  public ResultSet getOrders() {
    //return kitchenData.select("some query for orders");
    return null;
  }
  
  public void setOrderStatus() {
    //kitchenData.insertIntoTable("some command for order status");
  }
}
