package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
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
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      float calories = rs.getFloat("calories");
      String type = rs.getString("type");
      
      tempMap.put(type, new Consumable(itemName, itemPrice, calories, allergens));
    }


  }

  public void placeOrder(String orders) {
    // customerData.insertIntoTable("insert order data");
  }

  public void giveFeedback(String feedback) {
    // customerData.insertIntoTable("insert feedback data");
  }
}

