package consumable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Chak
 *
 */
public final class MenuMap{

  /**
   * instance for singleton class
   */
  private static MenuMap instance = null;
  /**
   * Hash map to keep track what is going to be displayed on the menu
   */
  private HashMap<String, ArrayList<Consumable>> menu = new HashMap<>();

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
  public HashMap<String, ArrayList<Consumable>> getMenu() {
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
    String tab = consumable.getType();
    ArrayList<Consumable> tempList = new ArrayList<>();
    if (menu.containsKey(tab)) {
      for (Consumable item : menu.get(tab)) {
        tempList.add(item);
      }
    }
    tempList.add(consumable);
    menu.put(tab, tempList);
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
  public ArrayList<Consumable> get(String key) {
    return menu.get(key);
  }
}