package views;

import java.util.ArrayList;

import consumable.Consumable;
import consumable.MenuMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
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
  private void addDeletePush() throws Exception {
    
  }
  /** A VBox containing the starters in the menu **/
	
  @FXML
  TabPane orderTabPane = new TabPane();
  
  @FXML 
  HBox orderConfirm = new HBox();
  
  MenuMap tempMap = MenuMap.getInstace();
  
  
  @FXML
  private void reloadPush() throws Exception {
	System.out.println("check for the reload button");
	tempMap.put("WAITING ORDERS", new Consumable("Special test 1", 10f));
	tempMap.put("PROCESSING ORDERS", new Consumable("Starter test 1", 10f));
	tempMap.put("READY ORDERS", new Consumable("Main test 1", 10f));
	System.out.println("test reload button!");
	orderTabPane.getTabs().clear();
	createMenu(tempMap);
  }
	
  private VBox createVBox(ArrayList<Consumable> consumables) {
		VBox vbox = new VBox();
		for (Consumable consumable : consumables) {
			HBox tempHBox = new HBox(); // Layout for one consumable of the list
			tempHBox.setPrefHeight(50);
			tempHBox.getChildren().add(initialiseGap());
			tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 150, 50));
			tempHBox.getChildren().add(initialiseGap());
			String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
			tempHBox.getChildren().add(initialiseLabel("£ " + price, 150, 50));
			tempHBox.getChildren().add(initialiseGap());
			StackPane confirmStackPane = initialiseButton("Confirm");
			((Button)confirmStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	vbox.getChildren().remove(tempHBox);
	            	//tempHBox.getChildren().remove();
	            }
	        });
			tempHBox.getChildren().add(confirmStackPane); // Remove food Button
			
			vbox.getChildren().add(tempHBox); // Add consumable to the list
		}
		return vbox;
	}
  
  private StackPane initialiseButton(String name) {
		StackPane sPane = new StackPane(); // Stack pane to centre button
		sPane.setPrefSize(100, 50);
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
  
  public void createMenu(MenuMap menu) {
		for (String string : menu.keyArray()) {
			orderTabPane.getTabs().add(createTab(string, menu.get(string)));
		}
	}

  private Tab createTab(String name, ArrayList<Consumable> list) {
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefWidth(580);
		anchorPane.getChildren().add(createVBox(list));
		ScrollPane scrollPane = new ScrollPane(anchorPane);
		scrollPane.setPrefWidth(600);
		Tab tab = new Tab(name.toUpperCase(), scrollPane);
		return tab;
	}
 
}
