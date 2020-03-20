
package consumable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 *
 * @author Chak
 */
public class Consumable implements Comparable<Consumable>, Serializable {

  /** serial ID of consumable class */
  private static final long serialVersionUID = 4356225391046116317L;

  private int id;

  /** Category of the consumable. */
  private String type;

  /** The name of the dish. */
  private String name;

  /** The price of the dish. */
  private float price;

  /** Calories of the dish. */
  private int calories;

  /** Specifies whether or not the dish is currently available. */
  private boolean isAvailable;

  /** A list of ingredients required to make the dish. */
  private String ingredients;

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
  public Consumable(int id, String type, String name, float price, int calories,
      boolean isAvailable, String ingredients) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.price = price;
    this.calories = calories;
    this.ingredients = ingredients;
    this.isAvailable = isAvailable;
  }

  public Consumable(String type, String name, float price, int calories,
      boolean isAvailable, String ingredients) {
    this.type = type;
    this.name = name;
    this.price = price;
    this.calories = calories;
    this.ingredients = ingredients;
    this.isAvailable = isAvailable;
  }

  public Consumable(String serializedString) {
    byte[] data = Base64.getDecoder().decode(serializedString);
    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
      Consumable temp = (Consumable) ois.readObject();
      this.type = temp.type;
      this.name = temp.name;
      this.price = temp.price;
      this.calories = temp.calories;
      this.ingredients = temp.ingredients;
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public int getID() {
    return this.id;
  }

  public boolean getIsAvailable() {
    return isAvailable;
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
   * Returns the list of the dish's ingredients.
   *
   * @return the ingredients
   */
  public String getIngredients() {
    return this.ingredients;
  }

  public String getType() {
    return type;
  }

  /**
   * Serialises Object to String.
   *
   * @return String
   * @throws IOException
   */
  public String serializeToString() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(this);
    return Base64.getEncoder().encodeToString(baos.toByteArray());
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Consumable other = (Consumable) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
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
