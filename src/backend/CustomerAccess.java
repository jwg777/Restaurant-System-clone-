package backend;

import java.sql.ResultSet;
import database_cafe.DataInteract;

public class CustomerAccess {
  
  DataInteract customerData;
  
  public CustomerAccess() {
    customerData = new DataInteract();
  }
  
  public ResultSet getMenu() {
    return customerData.select("SELECT * FROM Menu");
  }
  
  public void placeOrder(String orders) {
    //customerData.insertIntoTable("insert order data");
  }
  
  public void giveFeedback (String feedback) {
    //customerData.insertIntoTable("insert feedback data");
  }
}

