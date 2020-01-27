package consumable;

import java.util.List;

public class Drink extends Consumable{

	public Drink(String name, float price, List<String> ingredients) {
		super(name, price, ingredients);
	}

	public Drink(String name, float price) {
		super(name, price);
	}

	public Drink(String name) {
		super(name);
	}

}
