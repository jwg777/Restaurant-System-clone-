package views;

import java.util.ArrayList;

import consumable.Consumable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the customer view.
 */
public class CustomerViewController {

	/** The but controller. */
	ButtonController butController = ButtonController.getInstance();
	
	/** A VBox containing the starters in the menu. */
	@FXML
	VBox vboxStarter = new VBox();

	/**
	 * When the 'Back to main menu' button is pressed, return to the main menu.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void returnPush() throws Exception {
		butController.startMain();
	}
	
/**
 * When the reload button is pressed, refresh the starters menu with any new changes applied.
 *
 * @throws Exception the exception
 */
/*
 * Test reload button to refresh starter menu
 */
	@FXML
	private void reloadPush() throws Exception {
		ArrayList<Consumable> list = new ArrayList<>();
		list.add(new Consumable("Apple", 110.00f));
		list.add(new Consumable("Bacon", 1032.00f));
		list.add(new Consumable("Pizza", 5.00f));
		list.add(new Consumable("Garlic Bread", 13.00f));
		list.add(new Consumable("Ham", 65.00f));
		list.add(new Consumable("Chocoalte", 12.00f));
		list.add(new Consumable("Honey", 76.00f));
		list.add(new Consumable("Donut", 2.00f));
		list.add(new Consumable("Sushi", 43.00f));
		list.add(new Consumable("Apple", 10.00f));
		list.add(new Consumable("Bacon", 32.00f));
		list.add(new Consumable("Pizza", 54.00f));
		list.add(new Consumable("Garlic Bread", 13.00f));
		list.add(new Consumable("Ham", 65.00f));
		list.add(new Consumable("Chocoalte", 12.00f));
		list.add(new Consumable("Honey", 76.00f));
		list.add(new Consumable("Donut", 2.00f));
		list.add(new Consumable("Sushi", 43.00f));
		vboxStarter.getChildren().clear();
		addVBoxElements(list);
	}

	/**
	 * Adds items to the VBox, as well as buttons to add/remove the item from an order.
	 *
	 * @param consumables the consumables
	 */
	public void addVBoxElements(ArrayList<Consumable> consumables) {
		for (Consumable consumable : consumables) {
			HBox tempHBox = new HBox(); // Layout for one consumable of the list
			tempHBox.setPrefHeight(50);
			tempHBox.getChildren().add(initialiseGap());
			tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 200, 50));
			tempHBox.getChildren().add(initialiseGap());
			String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
			tempHBox.getChildren().add(initialiseLabel("£ "+price, 70, 50));
			tempHBox.getChildren().add(initialiseGap());
			tempHBox.getChildren().add(initialiseButton("-")); // Remove food Button
			tempHBox.getChildren().add(initialiseButton("+")); // Add food Button
			vboxStarter.getChildren().add(tempHBox); // Add consumable to the list
		}
	}

	/**
	 * Creates a new button.
	 *
	 * @param name the text in the button
	 * @return the stack pane
	 */
	private StackPane initialiseButton(String name) {
		StackPane sPane = new StackPane(); // Stack pane to centre button
		sPane.setPrefSize(50, 50);
		Button button = new Button(name); // Button to remove and add food to order list
		button.setPrefSize(30, 30);
		sPane.getChildren().add(button);
		return sPane;
	}

	/**
	 * Initialises a gap.
	 *
	 * @return the pane
	 */
	private Pane initialiseGap() {
		Pane gap = new Pane();
		gap.setPrefSize(25, 50);
		return gap;
	}

	/**
	 * Initialises a text label.
	 *
	 * @param name the text in the label
	 * @param width the width of the label
	 * @param height the height of the label
	 * @return the label
	 */
	private Label initialiseLabel(String name, double width, double height) {
		Label label = new Label(name);
		label.setPrefSize(width, height);
		return label;
	}

}
