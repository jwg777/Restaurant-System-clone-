package testConsumables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import consumable.Consumable;

public class testConsumable {

	private List<String> ingredients;
	private List<String> unsortedIngredients;

	@Before
	public void prepareTests() {
		ingredients = new ArrayList<>();
		ingredients.add("apple");
		ingredients.add("bread");
		ingredients.add("cabbage");
		ingredients.add("dark chocolate");
		unsortedIngredients = new ArrayList<>();
		unsortedIngredients.add("bread");
		unsortedIngredients.add("apple");
		unsortedIngredients.add("dark chocolate");
		unsortedIngredients.add("cabbage");

	}

	@Test
	public void testConstructor() {
		Consumable consumable = new Consumable("ChakDesert", 20.20f, unsortedIngredients);
		assertEquals("Failed to get name", "ChakDesert", consumable.getName());
		assertEquals("Failed to get price", 20.20f, consumable.getPrice(), 0.001);
		assertEquals("Failed to sort ingredients", ingredients, consumable.getIngredients());
	}

	@Test
	public void testNewConstructor() {
		Consumable consumable = new Consumable("ASDF");
		assertEquals("Failed to get get name", "ASDF", consumable.getName());
		assertEquals("Failed to get correct price", 0, consumable.getPrice(), 0.001);
		assertTrue("Failed to create empty ingredients list", consumable.getIngredients().isEmpty());
	}

	@Test
	public void testValues() {
		Consumable temp = new Consumable("test1");
		assertTrue("Failed to get default availability", temp.isAvailable());
		assertFalse("Failed to get default isSpecial", temp.isSpecial());
	}
	
	@Test
	public void testSetters() {
		Consumable temp = new Consumable("test1");
		assertTrue("Failed to get default availability", temp.isAvailable());
		temp.setAvailable(false);
		assertFalse("Failed to change availability", temp.isAvailable());
		assertFalse("Failed to get default isSpecial", temp.isSpecial());
		temp.setSpecial(true);
		assertTrue("Failed to get default isSpecial", temp.isSpecial());
	}

}
