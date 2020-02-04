package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import consumable.Consumable;
import database_cafe.DataInteract;

public class CustomerAccess {
  
  DataInteract customerData;
  
  public CustomerAccess() {
    customerData = new DataInteract();
  }
  
  public ArrayList<Consumable> getMenu() throws SQLException {
    ResultSet rs = customerData.select("SELECT * FROM Menu");
    ArrayList<Consumable> list = new ArrayList<>();
    
    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      list.add(new Consumable(itemName, itemPrice));
    }
    
    return list;
  }
  
  public void placeOrder(String orders) {
    //customerData.insertIntoTable("insert order data");
  }
  
  public void giveFeedback (String feedback) {
    //customerData.insertIntoTable("insert feedback data");
  }
}

