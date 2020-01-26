package consumable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Consumable.
 */
public class Consumable implements Comparator {

	/** The name. */
	private String name;
	
	/** The price. */
	private float price;
	
	/** The is available. */
	private boolean isAvailable = true;
	
	/** The is special. */
	private boolean isSpecial = false;
	
	/** The ingredients. */
	private List<String> ingredients;

	/**
	 * Instantiates a new consumable.
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
	 * Instantiates a new consumable.
	 *
	 * @param name the name
	 * @param price the price
	 */
	public Consumable(String name, float price) {
		this(name, price, new ArrayList<String>());
	}

	/**
	 * Instantiates a new consumable.
	 *
	 * @param name the name
	 */
	public Consumable(String name) {
		this(name, 0, new ArrayList<String>());
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Sets the available.
	 *
	 * @param isAvailable the new available
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
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
	 * Gets the ingredients.
	 *
	 * @return the ingredients
	 */
	public List<String> getIngredients() {
		return this.ingredients;
	}

	/**
	 * Compare.
	 *
	 * @param o1 the o 1
	 * @param o2 the o 2
	 * @return the int
	 */
	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

}
