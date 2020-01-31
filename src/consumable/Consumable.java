package consumable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Each dish that the restaurant offers will be represented by an object from this class.
 */
public class Consumable implements Comparable<Consumable> {

	/** The name of the dish. */
	private String name;
	
	/** The price of the dish. */
	private float price;
	
	/** Specifies whether or not the dish is currently available. */
	private boolean isAvailable = true;
	
	/** Specifies the menu type of the dish. */
	private String menuType = null;
	
	/** A list of ingredients required to make the dish. */
	private List<String> ingredients;

	/**
	 * Instantiates a new consumable dish by specifying its name, price, menu type and ingredients.
	 *
	 * @param name the name of the dish
	 * @param price the price of the dish
	 * @param menuType the menu type
	 * @param ingredients the ingredients needed to make the dish
	 */
	public Consumable(String name, float price, String menuType, List<String> ingredients) {
		this.name = name;
		this.price = price;
		this.menuType = menuType;
		this.ingredients = new ArrayList<String>();
		for (String ingredient : ingredients) {
			this.ingredients.add(ingredient);
		}
		Collections.sort(this.ingredients);
	}

	
	/**
	 * by specifying its name, price, menu type and ingredients but NOT it's menu type
	 *
	 * @param name the name
	 * @param price the price
	 * @param ingredients the ingredients
	 */
	public Consumable(String name, float price, List<String> ingredients) {
		this.name = name;
		this.price = price;
		this.ingredients = new ArrayList<String>();
		for (String ingredient : ingredients) {
			this.ingredients.add(ingredient);
		}
		Collections.sort(this.ingredients);
	}
	
	/**
	 * Instantiates a new consumable dish by specifying its name, menu type and price, but not its ingredients.
	 *
	 * @param name the name
	 * @param price the price
	 * @param menuType the menu type
	 */
	public Consumable(String name, float price, String menuType) {
		this.name = name;
		this.price = price;
		this.menuType = menuType;
	}
	
	/**
	 * Instantiates a new consumable dish by specifying its name and price, but not its ingredients.
	 *
	 * @param name the name
	 * @param price the price
	 */
	public Consumable(String name, float price) {
		this(name, price, new ArrayList<String>());
	}

	/**
	 * Instantiates a new consumable dish by specifying its name, but not its price or ingredients.
	 *
	 * @param name the name
	 */
	public Consumable(String name) {
		this(name, 0, new ArrayList<String>());
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
	 * @param menuType the new special
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * returns menu type.
	 *
	 * @return true, if is special
	 */
	public String getType() {
		return this.menuType;
	}

	/**
	 * Returns the list of the dish's ingredients.
	 *
	 * @return the ingredients
	 */
	public List<String> getIngredients() {
		return this.ingredients;
	}

	/**
	 * Compare two dishes.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Consumable o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
