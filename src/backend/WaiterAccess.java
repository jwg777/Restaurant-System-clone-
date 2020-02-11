package backend;

import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaiterAccess {
  DataInteract waiterData;

  public WaiterAccess() {
    waiterData = DataInteract.getInstance();
  }
  
  public void getMenu() throws SQLException {

    ResultSet rs = waiterData.select("SELECT * FROM Menu");
    MenuMap tempMap = MenuMap.getInstance();

    while (rs.next()) {
      String itemName = rs.getString("dish");
      String type = rs.getString("type");
      float itemPrice = rs.getFloat("price");
      tempMap.put(type, new Consumable(itemName, itemPrice));
    }


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
}
