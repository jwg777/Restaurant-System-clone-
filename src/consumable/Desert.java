package consumable;

import java.util.ArrayList;

public class Desert extends Consumable {

	public Desert(String name) {
		super(name);
	}

	public Desert(String name, float price) {
		super(name, price);
	}

	public Desert(String name, float price, ArrayList<String> ingredients) {
		super(name, price, ingredients);
	}
	
	

}
