package consumable;

import java.util.ArrayList;
import java.util.HashMap;

public final class MenuMap {
	
	private static MenuMap instance = null;
	private HashMap<String, ArrayList<Consumable>> menu = new HashMap<>();
	
	private MenuMap(){		
	}
	
	public static MenuMap getInstace() {
		if(instance == null) {
			instance = new MenuMap();
		}
		return instance;
	}
	
	public boolean isEmpty() {
		return menu.isEmpty();
	}
	
	public HashMap<String, ArrayList<Consumable>> getMenu() {
		return this.menu;
	}
	
	public void clear() {
		this.menu.clear();
	}
	
	public void put(String tab, Consumable consumable) {
		ArrayList<Consumable> tempList = new ArrayList<>();
		if(menu.containsKey(tab)) {
			for(Consumable item : menu.get(tab)) {
				tempList.add(item);
			}
		}
		tempList.add(consumable);
		menu.put(tab, tempList);
	}
	
	
	
}
