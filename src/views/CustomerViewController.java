
package views;

import java.util.ArrayList;
import backend.CustomerAccess;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

/**
 * Controller for the customer view.
 */
public class CustomerViewController {

  /** Object containing methods that interact with database **/
  CustomerAccess customerData = new CustomerAccess();

  /** The button controller */
  SceneController butController = SceneController.getInstance();

  MenuMap menu = MenuMap.getInstace();

  /**
   * A VBox containing the starters in the menu.
   */
  @FXML
  VBox vboxStarter = new VBox();

  @FXML
  TabPane menuTabPane = new TabPane();

  @FXML
  ListView<String> orderedList = new ListView<>();


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
   * When the reload button is pressed, refreshes the menu with any changes in the database.
   * 
   * @throws Exception the exception
   */
  @FXML
  private void reloadPush() throws Exception {
    menu.clear();
    customerData.getMenu();
    menuTabPane.getTabs().clear();
    createMenu(menu);
  }

  @FXML
  private void sendOrder() throws Exception {
    // method for sending order
  }

  @FXML
  void submitReview(ActionEvent event) {
    // System.out.println("Thanks");
    // method to submitReview
    try {
      FXMLLoader fLoad = new FXMLLoader(getClass().getResource("ThanksReviewView.fxml"));
      Parent root = (Parent) fLoad.load();
      Stage stage = new Stage();
      stage.setTitle("Thanks!");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds items to the VBox, as well as buttons to add/remove the item from an order.
   * 
   * @param consumables the consumables
   */
  private VBox createVBox(ArrayList<Consumable> consumables) {
    VBox vbox = new VBox();
    for (Consumable consumable : consumables) {
      HBox tempHBox = new HBox(); // Layout for one consumable of the list
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 200, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
      tempHBox.getChildren().add(initialiseLabel("£ " + price, 70, 50));
      tempHBox.getChildren().add(initialiseGap());
      StackPane minusStackPane = initialiseButton("-");
      ((Button) minusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          orderedList.getItems().remove(consumable.getName());
        }
      });
      tempHBox.getChildren().add(minusStackPane); // Remove food Button
      StackPane plusStackPane = initialiseButton("+");
      ((Button) plusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          orderedList.getItems().add(consumable.getName());
        }
      });
      tempHBox.getChildren().add(plusStackPane); // Add food Button
      StackPane infoStackPane = initialiseButton("i");
      ((Button) infoStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // method to bring up allergies and calories info
          try {
            FXMLLoader fLoad = new FXMLLoader(getClass().getResource("Allergy&CalorieView.fxml"));
            Parent root = (Parent) fLoad.load();
            Stage stage = new Stage();
            stage.setTitle("Thanks!");
            stage.setScene(new Scene(root));
            stage.show();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      tempHBox.getChildren().add(infoStackPane); // Add info button
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
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
    button.setPrefSize(40, 40);
    sPane.getChildren().add(button);
    return sPane;
  }

  /**
   * Initialises a gap between the other interface.
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

  /**
   * createMenu method to set menu depend of input value.
   * 
   * @param menu list of String
   */

  public void createMenu(MenuMap menu) {
    for (String string : menu.keyArray()) {
      menuTabPane.getTabs().add(createTab(string, menu.get(string)));
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

