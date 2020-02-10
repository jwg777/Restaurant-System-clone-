package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;

public class CustomerAccess {
  
  DataInteract customerData;
  
  public CustomerAccess() {
    customerData = DataInteract.getInstance();
  }
  
  public void getMenu() throws SQLException {
    ResultSet rs = customerData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstace();
    
    while (rs.next()) {
      String itemName = rs.getString("dish");
      String type = rs.getString("type");
      float itemPrice = rs.getFloat("price");
      tempMap.put(type, new Consumable(itemName, itemPrice));
    }
  }
  
  public void placeOrder(String orders) {
    //customerData.insertIntoTable("insert order data");
  }
  
  public void giveFeedback (String feedback) {
    //customerData.insertIntoTable("insert feedback data");
  }
}

