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
    
  }
  
  public void giveFeedback (String feedback) {
    
  }
}

