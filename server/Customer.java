

import java.sql.ResultSet;
import consumable.Consumable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer {

  Database database = Database.getInstance();
  
  private static Customer instance = null;
  
  private static ArrayList<Consumable> menuList = new ArrayList<>();

  private Customer() {
    try {
      menuList = getMenu();
    } catch (SQLException e) {
    }
  }

  public static Customer getInstance() {
    if(instance == null) {
      instance = new Customer();
    }
    return instance;
  }
  
  public ArrayList<Consumable> getMenuList(){
    return menuList;
  }

  public ArrayList<Consumable> getMenu() throws SQLException {
    ResultSet rs = database.select("SELECT * FROM Menu");
    ArrayList<Consumable> list = new ArrayList<>();
    while (rs.next()) {
      String itemName = rs.getString("dish");
      float itemPrice = rs.getFloat("price");
      String allergens = rs.getString("allergens");
      int calories = rs.getInt("calories");
      String type = rs.getString("type");
      list.add(new Consumable(type, itemName, itemPrice, calories, allergens));
    }
    return list;
  }
}
