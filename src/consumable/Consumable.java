package consumable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 * 
 * @author Chak
 */
public class Consumable implements Comparable<Consumable>, Serializable {

  /** serial ID of consumable class */
  private static final long serialVersionUID = -2617381742540284866L;

  /** Category of the consumable. */
  private String type;

  /** The name of the dish. */
  private String name;

  /** The price of the dish. */
  private float price;

  /** Calories of the dish. */
  private int calories;

  /** Allergens of the dish. */
  private String allergens;

  /** Specifies whether or not the dish is currently available. */
  private boolean isAvailable = true;

  /** Specifies whether or not the dish is a special. */
  private boolean isSpecial = false;

  /** A list of ingredients required to make the dish. */
  private List<String> ingredients;

  /**
   * Instantiates a new consumable dish by specifying its name, price and ingredients.
   *
   * @param type
   * @param name
   * @param price
   * @param calories
   * @param allergens
   * @param ingredients
   */
  public Consumable(String type, String name, float price, int calories, String allergens,
      List<String> ingredients) {
    this.type = type;
    this.name = name;
    this.price = price;
    this.calories = calories;
    this.allergens = allergens;
    this.ingredients = new ArrayList<String>();
    for (String ingredient : ingredients) {
      this.ingredients.add(ingredient);
    }
    Collections.sort(this.ingredients);
  }

  /**
   * Instantiates a new consumable dish by specifying its name and price, but not its ingredients.
   *
   * @param type
   * @param name
   * @param price
   * @param calories
   * @param allergens
   */
  public Consumable(String type, String name, float price, int calories, String allergens) {
    this(type, name, price, calories, allergens, new ArrayList<String>());
  }

  /**
   * Instantiates a new consumable dish by specifying its name, but not its price or ingredients.
   *
   * @param type
   * @param name
   */
  public Consumable(String type, String name) {
    this(type, name, 0, 0, "", new ArrayList<String>());
  }

  /**
   * Returns the name of the dish.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Changes the price of the dish.
   *
   * @param price the new price
   */
  public void setPrice(float price) {
    this.price = price;
  }

  /**
   * Returns the price of the dish.
   *
   * @return the price
   */
  public float getPrice() {
    return this.price;
  }

  public String getAllergen() {
    return this.allergens;
  }

  public int getCalories() {
    return this.calories;
  }

  /**
   * Sets a dish's availability.
   *
   * @param isAvailable true, if the dish is available
   */
  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  /**
   * Checks if the dish is available or not.
   *
   * @return true, if the dish is available
   */
  public boolean isAvailable() {
    return this.isAvailable;
  }

  /**
   * Sets the special.
   *
   * @param isSpecial the new special
   */
  public void setSpecial(boolean isSpecial) {
    this.isSpecial = isSpecial;
  }

  /**
   * Checks if is special.
   *
   * @return true, if is special
   */
  public boolean isSpecial() {
    return this.isSpecial;
  }

  /**
   * Returns the list of the dish's ingredients.
   *
   * @return the ingredients
   */
  public List<String> getIngredients() {
    return this.ingredients;
  }

  public String getType() {
    return type;
  }

  /**
   * Comparable method for sorting.
   *
   * @param o the o.
   * @return the int.
   */
  @Override
  public int compareTo(Consumable o) {
    // TODO Auto-generated method stub
    return 0;
  }

}
