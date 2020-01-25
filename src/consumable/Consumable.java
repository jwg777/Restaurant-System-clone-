package consumable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consumable {

	private String name;
	private float price;
	private boolean isAvailable = true;
	private boolean isSpecial = false;
	private List<String> ingredients;

	public Consumable(String name, float price, List<String> ingredients) {
		this.name = name;
		this.price = price;
		this.ingredients = new ArrayList<String>();
		for(String ingredient : ingredients) {
			this.ingredients.add(ingredient);
		}
		Collections.sort(this.ingredients);
	}
	
	public Consumable(String name) {
		this(name, 0, new ArrayList<String>());
	}

	public String getName() {
		return this.name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getPrice() {
		return this.price;
	}
	
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isAvailable() {
		return this.isAvailable;
	}
	
	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public boolean isSpecial() {
		return this.isSpecial;
	}

	public List<String> getIngredients() {
		return this.ingredients;
	}

}
