package views;

import java.util.ArrayList;

import consumable.Consumable;
import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the waiter view..
 */
public class WaiterViewController {
  
  /** The button controller. */

  
 

  SceneController butController = SceneController.getInstance();

  /**
   * When the 'Return to Main Menu button is pressed, return to the main menu.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception{
      butController.startMain();
  }
  @FXML
	VBox vboxStarter = new VBox();
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
		list.add(new Consumable("Dipo",50.00f));
		list.add(new Consumable("hello",50.00f));

		vboxStarter.getChildren().clear();
		addVBoxElements(list);
	}
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
  private StackPane initialiseButton(String name) {
		StackPane sPane = new StackPane(); // Stack pane to centre button
		sPane.setPrefSize(80, 50);
		Button button = new Button(name); // Button to remove and add food to order list
		button.setPrefSize(50, 50);
		sPane.getChildren().add(button);
		return sPane;
	}
  private Pane initialiseGap() {
		Pane gap = new Pane();
		gap.setPrefSize(25, 50);
		return gap;
	}
  private Label initialiseLabel(String name, double width, double height) {
		Label label = new Label(name);
		label.setPrefSize(width, height);
		return label;
	}
  
  
 
}
