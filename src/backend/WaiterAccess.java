package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

public class WaiterAccess {
  DataInteract waiterData;
  
  public WaiterAccess() {
    waiterData = new DataInteract();
  }
  
  public void markDelivered(String tableupdate) {
    //will update tables as delivered
  }
  
  public ResultSet viewOrders() {
    return null;
    //return waiterData.select("query for orders");
  }
  
  public ResultSet viewReady() {
    return null;
    //return waiterData.select("query for ready dishes ready to collect");
  }
}
