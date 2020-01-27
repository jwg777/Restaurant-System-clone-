package consumable;

import java.util.List;

public class Starter extends Consumable{

	public Starter(String name, float price, List<String> ingredients) {
		super(name, price, ingredients);
	}

	public Starter(String name, float price) {
		super(name, price);
	}

	public Starter(String name) {
		super(name);
	}
	
	

}
