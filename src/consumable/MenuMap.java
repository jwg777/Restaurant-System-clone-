package consumable;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;


/**
 * @author Chak
 *
 */
public final class MenuMap {

  /**
   * instance for singleton class
   */
  private static MenuMap instance = null;
  /**
   * Hash map to keep track what is going to be displayed on the menu
   */
  private ObservableMap<String, ObservableList<Consumable>> menu = FXCollections.observableHashMap();

  /**
   * private constructor for singleton class
   */

  private MenuMap() {}

  /**
   * returns the singleton instance of MenuMap.
   * 
   * @return single instance of MenuMap
   */
  public static MenuMap getInstance() {
    if (instance == null) {
      instance = new MenuMap();
    }
    return instance;
  }

  /**
   * returns if the map is empty.
   * 
   * @return true, if it's empty
   */
  public boolean isEmpty() {
    return menu.isEmpty();
  }

  /**
   * returns the menu.
   * 
   * @return the menu in Hash Map.
   */
  public ObservableMap<String, ObservableList<Consumable>> getMenu() {
    return this.menu;
  }

  /**
   * clears the menu / map.
   */
  public void clear() {
    this.menu.clear();
  }

  /**
   * puts the consumables into the correct key(category).
   * 
   * @param category
   * @param consumable
   */
  public void put(Consumable consumable) {
    String type = consumable.getType();
    ObservableList<Consumable> list;
    if (!menu.containsKey(type)) {
      list = FXCollections.observableArrayList();
      menu.put(type, list);
    } else {
      list = menu.get(type);
    }
    list.add(consumable);
  }

  /**
   * returns the keys in array list.
   * 
   * @return keys
   */
  public ArrayList<String> keyArray() {
    ArrayList<String> tempList = new ArrayList<>();
    for (String string : menu.keySet()) {
      tempList.add(string);
    }
    return tempList;
  }

  /**
   * Returns the consumable list from the given key.
   * 
   * @param key
   * @return the consumables in that category
   */
  public ObservableList<Consumable> get(String key) {
    return menu.get(key);
  }

  public void remove(Consumable consumable) {
    String type = consumable.getType();
    ObservableList<Consumable> list = menu.get(type);
    for (Consumable tempConsumable : list) {
      if (tempConsumable.equals(consumable)) {
        list.remove(tempConsumable);
        break;
      }
    }
  }
}
