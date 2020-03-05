
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
    if (instance == null) {
      instance = new Customer();
    }
    return instance;
  }

  public ArrayList<Consumable> getMenuList() {
    return menuList;
  }

  public ArrayList<Consumable> getMenu() throws SQLException {
    return database.getDishList();
  }
}
