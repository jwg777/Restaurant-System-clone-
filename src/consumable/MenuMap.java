package consumable;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuMap.
 */
public final class MenuMap {

  /** The instance. */
  private static MenuMap instance = null;

  /** The menu. */
  private HashMap<String, ArrayList<Consumable>> menu = new HashMap<>();

  /**
   * Instantiates a new menu map.
   */
  private MenuMap() {}

  /**
   * Gets the single instance of MenuMap.
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
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  public boolean isEmpty() {
    return menu.isEmpty();
  }

  /**
   * Gets the menu.
   *
   * @return the menu
   */
  public HashMap<String, ArrayList<Consumable>> getMenu() {
    return this.menu;
  }

  /**
   * Clear.
   */
  public void clear() {
    this.menu.clear();
  }

  /**
   * Put.
   *
   * @param tab the tab
   * @param consumable the consumable
   */
  public void put(String tab, Consumable consumable) {
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
   * Key array.
   *
   * @return the array list
   */
  public ArrayList<String> keyArray() {
    ArrayList<String> tempList = new ArrayList<>();
    for (String string : menu.keySet()) {
      tempList.add(string);
    }
    return tempList;
  }

  /**
   * Gets the.
   *
   * @param key the key
   * @return the array list
   */
  public ArrayList<Consumable> get(String key) {
    return menu.get(key);
  }



}
